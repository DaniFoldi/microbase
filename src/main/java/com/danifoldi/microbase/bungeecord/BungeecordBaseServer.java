package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
public class BungeecordBaseServer implements BaseServer {
    private final ServerInfo server;

    BungeecordBaseServer(ServerInfo server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getPlayers().stream().map(BungeecordBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }
}
