package org.example.concurrency.synchronized_demo.demo_02;
/**
 * synchronized关键字
 * 对类的实例加锁01
 */
public class Demo02 {

	private int count = 10;
	
	public void test(){
		// 需要先拿到Demo02对象实例的锁
		synchronized (this) {
			count --;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
}