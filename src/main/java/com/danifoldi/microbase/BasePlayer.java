package com.danifoldi.microbase;

public interface BasePlayer extends BaseSender {
    boolean vanished();

    int protocol();

    int ping();

    String locale();

    void run(String command);

    void actionbar(String message);

    void chat(String message);

    void connect(BaseServer server);

    BaseServer connectedTo();

    void title(String message, int fadeIn, int stay, int fadeOut);

    void subtitle(String message, int fadeIn, int stay, int fadeOut);

    Object raw();
}
