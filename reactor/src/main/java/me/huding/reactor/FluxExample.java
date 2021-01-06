package me.huding.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 创建 Flux
 * 有多种不同的方式可以创建 Flux 序列。
 *
 * Flux 类的静态方法
 * 第一种方式是通过 Flux 类中的静态方法。
 *
 * just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
 * fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
 * empty()：创建一个不包含任何元素，只发布结束消息的序列。
 * error(Throwable error)：创建一个只包含错误消息的序列。
 * never()：创建一个不包含任何消息通知的序列。
 * range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
 * interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
 * intervalMillis(long period)和 intervalMillis(long delay, long period)：与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。
 */
public class FluxExample {


    public static void simple() throws InterruptedException {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        Flux.interval(Duration.ofMillis(1000)).subscribe(System.out::println);
        Thread.sleep(100000);
    }

    public static void main(String[] args) throws InterruptedException {
        simple();
    }
}
