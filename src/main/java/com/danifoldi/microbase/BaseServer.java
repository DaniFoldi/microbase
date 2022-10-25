package com.danifoldi.microbase;

import java.util.Collection;

@SuppressWarnings("unused")
public interface BaseServer {
    String name();

    Collection<BasePlayer> players();

    Object raw();
}
