package com.danifoldi.microbase;

import java.util.Collection;

public interface BaseServer {
    String name();
    Collection<BasePlayer> players();

    Object raw();
}
