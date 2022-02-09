package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class WaterfallCommand extends Command implements TabExecutor {
    private final BiConsumer<BaseSender, String> dispatch;
    private final BiFunction<BaseSender, String, Collection<String>> suggest;

    WaterfallCommand(final List<String> aliases,
                   final BiConsumer<BaseSender, String> dispatch,
                   final BiFunction<BaseSender, String, Collection<String>> suggest) {
        super(aliases.stream().findFirst().orElseThrow(), null, aliases.stream().skip(1).toArray(String[]::new));
        this.dispatch = dispatch;
        this.suggest = suggest;
    }

    WaterfallCommand(final List<String> aliases,
                   final String permission,
                   final BiConsumer<BaseSender, String> dispatch,
                   final BiFunction<BaseSender, String, Collection<String>> suggest) {
        super(aliases.stream().findFirst().orElseThrow(), permission, aliases.stream().skip(1).toArray(String[]::new));
        this.dispatch = dispatch;
        this.suggest = suggest;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        BaseSender protoSender = Microbase.toBaseSender(sender);
        if (sender instanceof ProxiedPlayer player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        dispatch.accept(protoSender, buildArguments(args));
    }

    @Override
    public Iterable<String> onTabComplete(final CommandSender sender, final String[] args) {
        BaseSender protoSender = Microbase.toBaseSender(sender);
        if (sender instanceof ProxiedPlayer player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        return suggest.apply(protoSender, buildArguments(args));
    }

    private String buildArguments(final String[] args) {
        final StringJoiner joiner = new StringJoiner(" ");
        joiner.add(getName());
        Arrays.stream(args).forEach(joiner::add);
        return joiner.toString();
    }
}