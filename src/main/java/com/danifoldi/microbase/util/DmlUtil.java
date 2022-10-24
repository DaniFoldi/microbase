package com.danifoldi.microbase.util;

import com.danifoldi.dml.type.DmlObject;
import com.danifoldi.dml.type.DmlString;
import com.danifoldi.dml.type.DmlValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DmlUtil {

    public static Map<String, String> flattenStrings(DmlValue value) {
        if (!(value instanceof DmlObject object)) {
            return Collections.emptyMap();
        }
        return flattenStrings(object, "");
    }

    public static Map<String, String> flattenStrings(DmlObject object, String prefix) {
        Map<String, String> values = new HashMap<>();
        object.keys().forEach(key -> {
            DmlValue value = object.get(key);
            if (value instanceof DmlString stringValue) {
                values.put(Objects.equals(prefix, "") ? key.value() : prefix + "." + key.value(), stringValue.value());
            } else if (value instanceof DmlObject objectValue) {
                values.putAll(flattenStrings(objectValue, Objects.equals(prefix, "") ? key.value() : prefix + "." + key.value()));
            }
        });
        return values;
    }

    private DmlUtil() {
        throw new UnsupportedOperationException();
    }
}
