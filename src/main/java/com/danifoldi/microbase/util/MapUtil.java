package com.danifoldi.microbase.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    @SuppressWarnings("unchecked")
    public static<K, V> Map<K, V> make(Object... data) {
        Map<K, V> result = new HashMap<>();
        int i = 0;
        while (i < data.length) {
            result.put((K)data[i], (V)data[i + 1]);
            i += 2;
        }
        return result;
    }

    private MapUtil() {
        throw new UnsupportedOperationException();
    }
}
