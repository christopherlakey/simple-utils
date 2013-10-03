package com.scaleset.utils;

import java.util.Date;

public class IntegerUtils {

    /**
     * Returns an Integer object holding the value of the specified String. If
     * the string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param str
     *            The string to coerce to an Integer
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to an Integer
     * @return The input coerced to an Integer or the fallback value if the input
     *         cannot be coerced
     */
    public static Integer valueOf(String str, Number fallback) {
        Integer result = fallback == null ? null : fallback.intValue();
        if (str != null) {
            try {
                result = Integer.valueOf(str);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    /**
     * Returns an Integer object holding the value of the specified String. If
     * the object is null or if there is an issue converting the object, then
     * the null is returned instead. Supported types include: String, Integer,
     * Long
     * 
     * @param str
     *            The String to coerce to an Integer
     * @return The input coerced to a Integer or null if the input cannot be
     *         coerced
     */
    public static Integer valueOf(String str) {
        return valueOf(str, (Number) null);
    }

    /**
     * Returns an Integer object holding the value of the specified Object. If
     * the object is null or if there is an issue converting the object, then
     * the null is returned instead. Supported types include: String, Integer,
     * Long
     * 
     * @param obj
     *            The object to coerce to an Integer
     * @return The input coerced to a Integer or null if the input cannot be
     *         coerced
     */
    public static Integer valueOf(Object obj) {
        return valueOf(obj, (Number) null);
    }

    /**
     * Returns an Integer object holding the value of the specified Object. If
     * the object is null or if there is an issue converting the objectO, then
     * the defalt value is returned instead. Supported types include: String,
     * Integer, Long
     * 
     * @param obj
     *            The object to coerce to an Integer
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to an Integer
     * @return The input coerced to a Integer or the fallback value if the input
     *         cannot be coerced
     */
    public static Integer valueOf(Object obj, Number fallback) {
        Integer result = fallback == null ? null : fallback.intValue();
        if (obj instanceof Integer) {
            result = (Integer) obj;
        } else if (obj instanceof String) {
            try {
                result = Integer.valueOf((String) obj);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            result = (int) date.getTime();
        }
        return result;
    }

}
