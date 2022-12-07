package com.danifoldi.microbase.paper;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.internal.CommandCache;
import com.google.common.annotations.Beta;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class PaperBasePlatform implements BasePlatform {
    private final Server server;

    PaperBasePlatform(Server server) {
        this.server = server;
    }

    @Override
    public @Nullable BasePlayer getPlayer(UUID uuid) {
        return playerOrNull(() -> server.getPlayer(uuid));
    }

    @Override
    public @Nullable BasePlayer getPlayer(String name) {
        return playerOrNull(() -> server.getPlayer(name));
    }

    private @Nullable BasePlayer playerOrNull(Supplier<Player> playerSupplier) {
        @Nullable Player player = playerSupplier.get();
        return player == null ? null : new PaperBasePlayer(player);
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getOnlinePlayers().stream().map(PaperBasePlayer::new).map(p -> (BasePlayer)p).toList();
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
        return server.getMaxPlayers();
    }

    @Override
    public void registerCommand(List<String> commandAliases, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        try {
            //noinspection JavaReflectionInvocation
            PluginCommand command = PaperCommand.PLUGIN_COMMAND_CONSTRUCTOR.newInstance(commandAliases.stream().findFirst().orElseThrow(), Microbase.getPlugin().raw());
            command.setExecutor(CommandCache.addCommand(commandAliases, new PaperCommand(dispatch, suggest)));
            command.setAliases(commandAliases.stream().skip(1).toList());
            commandAliases.forEach(a -> PaperCommand.KNOWN_COMMANDS.put(a, command));
            Bukkit.getPluginManager().registerEvents((Listener)command.getExecutor(), (Plugin)Microbase.getPlugin().raw());
        } catch (ReflectiveOperationException e) {
            Microbase.logger.warning("Failed to register command");
            e.printStackTrace();
        }
    }

    @Override
    public void registerCommand(List<String> commandAliases, String permission, BiConsumer<BaseSender, String> dispatch, BiFunction<BaseSender, String, Collection<String>> suggest) {
        try {
            //noinspection JavaReflectionInvocation
            PluginCommand command = PaperCommand.PLUGIN_COMMAND_CONSTRUCTOR.newInstance(commandAliases.stream().findFirst().orElseThrow(), Microbase.getPlugin().raw());
            command.setExecutor(CommandCache.addCommand(commandAliases, new PaperCommand(dispatch, suggest)));
            command.setPermission(permission);
            command.setAliases(commandAliases.stream().skip(1).toList());
            commandAliases.forEach(a -> PaperCommand.KNOWN_COMMANDS.put(a, command));
            Bukkit.getPluginManager().registerEvents((Listener)command.getExecutor(), (Plugin)Microbase.getPlugin().raw());
        } catch (ReflectiveOperationException e) {
            Microbase.logger.warning("Failed to register command");
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterCommand(String command) {
        PaperCommand.KNOWN_COMMANDS.remove(command);
        HandlerList.unregisterAll((Listener)CommandCache.removeCommand(command));
    }

    @Override
    public void runConsoleCommand(String command) {
        server.dispatchCommand(server.getConsoleSender(), command);
    }

    @Override
    public void registerEventHandler(Object listener) {
        Bukkit.getPluginManager().registerEvents((Listener)listener, (JavaPlugin)Microbase.getPlugin().raw());
    }

    @Override
    public void unregisterEventHandler(Object listener) {
        HandlerList.unregisterAll((Listener)listener);
    }

    @Override
    public void unregisterAllEventHandlers() {
        HandlerList.unregisterAll((JavaPlugin)Microbase.getPlugin().raw());
    }

    @Override
    public boolean dispatchEvent(Object event) {
        Bukkit.getPluginManager().callEvent((Event)event);

        return event instanceof Cancellable c && c.isCancelled();
    }

    @Override
    public List<BasePlugin> getPlugins() {
        return Arrays.stream(server.getPluginManager().getPlugins()).map(PaperBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        return Map.of(server.getName(), new PaperBaseServer(server));
    }

    @Override
    public Object raw() {
        return null;
    }
}
