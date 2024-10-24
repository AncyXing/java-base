package org.example.concurrency.waitandnotify.demo_03;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class Demo03 {
    List<Integer> dataList = new ArrayList<>();
    Object lock = new Object();

    public void add() {
        // notify需要配合synchronized一起使用，因为notify执行之后会释放锁
        synchronized (lock) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " add(),当前个数" + dataList.size());
                dataList.add(i);
                if (i == 4) {
                    // 通知等待的其他线程,不是马上释放锁，要等当前线程执行完后
                    lock.notify();
                    try {
                        // 这里先释放锁 等待线程2通知再往下运行
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void getSize() {
        // wait需要配合synchronized一起使用，因为wait会释放锁
        synchronized (lock) {
            while (dataList.size() != 5) {
                try {
                    // 释放锁 等待其他线程通知唤醒
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            lock.notify();
            System.out.println("元素个数达到5，" + Thread.currentThread().getName() + "运行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Demo03 demo01 = new Demo03();
        Thread t1 = new Thread(demo01::add, "T1");
        Thread t2 = new Thread(demo01::getSize, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
