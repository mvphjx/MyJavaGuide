package com.guide.common.concurrent.sync;

/**
 * synchronized 实例方法
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class SycnMethodCounter
{

    private int count;

    public synchronized void incr(){
        count ++;
    }

    public synchronized int getCount() {
        return count;
    }

}
