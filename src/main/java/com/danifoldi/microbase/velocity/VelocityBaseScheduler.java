package com.danifoldi.microbase.velocity;

import com.danifoldi.microbase.BaseScheduler;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;

public class VelocityBaseScheduler implements BaseScheduler {

    private final Scheduler scheduler;
    private final Plugin plugin;

    public VelocityBaseScheduler(Plugin plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

    @Override
    public BaseTask runTask(Runnable task) {
        return scheduler.buildTask(plugin, task).schedule()::cancel;
    }

    @Override
    public BaseTask runTaskAfter(Runnable task, int amount, TimeUnit unit) {
        return scheduler.buildTask(plugin, task).repeat(amount, unit).schedule()::cancel;
    }

    @Override
    public BaseTask runTaskEvery(Runnable task, int amount, TimeUnit unit, int initialDelay) {
        return scheduler.buildTask(plugin, task).delay(initialDelay, unit).repeat(amount, unit).schedule()::cancel;
    }
}
