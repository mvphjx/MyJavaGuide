package com.guide.common.concurrent.biz;

import java.util.ArrayDeque;
import java.util.Queue;
/**
 * 生产者消费者
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日18:57:48
 */
public class ProducerConsumerDemo
{

	public static void main(String[] args) {
		MyBlockingQueue<String> queue = new MyBlockingQueue<>(10);
		new Producer(queue).start();
		new Consumer(queue).start();
		new Consumer(queue).start();
	}

	static class MyBlockingQueue<E> {
		private Queue<E> queue = null;
		private int limit;

		public MyBlockingQueue(int limit) {
			this.limit = limit;
			queue = new ArrayDeque<>(limit);
		}

		public synchronized void put(E e) throws InterruptedException {
			while (queue.size() == limit) {
				System.out.println("----------------------------queue full,block");
				wait();
			}
			queue.add(e);
			notifyAll();
		}

		public synchronized E take() throws InterruptedException {
			while (queue.isEmpty()) {
				System.out.println("++++++++++++++++++++++++++++queue Empty,block");
				wait();
			}
			E e = queue.poll();
			notifyAll();
			return e;
		}
	}

	static class Producer extends Thread {
		MyBlockingQueue<String> queue;

		public Producer(MyBlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			int num = 0;
			try {
				while (true) {
					String task = String.valueOf(num);
					queue.put(task);
					System.out.println(this.getName()+" produce task " + task);
					num++;
					Thread.sleep((int) (Math.random() * 100));
				}
			} catch (InterruptedException e) {
			}
		}
	}

	static class Consumer extends Thread {
		MyBlockingQueue<String> queue;

		public Consumer(MyBlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				while (true) {
					String task = queue.take();
					System.out.println(this.getName()+" handle task " + task);
					Thread.sleep((int) (Math.random() * 200));
				}
			} catch (InterruptedException e) {
			}
		}
	}



}
