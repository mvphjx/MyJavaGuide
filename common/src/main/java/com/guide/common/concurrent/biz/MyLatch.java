package com.guide.common.concurrent.biz;
/**
 * 主从协作模式
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:40:18
 */
public class MyLatch {

    private int count;

    public MyLatch(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        while (count > 0) {
            wait();
        }
    }

    public synchronized void countDown() {
        count--;
        if (count <= 0) {
            notifyAll();
        }
    }
}
