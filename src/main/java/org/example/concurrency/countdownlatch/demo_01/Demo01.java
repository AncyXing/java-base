package org.example.concurrency.countdownlatch.demo_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，可以考虑countdownlatch/cyclicbarrier/semaphore
 */
public class Demo01 {
    List<Integer> dataList = new ArrayList<>();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public void add() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " add(),当前个数" + dataList.size());
            dataList.add(i);
            if (i == 4) {
                countDownLatch.countDown();
            }
        }
    }

    public void getSize() {
        while (dataList.size() != 5) {
            try {
                // 等待
                countDownLatch.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("元素个数达到5，" + Thread.currentThread().getName() + "运行结束");
    }


    public static void main(String[] args) throws InterruptedException {
        Demo01 demo01 = new Demo01();
        Thread t1 = new Thread(demo01::add, "T1");
        Thread t2 = new Thread(demo01::getSize, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
