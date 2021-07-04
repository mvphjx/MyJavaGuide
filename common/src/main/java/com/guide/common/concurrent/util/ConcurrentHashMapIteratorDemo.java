package com.guide.common.concurrent.util;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 高性能、弱一致性的线程安全Map
 * 采用分段锁 cas
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:12:43
 */
public class ConcurrentHashMapIteratorDemo
{

    public static void test() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "abstract");
        map.put("b", "basic");

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (Entry<String, String> entry : map.entrySet()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(entry.getKey() + "," + entry.getValue());
                }
            }
        };
        t1.start();
        // 确保线程t1启动
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        map.put("c", "call");
    }

    public static void main(String[] args) {
        test();
    }

}
