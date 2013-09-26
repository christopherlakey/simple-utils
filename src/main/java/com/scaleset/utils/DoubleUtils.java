package com.scaleset.utils;


import java.util.Date;

public class DoubleUtils {

    /**
     * Returns an Double object holding the value of the specified String. If
     * the string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param s
     * @param fallback
     * @return
     */
    public static Double valueOf(String s, Number fallback) {
        Double result = fallback == null ? null : fallback.doubleValue();
        if (s != null) {
            try {
                result = Double.valueOf(s);
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
     * @param s
     * @param fallback
     * @return
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
