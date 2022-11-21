package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.internal.CommandCache;
import com.google.common.annotations.Beta;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BungeecordBasePlatform implements BasePlatform {
    private final ProxyServer server;
    private final BungeeAudiences audience;

    BungeecordBasePlatform(ProxyServer server, BungeeAudiences audience) {
        this.server = server;
        this.audience = audience;
    }

    @Override
    public @Nullable BasePlayer getPlayer(UUID uuid) {
        return playerOrNull(() -> server.getPlayer(uuid));
    }

    @Override
    public @Nullable BasePlayer getPlayer(String name) {
        return playerOrNull(() -> server.getPlayer(name));
    }

    private @Nullable BasePlayer playerOrNull(Supplier<ProxiedPlayer> playerSupplier) {
        @Nullable ProxiedPlayer player = playerSupplier.get();
        return player == null ? null : new BungeecordBasePlayer(player, audience);
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getPlayers().stream().map(p -> new BungeecordBasePlayer(p, audience)).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public String platformName() {
        return server.getName();
    }

    @Override
    public String platformVersion() {
        return server.getVersion();
    }

    @Override
    public int maxPlayerCount() {
        return server.getConfig().getPlayerLimit();
    }

    @Override
    public void registerCommand(List<String> commandAliases, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        server.getPluginManager().registerCommand((Plugin) Microbase.getPlugin().raw(), CommandCache.addCommand(commandAliases, new BungeecordCommand(commandAliases, dispatch, suggest)));
    }

    @Override
    public void registerCommand(List<String> commandAliases, String permission, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        server.getPluginManager().registerCommand((Plugin)Microbase.getPlugin().raw(), new BungeecordCommand(commandAliases, permission, dispatch, suggest));
    }

    @Override
    public void unregisterCommand(String command) {
        server.getPluginManager().unregisterCommand((Command) CommandCache.removeCommand(command));
    }

    @Override
    public void runConsoleCommand(String command) {
        server.getPluginManager().dispatchCommand(server.getConsole(), command);
    }

    @Beta
    @Override
    public void registerEventHandler(Object listener) {
        server.getPluginManager().registerListener((Plugin)Microbase.getPlugin().raw(), (Listener)listener);
    }

    @Beta
    @Override
    public void unregisterEventHandler(Object listener) {
        server.getPluginManager().unregisterListener((Listener)listener);
    }

    @Beta
    @Override
    public void unregisterAllEventHandlers() {
        server.getPluginManager().unregisterListeners((Plugin)Microbase.getPlugin().raw());
    }

    @Beta
    @Override
    public boolean dispatchEvent(Object event) {
        server.getPluginManager().callEvent((Event)event);

        return event instanceof Cancellable c && c.isCancelled();
    }

    @Override
    public List<BasePlugin> getPlugins() {
        return server.getPluginManager().getPlugins().stream().map(BungeecordBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        // noinspection deprecation
        Map<String, ServerInfo> servers = server.getServers();
        return Map.copyOf(servers).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new BungeecordBaseServer(e.getValue(), audience)));
    }

    @Override
    public Object raw() {
        return server;
    }
}
