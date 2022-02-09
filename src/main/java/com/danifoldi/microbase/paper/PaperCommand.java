package com.danifoldi.microbase.paper;

import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.Microbase;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class PaperCommand implements CommandExecutor, TabCompleter {
    private final BiConsumer<BaseSender, String> dispatch;
    private final BiFunction<BaseSender, String, Collection<String>> suggest;

    static final Constructor<PluginCommand> PLUGIN_COMMAND_CONSTRUCTOR;
    static final Map<String, Command> KNOWN_COMMANDS;

    static {
        try {
            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PLUGIN_COMMAND_CONSTRUCTOR = constructor;

            final Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            //noinspection unchecked
            KNOWN_COMMANDS = (Map<String, Command>)knownCommands.get(Bukkit.getCommandMap());
        } catch (final ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    PaperCommand(final BiConsumer<BaseSender, String> dispatch,
                 final BiFunction<BaseSender, String, Collection<String>> suggest) {
        this.dispatch = dispatch;
        this.suggest = suggest;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        BaseSender protoSender = Microbase.toBaseSender(sender);
        if (sender instanceof Player player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        dispatch.accept(protoSender, buildArguments(label, args));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        BaseSender protoSender = Microbase.toBaseSender(sender);
        if (sender instanceof ProxiedPlayer player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        return suggest.apply(protoSender, buildArguments(alias, args)).stream().toList();
    }

    private String buildArguments(final String alias, final String[] args) {
        final StringJoiner joiner = new StringJoiner(" ");
        joiner.add(alias);
        Arrays.stream(args).forEach(joiner::add);
        return joiner.toString();
    }
}
