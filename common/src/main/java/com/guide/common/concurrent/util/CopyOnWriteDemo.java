package com.guide.common.concurrent.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 写时复制
 * CopyOnWriteArrayList和CopyOnWriteArraySet适用于读远多于写、集合不太大的场合，
 * 它们采用了写时复制，这是计算机程序中一种重要的思维和技术
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:12:43
 */
public class CopyOnWriteDemo
{

    private static void startModifyThread(final List<String> list) {
        Thread modifyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    list.add("item " + i);
                    try {
                        Thread.sleep((int) (Math.random() * 10));
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        modifyThread.start();
    }

    private static void startIteratorThread(final List<String> list) {
        Thread iteratorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (String str : list) {
                    }
                }
            }
        });
        iteratorThread.start();
    }

    public static void sort(){
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("c");
        list.add("a");
        list.add("b");
        Collections.sort(list);
    }

    public static void main(String[] args) {
        final List<String> list = new CopyOnWriteArrayList<>();
        startIteratorThread(list);
        startModifyThread(list);
    }
}
