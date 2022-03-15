package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class WaterfallPlatform {
    private final static BungeeAudiences audience = BungeeAudiences.create((Plugin)Microbase.getPlugin().raw());

    public static BaseMessage baseMessage() {
        return new WaterfallBaseMessage();
    }

    public static BasePlatform toBasePlatform(ProxyServer server) {
        return new WaterfallBasePlatform(server, audience);
    }

    public static BasePlayer toBasePlayer(ProxiedPlayer player) {
        return new WaterfallBasePlayer(player, audience);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new WaterfallBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new WaterfallBaseSender(sender, audience);
    }

    public static BaseServer toBaseServer(ServerInfo server) {
        return new WaterfallBaseServer(server, audience);
    }

    private WaterfallPlatform() {
        throw new UnsupportedOperationException();
    }
}
