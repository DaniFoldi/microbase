package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Server;

import java.util.Collection;

public class SpigotBaseServer implements BaseServer {
    private final Server server;
    private final BukkitAudiences audience;

    SpigotBaseServer(Server server, BukkitAudiences audience) {
        this.server = server;
        this.audience = audience;
    }

    @Override
    public String name() {
        return server.getName();
    }

    @Override
    public Collection<BasePlayer> players() {
        return server.getOnlinePlayers().stream().map(p -> new SpigotBasePlayer(p, audience)).map(p -> (BasePlayer)p).toList();
    }

    @Override
    public Object raw() {
        return server;
    }
}
