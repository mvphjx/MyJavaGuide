package com.guide.common.concurrent.util;
/**
 * ThreadLocal
 * 线程本地变量基础用法
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:12:43
 */
public class ThreadLocalBasic
{
	static ThreadLocal<Integer> local = new ThreadLocal<>();

	public static void main(String[] args) throws InterruptedException {
		Thread child = new Thread() {
			@Override
			public void run() {
				System.out.println("child thread initial: " + local.get());
				local.set(200);
				System.out.println("child thread final: " + local.get());
			}
		};
		local.set(100);
		child.start();
		child.join();
		System.out.println("main thread final: " + local.get());
	}
}
