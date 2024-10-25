package org.example.concurrency.threadlocal.demo_01;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal线程局部变量
 * 没有使用ThreadLocal，相当于线程都是使用p这个共享变量
 */
public class Demo01 {

    private static Person p = new Person();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ " " +  p.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            p.name = "lisi";
            System.out.println(Thread.currentThread().getName() + " " + p.name);
        }).start();
    }
}

class Person {
    String name = "zhangsan";
}