package org.example.concurrency.interviewcase.demo_02;

import org.example.concurrency.interviewcase.demo_01.Demo01;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock condition来实现
 */
public class Demo02 {
    int maxSize = 10;
    LinkedList<Object> dataList = new LinkedList<>();
    Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    private volatile boolean haveValue = false;

    /**
     * 没有元素时要开始生产
     *
     * @param data
     */
    public void put(Object data) {
        lock.lock();
        try {
            // 超过最大容量时等待消费者先消费
            while (haveValue) {
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 添加元素
            dataList.add(data);
            haveValue = true;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "添加到容器，当前数量" + getCount());
            consumer.signalAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 存在元素则要一直消费
     */
    public Object get() {
        lock.lock();
        try {
            // 没有元素消费时，等待
            while (!haveValue) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Object o = dataList.removeFirst();
            haveValue = false;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "消费元素，当前数量" + getCount());
            producer.signalAll();
            return o;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getCount() {
        return dataList.size();
    }

    public static void main(String[] args) throws InterruptedException {
        Demo02 demo02 = new Demo02();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    demo02.get();
                }
            }, "consumer " + i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    demo02.put(Thread.currentThread().getName());
                }
            }, "producer " + i).start();
        }

    }
}
