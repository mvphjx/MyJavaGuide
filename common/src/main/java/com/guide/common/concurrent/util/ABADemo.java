package com.guide.common.concurrent.util;


import org.junit.Test;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题以及解决方案
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/10 13:36
 */
public class ABADemo
{


    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    private static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);

    @Test
    public void showABA()
    {
        System.out.println("\nshow ABA");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }).start();
        Thread.yield();

        new Thread(() -> {
            // 值已经不是初始的100，但是无法判断
            boolean result = atomicReference.compareAndSet(100, 2019);
            System.out.println(result + " " + atomicReference.get());
        }).start();
    }

    /**
     * 多个线程同时修改 Integer，但是只允许一个相同修改
     */
    @Test
    public void handleABA()
    {
        System.out.println("\nhandle ABA");
        System.out.println(stampedReference.getReference() + "  version:"+stampedReference.getStamp());
        stampedReference.compareAndSet(100,101,stampedReference.getStamp(),stampedReference.getStamp()+1);
        System.out.println(stampedReference.getReference() + "  version:"+stampedReference.getStamp());
        stampedReference.compareAndSet(101,100,stampedReference.getStamp(),stampedReference.getStamp()+1);
        System.out.println(stampedReference.getReference() + "  version:"+stampedReference.getStamp());
    }
}
