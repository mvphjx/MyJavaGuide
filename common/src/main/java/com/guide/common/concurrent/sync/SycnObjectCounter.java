package com.guide.common.concurrent.sync;
/**
 * synchronized 实例对象
 * @author hjx
 * @version 1.0
 * @date 2021/7/4 17:15
 */
public class SycnObjectCounter
{
    private int count;

    public void incr(){
        synchronized(this){
            count ++;
        }
    }

    public int getCount() {
        synchronized(this){
            return count;
        }
    }
}
