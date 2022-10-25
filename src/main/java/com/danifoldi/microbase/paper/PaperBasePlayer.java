package com.danifoldi.microbase.paper;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import com.danifoldi.microbase.depend.ViaVersionDepend;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PaperBasePlayer extends PaperBaseSender implements BasePlayer {
    private final Player player;

    PaperBasePlayer(Player player) {
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
    public void actionbar(Component message) {
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
        return new PaperBaseServer(Bukkit.getServer());
    }

    @Override
    public void title(String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitlePart(TitlePart.TITLE, Component.text(message));
        player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }

    @Override
    public void subtitle(String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitlePart(TitlePart.SUBTITLE, Component.text(message));
        player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }
}
