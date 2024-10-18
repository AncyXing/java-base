package org.example.concurrency.synchronized_demo.demo_06;


/**
 * 同步方法和非同步方法可以同时调用
 *
 */
public class Demo06 {

	public synchronized void test1(){
		System.out.println(Thread.currentThread().getName() + " test1 start..........");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " test1 end........");
	}
	
	public void test2(){
		System.out.println(Thread.currentThread().getName() + " test2 start..........");
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "test2 execute......");
	}
	
	public static void main(String[] args) {
		Demo06 demo06 = new Demo06();
		
		new Thread(demo06:: test1,"t1").start();
		new Thread(demo06:: test2,"t2").start();
	}
}