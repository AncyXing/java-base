package org.example.concurrency.waitandnotify.demo_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class Demo01 {
    List<Integer> dataList = new ArrayList<>();
    public void add() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+" add(),当前个数"+dataList.size());
            dataList.add(i);
        }
    }

    public void getSize(){
        while (true) {
            System.out.println(Thread.currentThread().getName()+" getSize() "+dataList.size());
            if (dataList.size() == 5) {
                System.out.println("元素个数达到5");
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Demo01 demo01 = new Demo01();
        Thread t1 = new Thread(demo01::add, "T1");
        Thread t2 = new Thread(demo01::getSize,"T2");
        // 表示两个线程都处于就绪状态，等待cpu执行，具体执行哪个线程是随机的，不一定是调用start方法的先后顺序
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
