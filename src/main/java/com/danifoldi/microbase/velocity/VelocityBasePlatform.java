package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class VelocityBasePlatform implements BasePlatform {
    private final ProxyServer server;

    VelocityBasePlatform(ProxyServer server) {
        this.server = server;
    }

    @Override
    public BasePlayer getPlayer(UUID uuid) {
        return server.getPlayer(uuid).map(VelocityBasePlayer::new).orElse(null);
    }

    @Override
    public BasePlayer getPlayer(String name) {
        return server.getPlayer(name).map(VelocityBasePlayer::new).orElse(null);
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getAllPlayers().stream().map(VelocityBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public String platformName() {
        return server.getVersion().getName();
    }

    @Override
    public String platformVersion() {
        return server.getVersion().getVersion();
    }

    @Override
    public int maxPlayerCount() {
        return server.getConfiguration().getShowMaxPlayers();
    }

    @Override
    public void registerCommand(List<String> commandAliases, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        server.getCommandManager().register(commandAliases.stream().findFirst().orElseThrow(), Microbase.addCommand(commandAliases, new VelocityCommand(dispatch, suggest, commandAliases.stream().findFirst().orElseThrow())), commandAliases.stream().skip(1).toArray(String[]::new));
    }

    @Override
    public void registerCommand(List<String> commandAliases, String permission, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        server.getCommandManager().register(commandAliases.stream().findFirst().orElseThrow(), Microbase.addCommand(commandAliases, new VelocityCommand(permission, dispatch, suggest, commandAliases.stream().findFirst().orElseThrow())), commandAliases.stream().skip(1).toArray(String[]::new));
    }

    @Override
    public void unregisterCommand(String command) {
        Microbase.removeCommand(command);
        server.getCommandManager().unregister(command);
    }

    @Override
    public void runConsoleCommand(String command) {
        server.getCommandManager().executeAsync(server.getConsoleCommandSource(), command);
    }

    @Override
    public List<BasePlugin> getPlugins() {
        return server.getPluginManager().getPlugins().stream().map(VelocityBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        return server.getAllServers().stream().collect(Collectors.toMap(e -> e.getServerInfo().getName(), VelocityBaseServer::new));
    }
}
