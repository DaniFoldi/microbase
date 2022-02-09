package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import org.bukkit.Server;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
public class SpigotBaseServer implements BaseServer {
    private final Server server;

    SpigotBaseServer(Server server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getOnlinePlayers().stream().map(SpigotBasePlayer::new).map(p -> (BasePlayer)p).toList();
    }
}
