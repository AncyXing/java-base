package org.example.concurrency.synchronized_demo.demo_04;

/**
 * synchronized关键字
 * 对类的.class进行加锁,类的字节码文件是唯一的
 */
public class Demo04 {

    private static int count = 10;

    /**
     * 这里等同于synchronized(Demo04.class)
     */
    public synchronized static void test1() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void test2() {
        synchronized (Demo04.class) {
            count--;
        }
    }
}