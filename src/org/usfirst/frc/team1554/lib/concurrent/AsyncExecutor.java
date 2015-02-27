package org.usfirst.frc.team1554.lib.concurrent;

import org.usfirst.frc.team1554.lib.common.Disposable;
import org.usfirst.frc.team1554.lib.common.ex.TimingException;
import org.usfirst.frc.team1554.lib.meta.Author;
import org.usfirst.frc.team1554.lib.meta.Beta;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Beta
@Author(name = "Matthew Crocco", msg = "matthewcrocco@gmail.com")
public class AsyncExecutor implements Disposable {

    private final ExecutorService executor;

    public AsyncExecutor(int maxConcurrent) {
        this.executor = Executors.newFixedThreadPool(maxConcurrent, r -> {
            final Thread t = new Thread(r, "RoboAsync");
            t.setDaemon(true);
            return t;
        });
    }

    public <T> AsyncResult<T> submit(AsyncTask<T> task) {
        if (isShutdown())
            throw new TimingException("Cannot Execute Async Task on a Shutdown Executor!");

        return new AsyncResult<>(this.executor.submit(task));
    }

    public boolean isShutdown() {
        return this.executor.isShutdown();
    }

    @Override
    public void dispose() {
        this.executor.shutdown();
    }

}
