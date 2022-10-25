package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class VelocityPlatform {
    private VelocityPlatform() {
        throw new UnsupportedOperationException();
    }

    public static BaseMessage baseMessage() {
        return new VelocityBaseMessage();
    }

    public static BasePlatform toBasePlatform(ProxyServer server) {
        return new VelocityBasePlatform(server);
    }

    public static BasePlayer toBasePlayer(Player player) {
        return new VelocityBasePlayer(player);
    }

    public static BasePlugin toBasePlugin(PluginContainer plugin) {
        return new VelocityBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSource sender) {
        return new VelocityBaseSender(sender);
    }

    public static BaseServer toBaseServer(RegisteredServer server) {
        return new VelocityBaseServer(server);
    }
}
