package me.huding.common.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureStudy {


    public static void main(String[] args) {
        CompletableFuture<Boolean>  completableFuture = CompletableFuture.supplyAsync(()-> {
            throw new RuntimeException("haha");
        });
    }
}
