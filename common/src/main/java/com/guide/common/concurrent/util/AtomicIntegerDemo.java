package com.guide.common.concurrent.util;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 原子工具类
 *      优点：非阻塞
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:12:43
 */
public class AtomicIntegerDemo
{
	private static AtomicInteger counter = new AtomicInteger(0);

	static class Visitor extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				//incrementAndGet=++i getAndIncrement=i++
				counter.incrementAndGet();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int num = 1000;
		Thread[] threads = new Thread[num];
		for (int i = 0; i < num; i++) {
			threads[i] = new Visitor();
			threads[i].start();
		}
		for (int i = 0; i < num; i++) {
			threads[i].join();
		}
		System.out.println(counter.get());
	}
}
