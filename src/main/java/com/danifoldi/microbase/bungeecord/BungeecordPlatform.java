package com.danifoldi.microbase.bungeecord;

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

public class BungeecordPlatform {
    private final static BungeeAudiences audience = BungeeAudiences.create((Plugin)Microbase.getPlugin());

    public static BaseMessage baseMessage() {
        return new BungeecordBaseMessage();
    }

    public static BasePlatform toBasePlatform(ProxyServer server) {
        return new BungeecordBasePlatform(server, audience);
    }

    public static BasePlayer toBasePlayer(ProxiedPlayer player) {
        return new BungeecordBasePlayer(player, audience);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new BungeecordBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new BungeecordBaseSender(sender, audience);
    }

    public static BaseServer toBaseServer(ServerInfo server) {
        return new BungeecordBaseServer(server, audience);
    }

    private BungeecordPlatform() {
        throw new UnsupportedOperationException();
    }
}
