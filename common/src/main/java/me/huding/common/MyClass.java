package me.huding.common;

import java.util.concurrent.atomic.AtomicLong;

public class MyClass {

    public AtomicLong atomicLong = new AtomicLong();


    public class A {

        public void add() {
            atomicLong.incrementAndGet();
        }
    }

    public class B {
        public void del() {
            atomicLong.decrementAndGet();
        }
    }
}
