package com.scaleset.utils;

import java.util.Date;

public class DoubleUtils {

    /**
     * Returns an Double object holding the value of the specified String. If
     * the string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param str
     *            The string to coerce to a double
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Double
     * @return The input coerced to a Double or the fallback value if the input
     *         cannot be coerced
     */
    public static Double valueOf(String str, Number fallback) {
        Double result = fallback == null ? null : fallback.doubleValue();
        if (str != null) {
            try {
                result = Double.valueOf(str);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    /**
     * Returns an Double object holding the value of the specified Object. If
     * the object is null or if there is an issue converting the object, then
     * the defalt value is returned instead. Supported types include: String,
     * Double, Long
     * 
     * @param obj
     *            The object to coerce to a Double
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Double
     * @return The input coerced to a Double or the fallback value if the input
     *         cannot be coerced
     */
    public static Double valueOf(Object obj, Number fallback) {
        Double result = fallback == null ? null : fallback.doubleValue();
        if (obj instanceof Number) {
            result = ((Number) obj).doubleValue();
        } else if (obj instanceof String) {
            try {
                result = Double.valueOf((String) obj);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            result = (double) date.getTime();
        }
        return result;
    }
}
