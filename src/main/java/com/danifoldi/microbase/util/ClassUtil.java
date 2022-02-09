package com.danifoldi.microbase.util;

public class ClassUtil {

    public static boolean check(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private ClassUtil() {
        throw new UnsupportedOperationException();
    }
}
