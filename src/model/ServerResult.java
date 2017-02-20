package model;

import com.sun.istack.internal.NotNull;

/**
 * Created by shrralis on 2/19/17.
 */
public class ServerResult<T extends Owner> {
    private int iResult = 0;

    private String sMessage = null;

    private T mObject;

    private ServerResult(int result, @NotNull String message) {
        iResult = result;
        sMessage = message;
    }

    private ServerResult(T object) {
        if (object != null) {
            mObject = object;
            iResult = 0;
        } else {
            mObject = null;
            iResult = -1;
        }
    }

    public static <T extends Owner> ServerResult create(int result, @NotNull String message) {
        return new ServerResult(result, message);
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Owner> ServerResult create(T object) {
        return new ServerResult(object);
    }

    public int getResult() {
        return iResult;
    }

    public String getMessage() {
        return sMessage;
    }

    public T getObject() {
        return mObject;
    }
}
