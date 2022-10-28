package com.danifoldi.microbase;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
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

    default void broadcast(String message) {
        getPlayers().forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcastExcept(String message, BasePlayer player) {
        getPlayers().stream().filter(basePlayer -> basePlayer.equals(player)).forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcast(String message, String permission) {
        getPlayers().stream().filter(basePlayer -> basePlayer.hasPermission(permission)).forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcast(Component message) {
        getPlayers().forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcastExcept(Component message, BasePlayer player) {
        getPlayers().stream().filter(basePlayer -> basePlayer.equals(player)).forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcast(Component message, String permission) {
        getPlayers().stream().filter(basePlayer -> basePlayer.hasPermission(permission)).forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcast(BaseMessage message) {
        getPlayers().forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcastExcept(BaseMessage message, BasePlayer player) {
        getPlayers().stream().filter(basePlayer -> !basePlayer.equals(player)).forEach(basePlayer -> basePlayer.send(message));
    }

    default void broadcast(BaseMessage message, String permission) {
        getPlayers().stream().filter(basePlayer -> basePlayer.hasPermission(permission)).forEach(basePlayer -> basePlayer.send(message));
    }

    List<BasePlugin> getPlugins();

    Map<String, BaseServer> getServers();

    Object raw();
}
