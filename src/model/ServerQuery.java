package model;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * Created by shrralis on 2/19/17.
 */
public class ServerQuery<T extends Owner> implements Serializable {
    private String sTableName = null;

    private String sMethodName = null;

    private T mObjectToProcess;

    private ServerQuery(@NotNull String tableName, @NotNull String methodName, T object) {
        sTableName = tableName;
        sMethodName = methodName;
        mObjectToProcess = object;
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Owner> ServerQuery create(@NotNull String tableName, @NotNull String methodName, T object) {
        return new ServerQuery(tableName, methodName, object);
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
}
