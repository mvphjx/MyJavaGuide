package com.guide.common.concurrent;

/**
 * 线程中断/线程停止
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 19:00
 */
public class ThreadInterruptDemo extends Thread
{
    @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                // 模拟任务代码
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // ... 清理操作
                // 重设中断标志位
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(isInterrupted());
    }

    public static void main(String[] args)
    {
        ThreadInterruptDemo thread = new ThreadInterruptDemo();
        thread.start();
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
        }
        thread.interrupt();
    }
}
