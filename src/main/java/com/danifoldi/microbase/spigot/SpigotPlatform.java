package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import com.danifoldi.microbase.Microbase;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlatform {
    private static final BukkitAudiences audience = BukkitAudiences.builder((JavaPlugin)Microbase.getPlugin()).build();

    public static BaseMessage baseMessage() {
        return new SpigotBaseMessage();
    }

    public static BasePlatform toBasePlatform(Server server) {
        return new SpigotBasePlatform(server, audience);
    }

    public static BasePlayer toBasePlayer(Player player) {
        return new SpigotBasePlayer(player, audience);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new SpigotBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new SpigotBaseSender(sender, audience);
    }

    public static BaseServer toBaseServer(Server server) {
        return new SpigotBaseServer(server, audience);
    }

    private SpigotPlatform() {
        throw new UnsupportedOperationException();
    }
}
