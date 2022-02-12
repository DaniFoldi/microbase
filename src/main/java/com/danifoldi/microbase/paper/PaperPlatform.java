package com.danifoldi.microbase.paper;

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

public class PaperPlatform {
    public static BaseMessage baseMessage() {
        return new PaperBaseMessage();
    }

    public static BasePlatform toBasePlatform(Server server) {
        return new PaperBasePlatform(server);
    }

    public static BasePlayer toBasePlayer(Player player) {
        return new PaperBasePlayer(player);
    }

    public static BasePlugin toBasePlugin(Plugin plugin) {
        return new PaperBasePlugin(plugin);
    }

    public static BaseSender toBaseSender(CommandSender sender) {
        return new PaperBaseSender(sender);
    }

    public static BaseServer toBaseServer(Server server) {
        return new PaperBaseServer(server);
    }

    private PaperPlatform() {
        throw new UnsupportedOperationException();
    }
}
