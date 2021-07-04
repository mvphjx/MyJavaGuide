package com.guide.common.concurrent.biz;

/**
 * 集合点模式
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:40:18
 */
public class AssemblePoint {

    private int n;

    public AssemblePoint(int n) {
        this.n = n;
    }

    public synchronized void await() throws InterruptedException {
        if (n > 0) {
            n--;
            if (n == 0) {
                notifyAll();
            } else {
                while (n != 0) {
                    wait();
                }
            }
        }
    }

}
