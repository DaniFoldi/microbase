package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.Microbase;
import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class VelocityCommand implements RawCommand {
    private final String permission;
    private final BiConsumer<BaseSender, String> dispatch;
    private final BiFunction<BaseSender, String, Collection<String>> suggest;
    private final String name;

    VelocityCommand(final BiConsumer<BaseSender, String> dispatch,
                   final BiFunction<BaseSender, String, Collection<String>> suggest,
                   final String name) {
        this.permission = null;
        this.dispatch = dispatch;
        this.suggest = suggest;
        this.name = name;
    }

    VelocityCommand(final String permission,
                   final BiConsumer<BaseSender, String> dispatch,
                   final BiFunction<BaseSender, String, Collection<String>> suggest,
                   final String name) {
        this.permission = permission;
        this.dispatch = dispatch;
        this.suggest = suggest;
        this.name = name;
    }

    private String buildArguments(final String args) {
        final StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name);
        joiner.add(args);
        return joiner.toString();
    }

    @Override
    public void execute(Invocation invocation) {
        BaseSender protoSender = Microbase.toBaseSender(invocation.source());
        if (invocation.source() instanceof Player player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        dispatch.accept(protoSender, buildArguments(invocation.arguments()));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        BaseSender protoSender = Microbase.toBaseSender(invocation.source());
        if (invocation.source() instanceof Player player) {
            protoSender = Microbase.toBasePlayer(player);
        }
        return suggest.apply(protoSender, buildArguments(invocation.arguments())).stream().toList();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.supplyAsync(() -> suggest.apply(Microbase.toBaseSender(invocation.source()), buildArguments(invocation.arguments())).stream().toList(), Microbase.getThreadPool());
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(permission);
    }
}
