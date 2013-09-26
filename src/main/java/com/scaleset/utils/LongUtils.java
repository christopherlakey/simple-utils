package com.scaleset.utils;


import java.util.Date;

public class LongUtils {

    /**
     * Returns an Long object holding the value of the specified String. If the
     * string is null or if there is an issue parsing the String, then the
     * defalt value is returned instead.
     * 
     * @param s
     * @param fallback
     * @return
     */
    public static Long valueOf(String s, Number fallback) {
        Long result = fallback == null ? null : fallback.longValue();
        if (s != null) {
            try {
                result = Long.valueOf(s);
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
     * defalt value is returned instead. Supported types include: String, Long,
     * Long
     * 
     * @param s
     * @param fallback
     * @return
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
