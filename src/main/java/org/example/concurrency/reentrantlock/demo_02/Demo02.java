package org.example.concurrency.reentrantlock.demo_02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用tryLock
 */
public class Demo02 {

    Lock lock = new ReentrantLock();

    public void test1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(i);

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { //必须手动释放
            lock.unlock();
        }
    }

    /**
     * 可以使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unclock的处理，必须放到finally中
     */
    public void test2() {
        boolean locked = false;
        try {
//            locked = lock.tryLock(6, TimeUnit.SECONDS);
            locked = lock.tryLock();
            System.out.println("test2...." + locked);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Demo02 rl = new Demo02();
        new Thread(rl::test1).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::test2).start();
    }
}