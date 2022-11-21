package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;

public class WaterfallBaseServer implements BaseServer {
    private final ServerInfo server;
    private final BungeeAudiences audience;

    WaterfallBaseServer(ServerInfo server, BungeeAudiences audience) {
        this.server = server;
        this.audience = audience;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getPlayers().stream().map(p -> new WaterfallBasePlayer(p, audience)).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public Object raw() {
        return server;
    }
}
