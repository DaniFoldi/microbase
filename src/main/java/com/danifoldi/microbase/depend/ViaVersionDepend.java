package com.danifoldi.microbase.depend;

import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.util.ClassUtil;

import java.util.UUID;

public class ViaVersionDepend {
    public static final boolean installed = ClassUtil.check("com.viaversion.viaversion.api.Via");

    public static int getProtocol(UUID uuid) {
        if (installed) {
            return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(uuid);
        } else {
            Microbase.logger.warning("protocol detection requires ViaVersion on your platform");
            return 0;
        }
    }

    private ViaVersionDepend() {
        throw new UnsupportedOperationException();
    }
}
