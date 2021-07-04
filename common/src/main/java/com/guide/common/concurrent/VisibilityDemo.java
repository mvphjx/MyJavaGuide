package com.guide.common.concurrent;
/**
 * 可见性DEMO
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class VisibilityDemo
{
    private static volatile boolean shutdown = false;

    static class HelloThread extends Thread {
        @Override
        public void run() {
            while(!shutdown){
                // do nothing
            }
            System.out.println("exit hello");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloThread().start();
        Thread.sleep(1000);
        shutdown = true;
        System.out.println("exit main");
    }
}
