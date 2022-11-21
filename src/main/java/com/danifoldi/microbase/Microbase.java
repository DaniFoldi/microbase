package com.danifoldi.microbase;

import com.danifoldi.dml.exception.DmlParseException;
import com.danifoldi.microbase.bungeecord.BungeecordBaseScheduler;
import com.danifoldi.microbase.bungeecord.BungeecordPlatform;
import com.danifoldi.microbase.paper.PaperBaseScheduler;
import com.danifoldi.microbase.paper.PaperPlatform;
import com.danifoldi.microbase.spigot.SpigotBaseScheduler;
import com.danifoldi.microbase.spigot.SpigotPlatform;
import com.danifoldi.microbase.util.ClassUtil;
import com.danifoldi.microbase.util.DmlUtil;
import com.danifoldi.microbase.util.FileUtil;
import com.danifoldi.microbase.util.Pair;
import com.danifoldi.microbase.velocity.VelocityBaseScheduler;
import com.danifoldi.microbase.velocity.VelocityPlatform;
import com.danifoldi.microbase.waterfall.WaterfallBaseScheduler;
import com.danifoldi.microbase.waterfall.WaterfallPlatform;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.bukkit.block.structure.Mirror;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Microbase {

    public static final MicrobasePlatformType platformType;
    private static final List<Pair<String, MicrobasePlatformType>> knownPlatforms = List.of(
            Pair.of("com.velocitypowered.api.proxy.ProxyServer", MicrobasePlatformType.VELOCITY),
            Pair.of("io.github.waterfallmc.waterfall.event.ConnectionInitEvent", MicrobasePlatformType.WATERFALL),
            Pair.of("net.md_5.bungee.BungeeCord", MicrobasePlatformType.BUNGEECORD),
            Pair.of("io.papermc.paper.event.block.TargetHitEvent", MicrobasePlatformType.PAPER),
            Pair.of("org.spigotmc.event.player.PlayerSpawnLocationEvent", MicrobasePlatformType.SPIGOT)
    );
    public static Logger logger = Logger.getLogger("(Microbase)");
    private static Object platform;
    private static Object plugin;
    private static Path datafolder;

    private static Map<String, ExecutorService> threadPools;
    private static Function<String, String> messageProvider;

    static {
        platformType = knownPlatforms.stream().filter(p -> ClassUtil.check(p.a())).findFirst().map(Pair::b).orElse(MicrobasePlatformType.UNKNOWN);
        if (platformType == MicrobasePlatformType.UNKNOWN) {
            logger.severe("Platform is not supported");
        }
    }

    @Deprecated
    public static void setup(Object platform, Object plugin, Path datafolder, ExecutorService threadPool, Function<String, String> messageProvider) {
        setup(platform, plugin, datafolder, messageProvider);
        threadPools.put("microbase", threadPool);
    }

    @Deprecated
    public static void setup(Object platform, Object plugin, Path datafolder, ExecutorService threadPool, String messageFile) {
        setup(platform, plugin, datafolder, messageFile);
        threadPools.put("microbase", threadPool);
    }

    public static void setup(Object platform, Object plugin, Path datafolder, Function<String, String> messageProvider) {
        Microbase.platform = platform;
        Microbase.plugin = plugin;
        Microbase.datafolder = datafolder;
        Microbase.messageProvider = messageProvider;

        logger = Logger.getLogger(Microbase.getPlugin().name() + "(Mb)");
    }

    public static void setup(Object platform, Object plugin, Path datafolder, String messageFile) {
        Map<String, String> builtProvider;
        try {
            Microbase.messageProvider = new ConcurrentHashMap<>(DmlUtil.flattenStrings(FileUtil.ensureDmlFile(datafolder, messageFile)))::get;
        } catch (IOException | DmlParseException e) {
            logger.warning("Could not load message file");
            Microbase.messageProvider = key -> "";
        }
        setup(platform, plugin, datafolder, messageProvider);
    }

    public static BasePlatform getPlatform() {
        return Microbase.toBasePlatform(platform);
    }

    public static BasePlugin getPlugin() {
        return Microbase.toBasePlugin(plugin);
    }

    public static Path getDatafolder() {
        return datafolder;
    }

    @Deprecated
    public static ExecutorService getThreadPool() {
        return getThreadPool("microbase");
    }

    public static ExecutorService getThreadPool(String namespace) {
        if (threadPools.containsKey(namespace)) {
            return threadPools.get(namespace);
        }

        threadPools.put(namespace, Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setNameFormat(Microbase.getPlugin().name() + " Thread Pool - %1$d")
                .build()));
        return threadPools.get(namespace);
    }

    public static Map<String, ExecutorService> getThreadPools() {
        return Map.copyOf(threadPools);
    }

    public static boolean shutdownThreadPool(String namespace, long timeoutMs, boolean force) {
        if (!threadPools.containsKey(namespace)) {
            Microbase.logger.warning("ThreadPool %s not found".formatted(namespace));
            return true;
        }
        ExecutorService threadPool = threadPools.get(namespace);
        if (threadPool.isShutdown()) {
            return true;
        }
        try {
            boolean terminated = threadPool.awaitTermination(timeoutMs, TimeUnit.MILLISECONDS);
            if (!force) {
                return terminated;
            } else {
                threadPool.shutdownNow();
                return true;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static String provideMessage(String key) {
        return messageProvider.apply(key);
    }

    public static BaseMessage baseMessage() {
        return new BaseMessage();
    }

    public static BaseScheduler getScheduler() {
        switch (platformType) {
            case BUNGEECORD -> {
                return new BungeecordBaseScheduler((net.md_5.bungee.api.plugin.Plugin)plugin, ((net.md_5.bungee.api.ProxyServer)platform).getScheduler());
            }
            case PAPER -> {
                return new PaperBaseScheduler((org.bukkit.plugin.java.JavaPlugin)plugin);
            }
            case SPIGOT -> {
                return new SpigotBaseScheduler((org.bukkit.plugin.java.JavaPlugin)plugin);
            }
            case VELOCITY -> {
                return new VelocityBaseScheduler((com.velocitypowered.api.plugin.Plugin)plugin, ((com.velocitypowered.api.proxy.ProxyServer)platform).getScheduler());
            }
            case WATERFALL -> {
                return new WaterfallBaseScheduler((net.md_5.bungee.api.plugin.Plugin)plugin, ((net.md_5.bungee.api.ProxyServer)platform).getScheduler());
            }
        }
        logger.warning("Microbase.getScheduler called (platform: %s)".formatted(platformType));
        return null;
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
