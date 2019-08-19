package me.huding.common.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureDemo {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {

            private AtomicInteger NUM = new AtomicInteger();

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "test-test" + NUM.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
        AtomicInteger seed = new AtomicInteger();
        List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompletableFuture<List<Result>> completableFuture = CompletableFuture.supplyAsync(() -> {
                // 生产结果
                List<Result> results = new ArrayList<>();
                for (int k = 0; k < 100; k++) {
                    results.add(new Result(seed.incrementAndGet()));
                }
                return results;
            }, executorService);

            CompletableFuture<Boolean> completableFuture2 = completableFuture.thenApply((results) -> {
                // TODO 插入数据库
                return insert2Database(results);
            }).exceptionally((throwable) -> {
                // TODO 处理数据库插入失败问题
                throwable.printStackTrace();
                return false;
            });

            completableFutures.add(completableFuture2);
        }

        // 等待所有任务结束
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .whenComplete((result,throwable) ->{
                    if (throwable != null) {
                        // 有异常,处理异常
                        throwable.printStackTrace();
                    } else {
                        // 所有任务是否执行成功 ？
                    }

                });
    }

    private static boolean insert2Database(List<Result> results) {
        // TODO 插入数据库逻辑
        System.out.println(results);
        return true;
    }


    static class Result {
        int value;

        public Result(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "value=" + value +
                    '}';
        }
    }

}
