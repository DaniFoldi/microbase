package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
public class WaterfallBaseServer implements BaseServer {
    private final ServerInfo server;

    WaterfallBaseServer(ServerInfo server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getPlayers().stream().map(WaterfallBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public Object raw() {
        return server;
    }
}
