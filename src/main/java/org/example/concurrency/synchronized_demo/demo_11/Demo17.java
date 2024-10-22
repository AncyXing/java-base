package org.example.concurrency.synchronized_demo.demo_11;

import java.util.concurrent.TimeUnit;

import org.omg.CORBA.TIMEOUT;

/**
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另外一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外一个对象
 *
 */
public class Demo17 {

	Object o = new Object();
	
	public void test(){
		synchronized (o) {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
	public static void main(String[] args) {
		Demo17 demo17 = new Demo17();
		//启动第一个线程
		new Thread(demo17 :: test, "t1").start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//启动第二个线程
		Thread t2 = new Thread(demo17 :: test, "t2");

		//对象发生改变，所以t2线程获得新对象的锁，但是已经获得o锁的线程t1依然保持锁的状态
		demo17.o = new Object();
		
		t2.start();
	}
}