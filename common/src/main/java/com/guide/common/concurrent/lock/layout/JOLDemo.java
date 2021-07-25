package com.guide.common.concurrent.lock.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * 锁在Java对象头中体现，Monitor 以及锁升级
 *
 * markword这部分其实就是加锁的核心(16*4=64bit)
 * 最后三位值代表含义：
 * 001 正常
 * 101 偏向锁
 * *00 轻量级锁
 * *10 重量级锁
 * *11 GC标记
 *
 * Monitor可以理解为一个同步工具或一种同步机制，通常被描述为一个对象。
 * 每一个Java对象就有一把看不见的锁，称为内部锁或者Monitor锁。
 * synchronized通过Monitor来实现线程同步
 *
 * 无锁 ->偏向锁/轻量级锁 ->  重量级锁
 *
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月7日11:54:51
 */
public class JOLDemo
{
    private static Object o;
    private static Object oo;

    public static void main(String[] args) throws Exception
    {
        System.out.println("\n\n轻量级锁");
        o = new Object();
        synchronized (o)
        {
            /**
             * 最后两位00轻量级锁
             * **** 1000
             */
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        System.out.println("\n\n偏向锁");
        try
        {
            //jvm设计 启动5秒后 开启偏向锁
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        oo = new Object();
        synchronized (oo)
        {
            /**
             * 最后三位101开启了偏向锁
             * **** 0101
             */
            System.out.println(ClassLayout.parseInstance(oo).toPrintable());
        }

        System.out.println("\n\n重量级锁");
        new Thread(() -> {
            synchronized (o)
            {
                try
                {
                    o.wait(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                /**
                 * 最后三位010开启了重量级锁
                 * **** 1010
                 */
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }).run();
    }
}
