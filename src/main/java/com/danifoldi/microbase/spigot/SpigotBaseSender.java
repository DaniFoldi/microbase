package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BaseSender;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class SpigotBaseSender implements BaseSender {
    private final CommandSender sender;
    private final BukkitAudiences audience;

    SpigotBaseSender(CommandSender sender, BukkitAudiences audience) {
        this.sender = sender;
        this.audience = audience;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public void send(String message) {
        send(LegacyComponentSerializer.legacySection().deserialize(message));
    }

    @Override
    public void send(BaseMessage message) {
        if (message instanceof SpigotBaseMessage cMessage) {
            sender.sendMessage(cMessage.convert());
        }
    }

    @Override
    public void send(Component message) {
        audience.sender(sender).sendMessage(message);
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
