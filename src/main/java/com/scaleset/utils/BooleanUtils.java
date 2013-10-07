package com.scaleset.utils;

public class BooleanUtils {

    /**
     * Returns an Integer object holding the value of the specified String. If
     * the string is null or if there is an issue parsing the String, then the
     * default value is returned instead.
     *
     * @param str      The string to coerce to a boolean
     * @param fallback The value to return if the input in null or cannot be coerced
     *                 to a boolean
     * @return The input coerced to a boolean or the fallback if the input
     *         cannot be coerced
     */
    public static Boolean valueOf(String str, Boolean fallback) {
        Boolean result = fallback == null ? null : fallback;
        if (str != null) {
            try {
                result = Boolean.valueOf(str);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    public static Boolean valueOf(String s) {
        return valueOf(s, null);
    }

    public static Boolean valueOf(Object obj) {
        return valueOf(obj, null);
    }

    /**
     * Returns an Boolean object holding the value of the specified Object. If
     * the object is null or if there is an issue converting the object, then
     * the defalt value is returned instead. Supported types include: String,
     * Integer, Long
     *
     * @param obj      The object to coerce to a boolean
     * @param fallback The value to return if the input in null or cannot be coerced
     *                 to a boolean
     * @return The input coerced to a boolean or the fallback if the input
     *         cannot be coerced
     */
    public static Boolean valueOf(Object obj, Boolean fallback) {
        Boolean result = fallback == null ? null : fallback;
        if (obj instanceof Boolean) {
            result = (Boolean) obj;
        } else if (obj instanceof Integer) {
            result = ((Integer) obj != 0);
        } else if (obj instanceof String) {
            try {
                result = valueOf((String) obj, result);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        } else if (obj != null) {
            result = true;
        }
        return result;
    }

}
