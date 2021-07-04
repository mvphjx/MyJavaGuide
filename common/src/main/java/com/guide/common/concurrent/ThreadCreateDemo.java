package com.guide.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程创建的几种方法
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class ThreadCreateDemo
{
    public static void main(String[] args)
    {
        create1();
        create2();
        create3();
        create4();
    }

    /**
     * 继承Thread，创建线程
     */
    public static void create1()
    {
        System.out.println("---------type1");
        Thread thread = new HelloThread1();
        thread.setName("Demo1");
        thread.setPriority(Thread.MIN_PRIORITY);
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        try
        {
            //Waits for this thread to die.
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
        System.out.println("---------type1 finish\n\n");
    }

    static class HelloThread1 extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("Hello World,extends Thread");
        }

    }

    /**
     * 实现Runnable接口，创建线程
     */
    public static void create2()
    {
        System.out.println("---------type2");
        Thread thread = new Thread(new HelloRunnable());
        thread.setName("Demo2");
        thread.setPriority(Thread.MIN_PRIORITY);
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        try
        {
            //Waits for this thread to die.
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
        System.out.println("---------type2 finish\n\n");
    }
    static class HelloRunnable implements Runnable
    {
        @Override
        public void run()
        {
            System.out.println("Hello World,implements Runnable");
        }

    }

    /**
     * 实现CallAble接口，创建线程
     */
    public static void create3()
    {
        System.out.println("---------type3");
        FutureTask futureTask = new FutureTask<>(new HelloCallable());
        try
        {
            futureTask.run();
            //阻塞 等待线程执行完毕
            futureTask.get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("---------type3 finish\n\n");
    }
    static class HelloCallable implements Callable
    {
        @Override
        public Object call()
        {
            System.out.println("Hello World,implements Callable");
            return null;
        }
    }

    /**
     * 通过线程池，创建线程
     */
    public static void create4()
    {
        System.out.println("---------type4");
        ThreadFactory threadFactory =  new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                Thread thread = new Thread(r);
                thread.setName("Demo4");
                thread.setPriority(Thread.MIN_PRIORITY);
                return thread;
            }
        };
        ExecutorService executorService = new ThreadPoolExecutor(2, 2, 0,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(),threadFactory);
        executorService.submit(new HelloCallable());
        executorService.submit(new HelloRunnable());
        executorService.shutdown();
        System.out.println("---------type4 finish\n\n");
    }

}
