package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class BungeecordBasePlatform implements BasePlatform {
    private final ProxyServer server;

    BungeecordBasePlatform(ProxyServer server) {
        this.server = server;
    }

    @Override
    public BasePlayer getPlayer(UUID uuid) {
        return new BungeecordBasePlayer(server.getPlayer(uuid));
    }

    @Override
    public BasePlayer getPlayer(String name) {
        return new BungeecordBasePlayer(server.getPlayer(name));
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getPlayers().stream().map(BungeecordBasePlayer::new).map(p -> (BasePlayer)p).toList();
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
        server.getPluginManager().registerCommand((Plugin) Microbase.getPlugin().raw(), Microbase.addCommand(commandAliases, new BungeecordCommand(commandAliases, dispatch, suggest)));
    }

    @Override
    public void registerCommand(List<String> commandAliases, String permission, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        server.getPluginManager().registerCommand((Plugin)Microbase.getPlugin().raw(), new BungeecordCommand(commandAliases, permission, dispatch, suggest));
    }

    @Override
    public void unregisterCommand(String command) {
        server.getPluginManager().unregisterCommand((Command)Microbase.removeCommand(command));
    }

    @Override
    public void runConsoleCommand(String command) {
        server.getPluginManager().dispatchCommand(server.getConsole(), command);
    }

    @Override
    public List<BasePlugin> getPlugins() {
        return server.getPluginManager().getPlugins().stream().map(BungeecordBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        return Map.copyOf(server.getServers()).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new BungeecordBaseServer(e.getValue())));
    }

    @Override
    public Object raw() {
        return server;
    }
}
