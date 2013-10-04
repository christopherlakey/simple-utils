package com.scaleset.utils;

import java.util.Date;

public class LongUtils {

    /**
     * Returns a Long object holding the value of the specified String. If the
     * string is null or if there is an issue parsing the String, then the
     * default value is returned instead.
     * 
     * @param str
     *            The string to coerce to a Long
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Long
     * @return The input coerced to a Long or the fallback value if the input
     *         cannot be coerced
     */
    public static Long valueOf(String str, Number fallback) {
        Long result = fallback == null ? null : fallback.longValue();
        if (str != null) {
            try {
                result = Long.valueOf(str);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    public static Long valueOf(String s) {
        return valueOf(s, null);
    }

    /**
     * Returns an Long object holding the value of the specified Object. If the
     * object is null or if there is an issue converting the objectO, then the
     * default value is returned instead. Supported types include: String, Long,
     * Long
     * 
     * @param obj
     *            The Object to coerce to a Long
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Long
     * @return The input coerced to a Long or the fallback value if the input
     *         cannot be coerced
     */
    public static Long valueOf(Object obj, Number fallback) {
        Long result = fallback == null ? null : fallback.longValue();
        if (obj instanceof Number) {
            result = ((Number) obj).longValue();
        } else if (obj instanceof String) {
            try {
                result = Long.valueOf((String) obj);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            result = date.getTime();
        }
        return result;
    }

}
