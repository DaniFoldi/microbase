package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import com.danifoldi.microbase.depend.ViaVersionDepend;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

@SuppressWarnings("ClassCanBeRecord")
public class SpigotBasePlayer extends SpigotBaseSender implements BasePlayer {
    private final Player player;

    SpigotBasePlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean vanished() {
        return PremiumVanishDepend.vanished(player.getUniqueId());
    }

    @Override
    public int protocol() {
        return ViaVersionDepend.getProtocol(player.getUniqueId());
    }

    @Override
    public int ping() {
        return player.getPing();
    }

    @Override
    public String locale() {
        return player.locale().getDisplayName();
    }

    @Override
    public void run(String command) {
        player.performCommand(command);
    }

    @Override
    public void actionbar(String message) {
        player.sendActionBar(message);
    }

    @Override
    public void chat(String message) {
        player.chat(message);
    }

    @Override
    public void connect(BaseServer server) {
        Logger.getLogger("MicroBase").warning("connect does nothing on your platform");
    }

    @Override
    public BaseServer connectedTo() {
        return new SpigotBaseServer(Bukkit.getServer());
    }

    @Override
    public void title(String message, int fadeIn, int stay, int fadeOut) {
        player.showTitle(new TextComponent(message), null, fadeIn, stay, fadeOut);
    }

    @Override
    public void subtitle(String message, int fadeIn, int stay, int fadeOut) {
        player.showTitle(null, new TextComponent(message), fadeIn, stay, fadeOut);
    }
}
