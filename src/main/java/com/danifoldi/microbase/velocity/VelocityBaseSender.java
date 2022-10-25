package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BaseSender;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class VelocityBaseSender implements BaseSender {
    private final CommandSource sender;

    VelocityBaseSender(CommandSource sender) {
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public void send(Component message) {
        sender.sendMessage(message);
    }

    @Override
    public String displayName() {
        if (sender instanceof Player player) {
            return player.getUsername();
        }
        return "CONSOLE";
    }

    @Override
    public String name() {
        if (sender instanceof Player player) {
            return player.getUsername();
        }
        return "CONSOLE";
    }

    @Override
    public UUID uniqueId() {
        if (sender instanceof Player player) {
            return player.getUniqueId();
        }
        return UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
    }

    @Override
    public Object raw() {
        return sender;
    }
}
