package com.scaleset.utils;

public class ClassUtils {

    public static <T> T safeNewInstance(Class<T> clss) {
        T result = null;
        try {
            result = clss.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
