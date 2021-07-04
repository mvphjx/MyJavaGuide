package com.guide.common.concurrent;
/**
 * 死锁示例
 *
 * 解决方案：
 * 1 应该尽量避免在持有一个锁的同时去申请另一个锁，如果确实需要多个锁，所有代码都应该按照相同的顺序去申请锁
 * 2 使用更灵活的tryLock
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class DeadLockDemo
{
	private static Object lockA = new Object();
	private static Object lockB = new Object();

	private static void startThreadA() {
		Thread aThread = new Thread() {

			@Override
			public void run() {
				synchronized (lockA) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					synchronized (lockB) {
					}
				}
			}
		};
		aThread.start();
	}

	private static void startThreadB() {
		Thread bThread = new Thread() {
			@Override
			public void run() {
				synchronized (lockB) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					synchronized (lockA) {
					}
				}
			}
		};
		bThread.start();
	}

	public static void main(String[] args) {
		startThreadA();
		startThreadB();
	}
}
