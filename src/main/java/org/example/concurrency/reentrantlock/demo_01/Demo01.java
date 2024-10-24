package org.example.concurrency.reentrantlock.demo_01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock用于替代synchronized
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 */
public class Demo01 {

    Lock lock = new ReentrantLock();

    public void test1() {
        // 加锁要放在try的外面，因为finally会释放锁
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);

                TimeUnit.SECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { //必须手动释放
            lock.unlock();
        }
    }

    public void test2() {
        lock.lock();
        System.out.println("execute test2........");
        lock.unlock();
    }

    public static void main(String[] args) {
        Demo01 rl = new Demo01();
        new Thread(rl::test1).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::test2).start();
    }
}