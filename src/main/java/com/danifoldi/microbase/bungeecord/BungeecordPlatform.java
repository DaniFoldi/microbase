package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeecordPlatform {
    public static BasePlatform toBasePlatform(ProxyServer server) {
        return new BungeecordBasePlatform(server);
    }

    public static BasePlayer toBasePlayer(ProxiedPlayer player) {
        return new BungeecordBasePlayer(player);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new BungeecordBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new BungeecordBaseSender(sender);
    }

    public static BaseServer toBaseServer(ServerInfo server) {
        return new BungeecordBaseServer(server);
    }

    private BungeecordPlatform() {
        throw new UnsupportedOperationException();
    }
}
