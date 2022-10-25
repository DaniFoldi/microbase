package com.danifoldi.microbase;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface BasePlatform {
    @Nullable BasePlayer getPlayer(UUID uuid);

    @Nullable BasePlayer getPlayer(String name);

    List<BasePlayer> getPlayers();

    String platformName();

    String platformVersion();

    int maxPlayerCount();

    void registerCommand(List<String> commandAliases, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest);

    void registerCommand(List<String> commandAliases, String permission, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest);

    void unregisterCommand(String command);

    default void unregisterAllCommands() {
        Microbase.registeredCommands().forEach(this::unregisterCommand);
    }

    void runConsoleCommand(String command);

    List<BasePlugin> getPlugins();

    Map<String, BaseServer> getServers();

    Object raw();
}
