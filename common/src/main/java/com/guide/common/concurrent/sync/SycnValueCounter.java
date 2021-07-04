package com.guide.common.concurrent.sync;
/**
 * synchronized 实例属性
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class SycnValueCounter
{

    private int count;
    private Object lock = new Object();

    public void incr(){
        synchronized(lock){
            count ++;
        }
    }

    public int getCount() {
        synchronized(lock){
            return count;
        }
    }
}
