package com.danifoldi.microbase;

import java.util.UUID;

public interface BaseSender {
    boolean hasPermission(String permission);
    void send(String message);
    String displayName();
    String name();
    UUID uniqueId();
}
