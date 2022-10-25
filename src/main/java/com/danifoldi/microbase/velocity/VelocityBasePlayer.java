package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;

public class VelocityBasePlayer extends VelocityBaseSender implements BasePlayer {
    private final Player player;

    VelocityBasePlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean vanished() {
        return PremiumVanishDepend.vanished(player.getUniqueId());
    }

    @Override
    public int protocol() {
        return player.getProtocolVersion().getProtocol();
    }

    @Override
    public int ping() {
        return (int)player.getPing();
    }

    @Override
    public String locale() {
        return Optional.ofNullable(player.getEffectiveLocale()).map(Locale::getDisplayName).orElse("");
    }

    @Override
    public void run(String command) {
        ((ProxyServer)Microbase.getPlatform().raw()).getCommandManager().executeAsync(player, command);
    }

    @Override
    public void actionbar(Component message) {
        player.sendActionBar(message);
    }

    @Override
    public void chat(String message) {
        player.spoofChatInput(message);
    }

    @Override
    public void connect(BaseServer server) {
        player.createConnectionRequest(((ProxyServer)Microbase.getPlatform().raw()).getServer(server.name()).orElse(null)).fireAndForget();
    }

    @Override
    public BaseServer connectedTo() {
        return player.getCurrentServer().map(ServerConnection::getServer).map(VelocityBaseServer::new).orElse(null);
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
