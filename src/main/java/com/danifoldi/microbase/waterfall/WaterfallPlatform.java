package com.danifoldi.microbase.waterfall;

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

public class WaterfallPlatform {
    public static BasePlatform toBasePlatform(ProxyServer server) {
        return new WaterfallBasePlatform(server);
    }

    public static BasePlayer toBasePlayer(ProxiedPlayer player) {
        return new WaterfallBasePlayer(player);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new WaterfallBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new WaterfallBaseSender(sender);
    }

    public static BaseServer toBaseServer(ServerInfo server) {
        return new WaterfallBaseServer(server);
    }

    private WaterfallPlatform() {
        throw new UnsupportedOperationException();
    }
}
