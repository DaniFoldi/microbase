package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BasePlugin;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class BungeecordBasePlugin implements BasePlugin {
    private final Plugin plugin;

    BungeecordBasePlugin(Plugin plugin) {
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
        return Arrays.stream(plugin.getDescription().getAuthor().split(",")).toList();
    }

    @Override
    public List<String> dependencies() {
        return plugin.getDescription().getDepends().stream().toList();
    }

    @Override
    public List<String> softDependencies() {
        return plugin.getDescription().getSoftDepends().stream().toList();
    }

    @Override
    public Object raw() {
        return plugin;
    }
}
