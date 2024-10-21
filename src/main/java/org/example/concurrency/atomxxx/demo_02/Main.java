package org.example.concurrency.atomxxx.demo_02;

import java.util.concurrent.TimeUnit;

/**
 * 假设一个线程在调用 increment() 后还没来得及调用 getCount()，
 * 另一个线程可能已经执行了 decrement()。这会导致最终结果与预期不符。
 */
public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // 创建多个线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                // 添加日志输出
                System.out.println("Thread 1 incremented: " + counter.getCount());
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
                // 添加日志输出
                System.out.println("Thread 2 decremented: " + counter.getCount());
            }
        });

        t1.start();
        t2.start();

        try {
//            t1.join();
//            t2.join();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}
