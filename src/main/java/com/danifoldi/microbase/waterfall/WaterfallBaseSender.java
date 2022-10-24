package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BaseSender;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class WaterfallBaseSender implements BaseSender {
    private final CommandSender sender;
    private final BungeeAudiences audience;

    WaterfallBaseSender(CommandSender sender, BungeeAudiences audience) {
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
        if (message instanceof WaterfallBaseMessage cMessage) {
            sender.sendMessage(cMessage.convert());
        }
    }

    @Override
    public void send(Component message) {
        audience.sender(sender).sendMessage(message);
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

    @Override
    public Object raw() {
        return sender;
    }
}
