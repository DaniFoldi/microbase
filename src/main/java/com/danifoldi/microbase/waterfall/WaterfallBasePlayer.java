package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class WaterfallBasePlayer extends WaterfallBaseSender implements BasePlayer {
    private final ProxiedPlayer player;
    private final BungeeAudiences audience;

    WaterfallBasePlayer(ProxiedPlayer player, BungeeAudiences audience) {
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
        return player.getPendingConnection().getVersion();
    }

    @Override
    public int ping() {
        return player.getPing();
    }

    @Override
    public String locale() {
        return player.getLocale().getDisplayName();
    }

    @Override
    public void run(String command) {
        ProxyServer.getInstance().getPluginManager().dispatchCommand(player, command);
    }

    @Override
    public void actionbar(Component message) {
        audience.player(player).sendActionBar(message);
    }

    @Override
    public void chat(String message) {
        player.chat(message);
    }

    @Override
    public void connect(BaseServer server) {
        player.connect(ProxyServer.getInstance().getServerInfo(server.name()));
    }

    @Override
    public BaseServer connectedTo() {
        return new WaterfallBaseServer(player.getServer().getInfo(), audience);
    }

    @Override
    public void title(String message, int fadeIn, int stay, int fadeOut) {
        audience.player(player).sendTitlePart(TitlePart.TITLE, Component.text(message));
        audience.player(player).sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }

    @Override
    public void subtitle(String message, int fadeIn, int stay, int fadeOut) {
        audience.player(player).sendTitlePart(TitlePart.SUBTITLE, Component.text(message));
        audience.player(player).sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }
}
