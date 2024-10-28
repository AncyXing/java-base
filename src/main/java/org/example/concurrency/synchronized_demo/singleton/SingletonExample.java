package org.example.concurrency.synchronized_demo.singleton;

/**
 * 双重检查锁定
 */
public class SingletonExample {
    // 防止指令重排后，singleton属性未初始化的问题
    private static volatile SingletonExample singleton = null;

    // 避免通过 new 初始化对象，构造方法应为 public 或 private
    private SingletonExample() {
    }


    public static SingletonExample getInstance() {
        if (singleton == null) {
            synchronized (SingletonExample.class) {
                if (singleton == null) {
                    // 存在指令重排的可能，非原子操作
                    // 1.分配内存 2.对象初始化 3.singleton指向分配的内存地址
                    // 1->3->2
                    singleton = new SingletonExample();
                }
            }
        }
        return singleton;
    }
}
