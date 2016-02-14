package com.akieus.misc;

import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> f = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                throw new NullPointerException();
            }
        });
        Thread.sleep(1000);
        System.out.println(f.isCancelled());
        System.out.println(f.isDone());
        System.out.println(f.cancel(false));
        // System.out.println(f.get());
    }
}
