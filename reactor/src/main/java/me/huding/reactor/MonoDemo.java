package me.huding.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class MonoDemo {

    public static void main(String[] args) {
        Mono<String> mono = Mono.empty();
        mono.subscribe(System.out::println);

        Mono<String> mono1 = Mono.fromSupplier(() -> "mono");

        mono1.subscribe(System.out::println);

        Mono.fromFuture(CompletableFuture.supplyAsync(() -> "mono1")).subscribe(System.out::println);

        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.completeExceptionally(new IllegalArgumentException("error"));

        Mono.fromFuture(completableFuture).subscribe(e -> {
            System.out.println("success");
        }, throwable -> {
            System.out.println("failure.");
            System.out.println(throwable);
        });

        System.out.println("-----------------------------------");
        Mono.fromFuture(CompletableFuture.completedFuture(true)).subscribe(e -> {
            System.out.println("success");
        }, throwable -> {
            System.out.println("failure.");
            System.out.println(throwable);
        }, () -> {
            System.out.println("completed....");
        });


        System.out.println("-----------------------------------");
        Mono.fromSupplier(() -> "value").subscribe(e -> {
            System.out.println("success");
        }, throwable -> {
            System.out.println("failure.");
            System.out.println(throwable);
        }, () -> {
            System.out.println("completed....");
        }, e -> {
           e.request(1);
        });

        System.out.println("################################");
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error));


        System.out.println("//////////////////////////////");
        ints = Flux.range(1, 50);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10));
    }
}
