package com.danifoldi.microbase.paper;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import org.bukkit.Server;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
public class PaperBaseServer implements BaseServer {
    private final Server server;

    PaperBaseServer(Server server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getOnlinePlayers().stream().map(PaperBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public Object raw() {
        return server;
    }
}
