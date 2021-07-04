package com.guide.common.concurrent.sync;
/**
 * 并发累加，如何保证线程安全
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class CounterThread extends Thread
{
    SycnMethodCounter counter;

    public CounterThread(SycnMethodCounter counter)
    {
        this.counter = counter;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 1000; i++)
        {
            counter.incr();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        int num = 1000;
        SycnMethodCounter counter = new SycnMethodCounter();
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++)
        {
            threads[i] = new CounterThread(counter);
            threads[i].start();
        }
        for (int i = 0; i < num; i++)
        {
            threads[i].join();
        }
        System.out.println(counter.getCount());
    }
}
