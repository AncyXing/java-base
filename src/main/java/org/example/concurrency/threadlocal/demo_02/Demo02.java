package org.example.concurrency.threadlocal.demo_02;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal线程局部变量
 */
public class Demo02 {
    // 相当于一个key, 线程设值后可以通过tl获取设置的值。
    private static final ThreadLocal<Person> PERSON_THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName()+ " " +  PERSON_THREAD_LOCAL.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            PERSON_THREAD_LOCAL.set(new Person());
            System.out.println(Thread.currentThread().getName() + " " + PERSON_THREAD_LOCAL.get());
        }).start();
    }
}

class Person {
    String name = "zhangsan";
}