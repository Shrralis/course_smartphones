package model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Collection of helpers to parse server responses.
 *
 * Created by shrralis on 2/19/17.
 */
public class ParseUtils {
    private ParseUtils() {}
    /**
     * Parse boolean from ResultSet with given name.
     *
     * @param from server response like this format: {@code field: 1}
     * @param name name of field to read
     */
    public static boolean parseBoolean(ResultSet from, String name) {
        try {
            return from != null && from.getInt(name) == 1;
        } catch (SQLException ignored) {
            return false;
        }
    }
    /**
     * Parse int from ResultSet with given name.
     *
     * @param from server response like this format: {@code field: 34}
     * @param name name of field to read
     */
    public static int parseInt(ResultSet from, String name) {
        if (from == null) {
            return 0;
        }

        try {
            return from.getInt(name);
        } catch (SQLException ignored) {
            return 0;
        }
    }
    /**
     * Parse long from ResultSet with given name.
     *
     * @param from server response like this format: {@code field: 34}
     * @param name name of field to read
     */
    public static long parseLong(ResultSet from, String name) {
        if (from == null) {
            return 0;
        }

        try {
            return from.getLong(name);
        } catch (SQLException ignored) {
            return 0;
        }
    }
    /**
     * Parses object with follow rules:
     * <p/>
     * 1. All fields should had a public access.
     * 2. The name of the filed should be fully equal to name of JSONObject key.
     * 3. Supports parse of all Java primitives, all {@link java.lang.String},
     * arrays of primitive types, {@link java.lang.String}s and {@link model.Model}s.
     * <p/>
     * 4. Boolean fields defines by int == 1 expression.
     *
     * @param object object to initialize
     * @param source data to read values
     * @param <T>    type of result
     * @return initialized according with given data object
     * @throws SQLException if source object structure is invalid
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T parseViaReflection(T object, ResultSet source) throws SQLException {
        if (source == null) {
            return object;
        }

        for (Field field : object.getClass().getFields()) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            Object value = source.getObject(fieldName);

            if (value == null) {
                continue;
            }

            try {
                if (fieldType.isPrimitive() && value instanceof Number) {
                    Number number = (Number) value;

                    if (fieldType.equals(int.class)) {
                        field.setInt(object, number.intValue());
                    } else if (fieldType.equals(long.class)) {
                        field.setLong(object, number.longValue());
                    } else if (fieldType.equals(float.class)) {
                        field.setFloat(object, number.floatValue());
                    } else if (fieldType.equals(double.class)) {
                        field.setDouble(object, number.doubleValue());
                    } else if (fieldType.equals(boolean.class)) {
                        field.setBoolean(object, number.intValue() == 1);
                    } else if (fieldType.equals(short.class)) {
                        field.setShort(object, number.shortValue());
                    } else if (fieldType.equals(byte.class)) {
                        field.setByte(object, number.byteValue());
                    }
                } else {
                    Object result = field.get(object);

                    if (value.getClass().equals(fieldType)) {
                        result = value;
                    } else if (Model.class.isAssignableFrom(fieldType) && value instanceof ResultSet) {
                        result = ((Model) fieldType.newInstance()).parse((ResultSet) value);
                    }
                    field.set(object, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
