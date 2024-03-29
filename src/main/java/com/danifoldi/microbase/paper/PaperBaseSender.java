package com.danifoldi.microbase.paper;

import com.danifoldi.microbase.BaseSender;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperBaseSender implements BaseSender {
    private final CommandSender sender;

    PaperBaseSender(CommandSender sender) {
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
        return sender.getName();
    }

    @Override
    public String name() {
        return sender.getName();
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
