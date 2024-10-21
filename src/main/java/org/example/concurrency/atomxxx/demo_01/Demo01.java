package org.example.concurrency.atomxxx.demo_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样的问题的更高效的方法，使用AtomXXX类
 * AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性
 *
 */
public class Demo01 {

//	volatile int count = 0;
	AtomicInteger count = new AtomicInteger(0);
	
	public /*synchronized*/ void test(){
		for (int i = 0; i < 10000; i++) {
//			count ++;
			count.incrementAndGet();
		}
	}
	
	public static void main(String[] args) {
		Demo01 demo01 = new Demo01();
		
		List<Thread> threads = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(demo01::test, "thread-" + i));
		}
		
		threads.forEach((o)->o.start());
		
		threads.forEach((o)->{
			try {
				//等线程执行完毕之后才执行主线程main, 等待一个接一个执行完
				o.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(demo01.count);
	}


}