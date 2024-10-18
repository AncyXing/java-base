package org.example.concurrency.synchronized_demo.demo_07;

import java.util.concurrent.TimeUnit;

/**
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 容易产生脏读问题

 */
public class Demo07 {

	String name;
	double balance;
	
	public synchronized void set(String name, double balance){
		this.name = name;
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = balance;
	}
	
	public /**synchronized*/ double getBalance(String name){
		return this.balance;
	}
	
	public static void main(String[] args) {
		Demo07 demo07 = new Demo07();
		
		new Thread(()-> demo07.set("zhangsan",100.0)).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(demo07.getBalance("zhangsan"));
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(demo07.getBalance("zhangsan"));
	}
}