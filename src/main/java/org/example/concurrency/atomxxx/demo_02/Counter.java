package org.example.concurrency.atomxxx.demo_02;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public int increment() {
        return count.incrementAndGet(); // 原子性操作
    }

    public int decrement() {
        return count.decrementAndGet(); // 原子性操作
    }

    public int getCount() {
        return count.get(); // 原子性操作
    }

    // 复合操作：增加并获取当前值
    public int incrementAndGetCurrent() {
        increment(); // 增加
        return getCount(); // 获取当前值
    }
}
