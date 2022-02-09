package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BasePlugin;
import org.bukkit.plugin.Plugin;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class SpigotBasePlugin implements BasePlugin {
    private final Plugin plugin;

    SpigotBasePlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return plugin.getDescription().getName();
    }

    @Override
    public String description() {
        return plugin.getDescription().getDescription();
    }

    @Override
    public String main() {
        return plugin.getDescription().getMain();
    }

    @Override
    public String version() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public List<String> authors() {
        return plugin.getDescription().getAuthors();
    }

    @Override
    public List<String> dependencies() {
        return plugin.getDescription().getDepend();
    }

    @Override
    public List<String> softDependencies() {
        return plugin.getDescription().getSoftDepend();
    }
}
