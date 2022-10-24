package com.danifoldi.microbase.waterfall;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
    public void actionbar(String message) {
        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
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
        player.sendTitle(ProxyServer.getInstance().createTitle().title(new TextComponent(message)).fadeIn(fadeIn).stay(stay).fadeOut(fadeOut));
    }

    @Override
    public void subtitle(String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(ProxyServer.getInstance().createTitle().subTitle(new TextComponent(message)).fadeIn(fadeIn).stay(stay).fadeOut(fadeOut));
    }
}
