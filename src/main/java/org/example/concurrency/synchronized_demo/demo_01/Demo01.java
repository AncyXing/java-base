package org.example.concurrency.synchronized_demo.demo_01;
/**
 * synchronized关键字
 * 对某个对象加锁
 */
public class Demo01 {

	private int count = 10;
	private Object object = new Object();
	
	public void test(){
		// 需要先拿到object对象的锁
		synchronized (object) {
			count --;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
}