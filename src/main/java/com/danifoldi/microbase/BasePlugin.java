package com.danifoldi.microbase;

import java.util.List;

public interface BasePlugin {
    String name();
    String description();
    String main();
    String version();

    List<String> authors();
    List<String> dependencies();
    List<String> softDependencies();

    Object raw();
}
