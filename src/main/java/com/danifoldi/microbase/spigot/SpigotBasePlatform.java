package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuppressWarnings("ClassCanBeRecord")
public class SpigotBasePlatform implements BasePlatform {
    private final Server server;

    SpigotBasePlatform(Server server) {
        this.server = server;
    }

    @Override
    public BasePlayer getPlayer(UUID uuid) {
        return new SpigotBasePlayer(server.getPlayer(uuid));
    }

    @Override
    public BasePlayer getPlayer(String name) {
        return new SpigotBasePlayer(server.getPlayer(name));
    }

    @Override
    public List<BasePlayer> getPlayers() {
        return server.getOnlinePlayers().stream().map(SpigotBasePlayer::new).map(p -> (BasePlayer)p).toList();
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
            command.setExecutor(Microbase.addCommand(commandAliases, new SpigotCommand(dispatch, suggest)));
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
            command.setExecutor(Microbase.addCommand(commandAliases, new SpigotCommand(dispatch, suggest)));
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
        HandlerList.unregisterAll((Listener)Microbase.removeCommand(command));
    }

    @Override
    public void runConsoleCommand(String command) {
        server.dispatchCommand(server.getConsoleSender(), command);
    }

    @Override
    public List<BasePlugin> getPlugins() {
        return Arrays.stream(server.getPluginManager().getPlugins()).map(SpigotBasePlugin::new).map(p -> (BasePlugin)p).toList();
    }

    @Override
    public Map<String, BaseServer> getServers() {
        return Map.of(server.getName(), new SpigotBaseServer(server));
    }

    @Override
    public Object raw() {
        return server;
    }
}
