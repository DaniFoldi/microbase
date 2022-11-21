package com.danifoldi.microbase.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CommandCache {
    private static final Map<String, Object> commandCache = new ConcurrentHashMap<>();

    public static<T> T addCommand(List<String> aliases, T command) {
        aliases.forEach(a -> commandCache.put(a, command));
        return command;
    }

    public static Object removeCommand(String name) {
        return commandCache.remove(name);
    }

    public static Set<String> registeredCommands() {
        return commandCache.keySet();
    }
}
