package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import com.danifoldi.microbase.depend.ViaVersionDepend;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpigotBasePlayer extends SpigotBaseSender implements BasePlayer {
    private final Player player;
    private final BukkitAudiences audience;

    SpigotBasePlayer(Player player, BukkitAudiences audience) {
        super(player, audience);
        this.player = player;
        this.audience = audience;
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
        //noinspection deprecation
        player.sendActionBar(message);
    }

    @Override
    public void chat(String message) {
        player.chat(message);
    }

    @Override
    public void connect(BaseServer server) {
        Microbase.logger.warning("connect does nothing on your platform");
    }

    @Override
    public BaseServer connectedTo() {
        return new SpigotBaseServer(Bukkit.getServer(), audience);
    }

    @Override
    public void title(String message, int fadeIn, int stay, int fadeOut) {
        //noinspection deprecation
        player.showTitle(new TextComponent(message), null, fadeIn, stay, fadeOut);
    }

    @Override
    public void subtitle(String message, int fadeIn, int stay, int fadeOut) {
        //noinspection deprecation
        player.showTitle(null, new TextComponent(message), fadeIn, stay, fadeOut);
    }

    @Override
    public void send(BaseMessage message) {

    }
}
