package org.example.concurrency.volatile_demo.demo_02;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，
 * 也就是说volatile不能替代synchronize
 */
public class Demo02 {

    volatile int count = 0;

    public synchronized void  test() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        Demo02 demo02 = new Demo02();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(demo02::test, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                //等线程执行完毕之后才执行主线程main
                o.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println(demo02.count);
    }
}