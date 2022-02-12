package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BaseSender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class BungeecordBaseSender implements BaseSender {
    private final CommandSender sender;

    BungeecordBaseSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public void send(String message) {
        sender.sendMessage(new TextComponent(message));
    }

    @Override
    public void send(BaseMessage message) {
        if (message instanceof BungeecordBaseMessage cMessage) {
            sender.sendMessage(cMessage.convert());
        }
    }

    @Override
    public String displayName() {
        if (sender instanceof ProxiedPlayer player) {
            return player.getDisplayName();
        }
        return sender.getName();
    }

    @Override
    public String name() {
        return sender.getName();
    }

    @Override
    public UUID uniqueId() {
        if (sender instanceof ProxiedPlayer player) {
            return player.getUniqueId();
        }
        return UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
    }
}
