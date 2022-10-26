package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BaseScheduler;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

public class BungeecordBaseScheduler implements BaseScheduler {

    private final Plugin plugin;
    private final TaskScheduler scheduler;

    public BungeecordBaseScheduler(Plugin plugin, TaskScheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

    @Override
    public BaseTask runTask(Runnable task) {
        return scheduler.runAsync(plugin, task)::cancel;
    }

    @Override
    public BaseTask runTaskAfter(Runnable task, int amount, TimeUnit unit) {
        return scheduler.schedule(plugin, task, amount, unit)::cancel;
    }

    @Override
    public BaseTask runTaskEvery(Runnable task, int amount, TimeUnit unit, int initialDelay) {
        return scheduler.schedule(plugin, task, initialDelay, amount, unit)::cancel;
    }
}
