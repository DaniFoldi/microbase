package com.danifoldi.microbase.depend;

import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.util.ClassUtil;

import java.util.UUID;

public class PremiumVanishDepend {
    public static final boolean vInstalled = ClassUtil.check("de.myzelyam.api.vanish.VanishAPI");
    public static final boolean bvInstalled = ClassUtil.check("de.myzelyam.api.vanish.BungeeVanishAPI");

    public static boolean vanished(UUID uuid) {
        if (vInstalled) {
            return de.myzelyam.api.vanish.VanishAPI.getInvisiblePlayers().contains(uuid);
        } else if (bvInstalled) {
            return de.myzelyam.api.vanish.BungeeVanishAPI.getInvisiblePlayers().contains(uuid);
        } else {
            Microbase.logger.warning("vanished checking requires PremiumVanish on your platform");
            return false;
        }
    }

    private PremiumVanishDepend() {
        throw new UnsupportedOperationException();
    }
}
