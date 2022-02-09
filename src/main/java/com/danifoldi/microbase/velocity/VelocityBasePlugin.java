package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BasePlugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.meta.PluginDependency;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class VelocityBasePlugin implements BasePlugin {
    private final PluginContainer plugin;

    VelocityBasePlugin(PluginContainer plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return plugin.getDescription().getName().orElse("");
    }

    @Override
    public String description() {
        return plugin.getDescription().getDescription().orElse("");
    }

    @Override
    public String main() {
        return plugin.getInstance().map(Object::getClass).map(Class::getName).orElse("");
    }

    @Override
    public String version() {
        return plugin.getDescription().getVersion().orElse("");
    }

    @Override
    public List<String> authors() {
        return plugin.getDescription().getAuthors();
    }

    @Override
    public List<String> dependencies() {
        return plugin.getDescription().getDependencies().stream().filter(p -> !p.isOptional()).map(PluginDependency::getId).toList();
    }

    @Override
    public List<String> softDependencies() {
        return plugin.getDescription().getDependencies().stream().filter(PluginDependency::isOptional).map(PluginDependency::getId).toList();
    }
}
