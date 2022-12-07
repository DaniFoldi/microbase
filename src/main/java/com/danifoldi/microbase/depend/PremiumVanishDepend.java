package com.danifoldi.microbase.depend;

import com.danifoldi.microbase.BasePlayer;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.util.ClassUtil;

import java.util.List;
import java.util.UUID;

public class PremiumVanishDepend {
    public static Boolean bvInstalled = null;
    public static Boolean vInstalled = null;

    private static void init() {
        bvInstalled = ClassUtil.check("de.myzelyam.api.vanish.BungeeVanishAPI");
        vInstalled = ClassUtil.check("de.myzelyam.api.vanish.VanishAPI");
    }

    public static boolean vanished(UUID uuid) {
        if (bvInstalled == null || vInstalled == null) {
            init();
        }

        if (bvInstalled) {
            return de.myzelyam.api.vanish.BungeeVanishAPI.getInvisiblePlayers().contains(uuid);
        } else if (vInstalled) {
            return de.myzelyam.api.vanish.VanishAPI.getInvisiblePlayers().contains(uuid);
        } else {
            Microbase.logger.warning("vanished checking requires PremiumVanish on your platform");
            return false;
        }
    }

    public static boolean isVisibleTo(UUID target, UUID eyes) {
        if (bvInstalled == null || vInstalled == null) {
            init();
        }

        if (bvInstalled) {
            return de.myzelyam.api.vanish.BungeeVanishAPI.canSee(net.md_5.bungee.api.ProxyServer.getInstance().getPlayer(target), net.md_5.bungee.api.ProxyServer.getInstance().getPlayer(eyes));
        } else if (vInstalled) {
            return de.myzelyam.api.vanish.VanishAPI.canSee(org.bukkit.Bukkit.getPlayer(target), org.bukkit.Bukkit.getPlayer(eyes));
        } else {
            Microbase.logger.warning("visibility requires PremiumVanish on your platform");
            return true;
        }
    }

    private PremiumVanishDepend() {
        throw new UnsupportedOperationException();
    }
}
