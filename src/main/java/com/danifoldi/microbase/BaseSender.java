package com.danifoldi.microbase;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

@SuppressWarnings("unused")
public interface BaseSender {
    boolean hasPermission(String permission);

    default boolean isPlayer() {
        return this instanceof BasePlayer;
    }

    default void send(String message) {
        send(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    default void send(BaseMessage message) {
        send(message.convert());
    }

    void send(Component message);

    String displayName();

    String name();

    UUID uniqueId();

    Object raw();
}
