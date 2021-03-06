package com.danifoldi.microbase;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface BaseSender {
    boolean hasPermission(String permission);

    void send(String message);
    void send(BaseMessage message);
    void send(Component message);

    String displayName();
    String name();
    UUID uniqueId();

    Object raw();
}
