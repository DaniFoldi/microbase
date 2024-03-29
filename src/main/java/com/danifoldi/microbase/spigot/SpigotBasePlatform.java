package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.internal.CommandCache;
import com.google.common.annotations.Beta;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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

public class SpigotBasePlatform implements BasePlatform {
    private final Server server;
    private final BukkitAudiences audience;

    SpigotBasePlatform(Server server, BukkitAudiences audience) {
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

    private @Nullable BasePlayer playerOrNull(Supplier<Player> playerSupplier) {
        Player player = playerSupplier.get();
        return player == null ? null : new SpigotBasePlayer(player, audience);
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getOnlinePlayers().stream().map(p -> new SpigotBasePlayer(p, audience)).map(p -> (BasePlayer)p).toList();
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
            PluginCommand command = SpigotCommand.PLUGIN_COMMAND_CONSTRUCTOR.newInstance(commandAliases.stream().findFirst().orElseThrow(), Microbase.getPlugin().raw());
            command.setExecutor(CommandCache.addCommand(commandAliases, new SpigotCommand(dispatch, suggest)));
            command.setAliases(commandAliases.stream().skip(1).toList());
            commandAliases.forEach(a -> SpigotCommand.KNOWN_COMMANDS.put(a, command));
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
            PluginCommand command = SpigotCommand.PLUGIN_COMMAND_CONSTRUCTOR.newInstance(commandAliases.stream().findFirst().orElseThrow(), Microbase.getPlugin().raw());
            command.setExecutor(CommandCache.addCommand(commandAliases, new SpigotCommand(dispatch, suggest)));
            command.setPermission(permission);
            command.setAliases(commandAliases.stream().skip(1).toList());
            commandAliases.forEach(a -> SpigotCommand.KNOWN_COMMANDS.put(a, command));
            Bukkit.getPluginManager().registerEvents((Listener)command.getExecutor(), (Plugin)Microbase.getPlugin().raw());
        } catch (ReflectiveOperationException e) {
            Microbase.logger.warning("Failed to register command");
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterCommand(String command) {
        SpigotCommand.KNOWN_COMMANDS.remove(command);
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
        return Arrays.stream(server.getPluginManager().getPlugins()).map(SpigotBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        return Map.of(server.getName(), new SpigotBaseServer(server, audience));
    }

    @Override
    public Object raw() {
        return server;
    }
}
