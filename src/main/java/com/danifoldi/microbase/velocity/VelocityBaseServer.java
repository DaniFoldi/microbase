package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
public class VelocityBaseServer implements BaseServer {
    private final RegisteredServer server;

    VelocityBaseServer(RegisteredServer server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.getServerInfo().getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getPlayersConnected().stream().map(VelocityBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }
}
