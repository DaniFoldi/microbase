package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.BasePlatform;
import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.BasePlugin;
import com.danifoldi.microbase.BaseSender;
import com.danifoldi.microbase.BaseServer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpigotPlatform {
    public static BaseMessage baseMessage() {
        return new SpigotBaseMessage();
    }

    public static BasePlatform toBasePlatform(Server server) {
        return new SpigotBasePlatform(server);
    }

    public static BasePlayer toBasePlayer(Player player) {
        return new SpigotBasePlayer(player);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new SpigotBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new SpigotBaseSender(sender);
    }

    public static BaseServer toBaseServer(Server server) {
        return new SpigotBaseServer(server);
    }

    private SpigotPlatform() {
        throw new UnsupportedOperationException();
    }
}
