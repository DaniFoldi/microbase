package com.danifoldi.microbase;

import com.danifoldi.microbase.bungeecord.BungeecordPlatform;
import com.danifoldi.microbase.paper.PaperPlatform;
import com.danifoldi.microbase.spigot.SpigotPlatform;
import com.danifoldi.microbase.util.ClassUtil;
import com.danifoldi.microbase.util.Pair;
import com.danifoldi.microbase.velocity.VelocityPlatform;
import com.danifoldi.microbase.waterfall.WaterfallPlatform;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Microbase {

    public static final Logger logger = Logger.getLogger("Microbase");
    public static final MicrobasePlatformType platformType;
    private static final List<Pair<String, MicrobasePlatformType>> knownPlatforms = List.of(
            Pair.of("com.velocitypowered.proxy.ProxyServer", MicrobasePlatformType.VELOCITY),
            Pair.of("io.github.waterfallmc.waterfall.event.ConnectionInitEvent", MicrobasePlatformType.WATERFALL),
            Pair.of("net.md_5.bungee.BungeeCord", MicrobasePlatformType.BUNGEECORD),
            Pair.of("io.papermc.paper.event.block.TargetHitEvent", MicrobasePlatformType.PAPER),
            Pair.of("org.spigotmc.event.player.PlayerSpawnLocationEvent", MicrobasePlatformType.SPIGOT)
    );

    static {
        platformType = knownPlatforms.stream().filter(p -> ClassUtil.check(p.a())).findFirst().map(Pair::b).orElse(MicrobasePlatformType.UNKNOWN);
        if (platformType == MicrobasePlatformType.UNKNOWN) {
            logger.severe("Platform is not supported");
        }
    }

    private static Object platform;
    private static Object plugin;
    private static Path datafolder;
    private static ExecutorService threadPool;
    private static final Map<String, Object> commandCache = new ConcurrentHashMap<>();

    public static void setup(Object platform, Object plugin, Path datafolder, ExecutorService threadPool) {
        Microbase.platform = platform;
        Microbase.plugin = plugin;
        Microbase.datafolder = datafolder;
        Microbase.threadPool = threadPool;
    }

    public static Object getPlatform() {
        return platform;
    }

    public static Object getPlugin() {
        return plugin;
    }

    public static Path getDatafolder() {
        return datafolder;
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static<T> T addCommand(List<String> aliases, T command) {
        aliases.forEach(a -> commandCache.put(a, command));
        return command;
    }

    public static Object removeCommand(String name) {
        return commandCache.remove(name);
    }

    public static<T> BasePlatform toBasePlatform(T pl) {
        switch (platformType) {
            case BUNGEECORD -> {
                if (pl instanceof net.md_5.bungee.api.ProxyServer cPlatform) {
                    return BungeecordPlatform.toBasePlatform(cPlatform);
                }
            }
            case PAPER -> {
                if (pl instanceof org.bukkit.Server cPlatform) {
                    return PaperPlatform.toBasePlatform(cPlatform);
                }
            }
            case SPIGOT -> {
                if (pl instanceof org.bukkit.Server cPlatform) {
                    return SpigotPlatform.toBasePlatform(cPlatform);
                }
            }
            case VELOCITY -> {
                if (pl instanceof com.velocitypowered.api.proxy.ProxyServer cPlatform) {
                    return VelocityPlatform.toBasePlatform(cPlatform);
                }
            }
            case WATERFALL -> {
                if (pl instanceof net.md_5.bungee.api.ProxyServer cPlatform) {
                    return WaterfallPlatform.toBasePlatform(cPlatform);
                }
            }
        }
        logger.warning("Microbase.toBasePlatform called with %s which cannot be converted (platform: %s)".formatted(platformType.getClass().getName(), platformType));
        return null;
    }

    public static<T> BasePlayer toBasePlayer(T player) {
        switch (platformType) {
            case BUNGEECORD -> {
                if (player instanceof net.md_5.bungee.api.connection.ProxiedPlayer cPlayer) {
                    return BungeecordPlatform.toBasePlayer(cPlayer);
                }
            }
            case PAPER -> {
                if (player instanceof org.bukkit.entity.Player cPlayer) {
                    return PaperPlatform.toBasePlayer(cPlayer);
                }
            }
            case SPIGOT -> {
                if (player instanceof org.bukkit.entity.Player cPlayer) {
                    return SpigotPlatform.toBasePlayer(cPlayer);
                }
            }
            case VELOCITY -> {
                if (player instanceof com.velocitypowered.api.proxy.Player cPlayer) {
                    return VelocityPlatform.toBasePlayer(cPlayer);
                }
            }
            case WATERFALL -> {
                if (player instanceof net.md_5.bungee.api.connection.ProxiedPlayer cPlayer) {
                    return WaterfallPlatform.toBasePlayer(cPlayer);
                }
            }
        }
        logger.warning("Microbase.toBasePlayer called with %s which cannot be converted (platform: %s)".formatted(player.getClass().getName(), platformType));
        return null;
    }

    public static<T> BasePlugin toBasePlugin(T plugin) {
        switch (platformType) {
            case BUNGEECORD -> {
                if (plugin instanceof net.md_5.bungee.api.plugin.Plugin cPlugin) {
                    return BungeecordPlatform.toBasePlugin(cPlugin);
                }
            }
            case PAPER -> {
                if (plugin instanceof org.bukkit.plugin.Plugin cPlugin) {
                    return PaperPlatform.toBasePlugin(cPlugin);
                }
            }
            case SPIGOT -> {
                if (plugin instanceof org.bukkit.plugin.Plugin cPlugin) {
                    return SpigotPlatform.toBasePlugin(cPlugin);
                }
            }
            case VELOCITY -> {
                if (plugin instanceof com.velocitypowered.api.plugin.PluginContainer cPlugin) {
                    return VelocityPlatform.toBasePlugin(cPlugin);
                }
            }
            case WATERFALL -> {
                if (plugin instanceof net.md_5.bungee.api.plugin.Plugin cPlugin) {
                    return WaterfallPlatform.toBasePlugin(cPlugin);
                }
            }
        }
        logger.warning("Microbase.toBasePlugin called with %s which cannot be converted (platform: %s)".formatted(plugin.getClass().getName(), platformType));
        return null;
    }

    public static<T> BaseSender toBaseSender(T sender) {
        switch (platformType) {
            case BUNGEECORD -> {
                if (sender instanceof net.md_5.bungee.api.CommandSender cSender) {
                    return BungeecordPlatform.toBaseSender(cSender);
                }
            }
            case PAPER -> {
                if (sender instanceof org.bukkit.command.CommandSender cSender) {
                    return PaperPlatform.toBaseSender(cSender);
                }
            }
            case SPIGOT -> {
                if (sender instanceof org.bukkit.command.CommandSender cSender) {
                    return SpigotPlatform.toBaseSender(cSender);
                }
            }
            case VELOCITY -> {
                if (sender instanceof com.velocitypowered.api.command.CommandSource cSender) {
                    return VelocityPlatform.toBaseSender(cSender);
                }
            }
            case WATERFALL -> {
                if (sender instanceof net.md_5.bungee.api.CommandSender cSender) {
                    return WaterfallPlatform.toBaseSender(cSender);
                }
            }
        }
        logger.warning("Microbase.toBaseSender called with %s which cannot be converted (platform: %s)".formatted(sender.getClass().getName(), platformType));
        return null;
    }

    public static<T> BaseServer toBaseServer(T server) {
        switch (platformType) {
            case BUNGEECORD -> {
                if (server instanceof net.md_5.bungee.api.config.ServerInfo cServer) {
                    return BungeecordPlatform.toBaseServer(cServer);
                }
            }
            case PAPER -> {
                if (server instanceof org.bukkit.Server cServer) {
                    return PaperPlatform.toBaseServer(cServer);
                }
            }
            case SPIGOT -> {
                if (server instanceof org.bukkit.Server cServer) {
                    return SpigotPlatform.toBaseServer(cServer);
                }
            }
            case VELOCITY -> {
                if (server instanceof com.velocitypowered.api.proxy.server.RegisteredServer cPlayer) {
                    return VelocityPlatform.toBaseServer(cPlayer);
                }
            }
            case WATERFALL -> {
                if (server instanceof net.md_5.bungee.api.config.ServerInfo cServer) {
                    return WaterfallPlatform.toBaseServer(cServer);
                }
            }
        }
        logger.warning("Microbase.toBaseServer called with %s which cannot be converted (platform: %s)".formatted(server.getClass().getName(), platformType));
        return null;
    }
}
