package org.example.concurrency.reentrantlock.demo_03;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 */
public class Demo03 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        // 线程1一直休眠
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        // 线程2支持等待锁时被中断
        Thread t2 = new Thread(() -> {
            try {
                // 先获得锁再往下执行
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                // 获取锁失败
                throw new RuntimeException(e);
            }
            try {
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("等待过程中响应中断，不进行等待了，interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打断线程2的等待
        t2.interrupt();
    }
}