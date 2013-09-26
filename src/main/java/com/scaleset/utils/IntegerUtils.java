package com.scaleset.utils;


import java.util.Date;

public class IntegerUtils {

    /**
     * Returns an Integer object holding the value of the specified String. If
     * the string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param s
     * @param fallback
     * @return
     */
    public static Integer valueOf(String s, Number fallback) {
        Integer result = fallback == null ? null : fallback.intValue();
        if (s != null) {
            try {
                result = Integer.valueOf(s);
            } catch (NumberFormatException nfe) {
                // leave result = fallback;
            }
        }
        return result;
    }

    public static Integer valueOf(String s) {
        return valueOf(s, (Number) null);
    }

    public static Integer valueOf(Object obj) {
        return valueOf(obj, (Number) null);
    }

    /**
     * Returns an Integer object holding the value of the specified Object. If
     * the object is null or if there is an issue converting the objectO, then
     * the defalt value is returned instead. Supported types include: String,
     * Integer, Long
     * 
     * @param s
     * @param fallback
     * @return
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
