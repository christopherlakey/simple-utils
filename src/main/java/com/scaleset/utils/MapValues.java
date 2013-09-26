package com.scaleset.utils;


import java.util.Map;

public class MapValues<K> {

    private Map<K, ?> values;

    public MapValues(Map<K, ?> values) {
        this.values = values;
    }

    public Integer getInteger(K key) {
        Object value = values.get(key);
        return IntegerUtils.valueOf(value, null);
    }

    public Long getLong(K key) {
        Object value = values.get(key);
        return LongUtils.valueOf(value, null);
    }

    public Double getDouble(K key) {
        Object value = values.get(key);
        return DoubleUtils.valueOf(value, null);
    }

    public String getString(K key) {
        Object value = values.get(key);
        return StringUtils.valueOf(value, null);
    }

    public Float getFloat(K key) {
        Object value = values.get(key);
        return FloatUtils.valueOf(value, null);
    }

}
