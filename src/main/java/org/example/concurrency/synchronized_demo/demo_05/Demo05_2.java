package org.example.concurrency.synchronized_demo.demo_05;
/**
 * count是类的成员变量，在此例子中为多线程的共享变量
 * 多个线程对count进行操作，可以出现线程安全问题
 */
public class Demo05_2 implements Runnable{

	private int count = 10;

	@Override
	public synchronized void run() {
		count --;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < 5; i++) {
			Demo05_2 demo052 = new Demo05_2();
			new Thread(demo052,"THREAD" + i).start();
		}
	}
}