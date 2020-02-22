package me.huding.common.function;

import java.util.Arrays;
import java.util.List;

public class Demo {

    @FunctionalInterface
    interface Handler<T> {

        void handle(T t);
    }

    public static <T> void print(List<T> list, Handler<List<T>> handler) {
        handler.handle(list);
    }

    public static void main(String[] args) {
        print(Arrays.asList(1, 2, 3), objects -> System.out.println(objects));

        print(Arrays.asList(1, 2, 3), objects -> {
            System.out.println("-----------");
            for (Integer integer : objects) {
                System.out.println(integer);
            }
            System.out.println("-----------");
        });
    }
}
