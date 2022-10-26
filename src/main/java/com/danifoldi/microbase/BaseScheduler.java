package com.danifoldi.microbase;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public interface BaseScheduler {
    static void cancel(BaseTask task) {
        task.cancel();
    }

    BaseTask runTask(Runnable task);

    BaseTask runTaskAfter(Runnable task, int amount, TimeUnit unit);

    default BaseTask runTaskEvery(Runnable task, int amount, TimeUnit unit) {
        return runTaskEvery(task, amount, unit, 0);
    }

    BaseTask runTaskEvery(Runnable task, int amount, TimeUnit unit, int initialDelay);

    interface BaseTask {
        void cancel();
    }
}
