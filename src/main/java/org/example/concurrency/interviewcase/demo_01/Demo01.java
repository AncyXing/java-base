package org.example.concurrency.interviewcase.demo_01;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容量的同步容器，拥有Put和get方法，以及getCount方法，
 * 能够支持两个生产者线程以及10个消费者线程的阻塞调用
 */
public class Demo01 {
    int maxSize = 10;
    LinkedList<Object> dataList = new LinkedList<>();
    Object lock = new Object();

    /**
     * 没有元素时要开始生产
     *
     * @param data
     */
    public void put(Object data) {
        synchronized (lock) {
            // 超过最大容量时等待消费者先消费
            while (getCount() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 添加元素
            dataList.add(data);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "添加到容器，当前数量" + getCount());
            lock.notifyAll();
        }
    }

    /**
     * 存在元素则要一直消费
     */
    public Object get() {
        synchronized (lock) {
            // 没有元素消费时，等待
            while (getCount() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Object o = dataList.removeFirst();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "消费元素，当前数量" + getCount());
            lock.notifyAll();
            return o;
        }
    }

    public synchronized int getCount() {
        return dataList.size();
    }

    public static void main(String[] args) throws InterruptedException {
        Demo01 demo01 = new Demo01();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    demo01.get();
                }
            }, "consumer " + i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    demo01.put(Thread.currentThread().getName());
                }
            }, "producer " + i).start();
        }
    }
}
