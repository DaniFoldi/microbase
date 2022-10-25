package com.danifoldi.microbase;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@SuppressWarnings("unused")
public interface BasePlayer extends BaseSender {
    boolean vanished();

    int protocol();

    int ping();

    String locale();

    void run(String command);

    default void actionbar(String message) {
        actionbar(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    default void actionbar(BaseMessage message) {
        actionbar(message.convert());
    }

    void actionbar(Component message);

    void chat(String message);

    void connect(BaseServer server);

    BaseServer connectedTo();

    void title(String message, int fadeIn, int stay, int fadeOut);

    void subtitle(String message, int fadeIn, int stay, int fadeOut);

    Object raw();
}
