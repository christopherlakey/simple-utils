package com.scaleset.utils;


import java.util.Date;

public class FloatUtils {

    /**
     * Returns an Float object holding the value of the specified String. If the
     * string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param s
     * @param fallback
     * @return
     */
    public static Float valueOf(String s, Number fallback) {
        Float result = fallback == null ? null : fallback.floatValue();
        if (s != null) {
            try {
                result = Float.valueOf(s);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }


    /**
     * Returns an Float object holding the value of the specified Object. If the
     * object is null or if there is an issue converting the object, then the
     * defalt value is returned instead. Supported types include: String, Float,
     * Long
     * 
     * @param s
     * @param fallback
     * @return
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
