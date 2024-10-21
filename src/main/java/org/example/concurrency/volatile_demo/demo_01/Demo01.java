package org.example.concurrency.volatile_demo.demo_01;

import java.util.concurrent.TimeUnit;

/**
 * volatile 关键字，是一个变量在多个线程间可见
 */
public class Demo01 {

	volatile boolean running = true;
	
	public void test(){
		System.out.println("test start.......");
		while (running) {
			try {
				System.out.println("test running");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("test end........");
	}
	
	public static void main(String[] args) {
		Demo01 demo01 = new Demo01();
		
		new Thread(demo01:: test, "t1").start();
		
		try {
			// 主线程休眠2s
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 主线程修改volatile修饰的变量
		demo01.running = false;
	}
}