package org.example.concurrency.synchronized_demo.demo_03;
/**
 * synchronized关键字
 * 对类的实例加锁02
 *
 */
public class Demo03 {

	private int count = 10;

	/**
	 * 等同于synchronized(this)，锁定的是Demo03对象的实例
	 */
	public synchronized void test(){
		count --;
		System.out.println(Thread.currentThread().getName() + " count =" + count);
	}
}