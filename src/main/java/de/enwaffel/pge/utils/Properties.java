package de.enwaffel.pge.utils;

import java.util.HashMap;

public class Properties {

    private final HashMap<String, Object> values = new HashMap<>();

    public void set(String name, Object value) {
        values.put(name, value);
    }

    public <T> T get(String name, Class<T> clazz, T defaultValue) {
        if (!values.containsKey(name)) return defaultValue;
        Object value = values.get(name);
        if (value == null) return null;

        return clazz.cast(value);
    }

    public <T> T get(String name, Class<T> clazz) {
        return get(name, clazz, null);
    }

    public boolean has(String name) {
        return values.containsKey(name);
    }

}
