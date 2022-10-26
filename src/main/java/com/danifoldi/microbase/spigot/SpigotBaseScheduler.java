package com.danifoldi.microbase.spigot;

import com.danifoldi.microbase.BaseScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SpigotBaseScheduler implements BaseScheduler {

    private final JavaPlugin plugin;

    public SpigotBaseScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public BaseTask runTask(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, task)::cancel;
    }

    @Override
    public BaseTask runTaskAfter(Runnable task, int amount, TimeUnit unit) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, Duration.of(amount, unit.toChronoUnit()).toSeconds() * 20)::cancel;
    }

    @Override
    public BaseTask runTaskEvery(Runnable task, int amount, TimeUnit unit, int initialDelay) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, Duration.of(amount, unit.toChronoUnit()).toSeconds() * 20, Duration.of(amount, unit.toChronoUnit()).toSeconds() * 20)::cancel;
    }
}
