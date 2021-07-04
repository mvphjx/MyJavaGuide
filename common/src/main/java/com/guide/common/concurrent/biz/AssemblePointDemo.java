package com.guide.common.concurrent.biz;
/**
 * 集合点模式
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:40:18
 */
public class AssemblePointDemo {

	static class Tourist extends Thread {
		AssemblePoint ap;

		public Tourist(AssemblePoint ap) {
			this.ap = ap;
		}

		@Override
		public void run() {
			try {
				// 模拟先各自独立运行
				Thread.sleep((int) (Math.random() * 1000));

				// 集合
				ap.await();
				System.out.println("arrived");
				// ... 集合后执行其他操作
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] args) {
		int num = 10;
		Tourist[] threads = new Tourist[num];
		AssemblePoint ap = new AssemblePoint(num);
		for (int i = 0; i < num; i++) {
			threads[i] = new Tourist(ap);
			threads[i].start();
		}
	}

}
