package me.huding.common.reference;

import sun.misc.Cleaner;

public class TestPhantomReference2 {


    public static class Chunk {

        private long id;

        private Cleaner cleaner;

        public Chunk(long id) {
            this.id = id;
            this.cleaner = Cleaner.create(this, () -> System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^" + id));
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            Chunk chunk = new Chunk(i);
        }

        System.gc();
        // 等待1000ms,测试是否会回收虚引用
        Thread.sleep(5000);
        System.out.println("-------------------------");
        System.gc();
        System.gc();
        Thread.sleep(5000);
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
//        System.out.println(chunk.isEnqueued());
        // 等待1000ms,测试是否会回收虚引用
        Thread.sleep(5000);
    }

}
