package com.scaleset.utils;

import java.util.Date;

public class FloatUtils {

    /**
     * Returns an Float object holding the value of the specified String. If the
     * string is null or if there is an issue parsing the String, then the
     * default value is returned instead.
     * 
     * @param str
     *            The object to coerce to a Float
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Float
     * @return The input coerced to a Float or the fallback value if the input
     *         cannot be coerced
     */
    public static Float valueOf(String str, Number fallback) {
        Float result = fallback == null ? null : fallback.floatValue();
        if (str != null) {
            try {
                result = Float.valueOf(str);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    /**
     * Returns a Float object holding the value of the specified Object. If the
     * object is null or if there is an issue converting the object, then the
     * fallback value is returned instead. Supported types include: String,
     * Float, Long
     * 
     * @param obj
     *            The object to coerce to a Float
     * @param fallback
     *            The value to return if the input in null or cannot be coerced
     *            to a Float
     * @return The input coerced to a Float or the fallback value if the input
     *         cannot be coerced
     */
    public static Float valueOf(Object obj, Number fallback) {
        Float result = fallback == null ? null : fallback.floatValue();
        if (obj instanceof Number) {
            result = ((Number) obj).floatValue();
        } else if (obj instanceof String) {
            try {
                result = Float.valueOf((String) obj);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            result = (float) date.getTime();
        }
        return result;
    }
}
