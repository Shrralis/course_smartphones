package model;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by shrralis on 2/19/17.
 */
public class ServerQuery<T extends Owner> implements Serializable {
    private String sTableName = null;

    private String sMethodName = null;

    private T mObjectToProcess;

    private HashMap<String, Object> mQueryParameters;

    private ServerQuery(@NotNull String tableName, @NotNull String methodName, T object, HashMap<String, Object> queryParameters) {
        sTableName = tableName;
        sMethodName = methodName;
        mObjectToProcess = object;
        mQueryParameters = queryParameters;
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Owner> ServerQuery create(@NotNull String tableName, @NotNull String methodName, T object,
                                                       HashMap<String, Object> queryParameters) {
        return new ServerQuery(tableName, methodName, object, queryParameters);
    }

    public String getTableName() {
        return sTableName;
    }

    public String getMethodName() {
        return sMethodName;
    }

    public T getObjectToProcess() {
        return mObjectToProcess;
    }
    @SuppressWarnings("unchecked")
    public String getMySQLCondition() {
        String result = "";

        if (isParamsValidForDB(mQueryParameters)) {
            result += " WHERE ";

            for (String key : mQueryParameters.keySet()) {
                if (mQueryParameters.get(key) != null) {
                    if (key.matches("^arr(\\s|\\S)*")) {
                        if (!((HashMap) mQueryParameters.get(key)).isEmpty()) {
                            result += parseOrConditionFromArray((HashMap<String, Object>) mQueryParameters.get(key));
                        }
                    } else {
                        key = (key.lastIndexOf(".") != -1
                                ? key.substring(0, key.lastIndexOf(".") + 1)
                                : "") + "`" + key.substring(key.indexOf(".") + 1) + "`";
                        result += (key.matches("^`(exp)(\\s|\\S)*`$") ? mQueryParameters.get(key) : (key +
                                (mQueryParameters.get(key) instanceof Number ? " = " + mQueryParameters.get(key) :
                                        (((String) mQueryParameters.get(key)).matches("^(NOT )?NULL$") ? " IS " +
                                                mQueryParameters.get(key) : " LIKE '" + mQueryParameters.get(key) + "'"))))
                                + " AND ";
                    }
                }
            }

            result = result.substring(0, result.lastIndexOf(" AND"));
        }
        return result;
    }
    @SuppressWarnings("unchecked")
    private boolean isParamsValidForDB(HashMap<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return false;
        }

        for (Object object: params.values()) {
            if (object != null) {
                if (object instanceof HashMap && isParamsValidForDB((HashMap<String, Object>) object)) {
                    return true;
                } else if (object instanceof String && !((String) object).equalsIgnoreCase("")) {
                    return true;
                }
                return true;
            }
        }
        return false;
    }
    @SuppressWarnings("unchecked")
    private String parseOrConditionFromArray(HashMap<String, Object> map) {
        String result = "(";

        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                if (key.matches("^arr(\\s|\\S)*/")) {
                    if (!((HashMap) map.get(key)).isEmpty()) {
                        result += parseOrConditionFromArray((HashMap<String, Object>) map.get(key));
                        result = result.substring(0, result.lastIndexOf(" AND "));
                        result += " OR ";
                    }
                } else {
                    result += '(';
                    boolean match = result.matches("^(\\D+)(\\d+)$");
                    String[] matches = key.split("^(\\D+)(\\d+)$");

                    key = match ? matches[0] : key;
                    key = (key.lastIndexOf(".") != -1
                            ? key.substring(0, key.lastIndexOf(".") + 1)
                            : "") + "`" + key.substring(key.indexOf(".") + 1) + "`";
                    result += (key.matches("^`(exp)(\\s|\\S)*`$") ? map.get(key) : (key +
                            (map.get(key) instanceof Number ? " = " +  map.get(key):
                            (((String) map.get(key)).matches("^(NOT )?NULL$") ? " IS " + map.get(key) : " LIKE '"
                                    + map.get(key) + "'")))) + ") OR ";
                }
            }
        }

        result = result.substring(0, result.lastIndexOf(" OR "));
        result += ") AND ";

        return result;
    }
}
