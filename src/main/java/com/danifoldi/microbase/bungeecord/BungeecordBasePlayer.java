package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BaseScheduler;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.depend.PremiumVanishDepend;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BungeecordBasePlayer extends BungeecordBaseSender implements BasePlayer {
    private final ProxiedPlayer player;
    private final BungeeAudiences audience;

    BungeecordBasePlayer(ProxiedPlayer player, BungeeAudiences audience) {
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
        return new BungeecordBaseServer(player.getServer().getInfo(), audience);
    }

    @Override
    public void title(BaseMessage message, int fadeIn, int stay, int fadeOut) {
        audience.player(player).sendTitlePart(TitlePart.TITLE, message.convert());
        audience.player(player).sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }

    @Override
    public void subtitle(BaseMessage message, int fadeIn, int stay, int fadeOut) {
        audience.player(player).sendTitlePart(TitlePart.SUBTITLE, message.convert());
        audience.player(player).sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.of(fadeIn, ChronoUnit.SECONDS), Duration.of(stay, ChronoUnit.SECONDS), Duration.of(fadeOut, ChronoUnit.SECONDS)));
    }

    @Override
    public void kick(BaseMessage message) {
        player.disconnect(BungeeComponentSerializer.get().serialize(message.convert()));
    }

    @Override
    public void bossbar(BaseMessage message, int time, float startFill, float endFill, String color, String style) {
        BossBar bossBar = BossBar.bossBar(message.convert(), startFill, BossBar.Color.valueOf(color.toUpperCase(Locale.ROOT)), BossBar.Overlay.valueOf(style.toUpperCase(Locale.ROOT)));
        audience.player(player).showBossBar(bossBar);
        AtomicInteger iter = new AtomicInteger();
        BaseScheduler.BaseTask updateBossBar = Microbase.getScheduler().runTaskEvery(() -> {
            bossBar.progress(startFill + (endFill - startFill) * (float)iter.incrementAndGet() / (float)(time * 20));
        }, 50, TimeUnit.MILLISECONDS, 50);
        Microbase.getScheduler().runTaskAfter(() -> {
            updateBossBar.cancel();
            audience.player(player).hideBossBar(bossBar);
        }, time, TimeUnit.SECONDS);
    }
}
