package com.danifoldi.microbase;

import net.kyori.adventure.text.Component;

import java.util.UUID;

@SuppressWarnings("unused")
public interface BaseSender {
    boolean hasPermission(String permission);

    default boolean isPlayer() {
        return this instanceof BasePlayer;
    }

    void send(String message);

    void send(BaseMessage message);

    void send(Component message);

    String displayName();

    String name();

    UUID uniqueId();

    Object raw();
}
