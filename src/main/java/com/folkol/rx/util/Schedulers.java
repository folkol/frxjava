package com.folkol.rx.util;

import com.folkol.rx.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class Schedulers
{
    private static final AtomicLong count = new AtomicLong();
    private static final ExecutorService io = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("schedulers-io-" + count.incrementAndGet());
        return thread;
    });
    private static final ExecutorService computation = Executors.newFixedThreadPool(10, r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("schedulers-io-" + count.incrementAndGet());
        return thread;
    });

    public static Scheduler newThread()
    {
        ExecutorService es = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("schedulers-new-thread-" + count.incrementAndGet());
            return thread;
        });
        return es::submit;
    }

    public static Scheduler io()
    {
        return io::submit;
    }

    public static Scheduler computation()
    {
        return computation::submit;
    }

    public static Scheduler immediate()
    {
        return Runnable::run;
    }
}