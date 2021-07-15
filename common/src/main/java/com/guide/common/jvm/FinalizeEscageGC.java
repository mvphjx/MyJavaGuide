package com.guide.common.jvm;


/**
 * 此代码演示两点：
 * 1 对象可以利用 finalize（）方法逃脱GC
 * 2 一个对象的finalize（）最多知乎被系统条用一次
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/11 18:23
 */
public class FinalizeEscageGC
{
    public static FinalizeEscageGC SAVE_HOOK = null;

    public void isAlive()
    {
        System.out.println("alive");
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        System.out.println("finalize");
        FinalizeEscageGC.SAVE_HOOK = this;

    }

    public static void main(String[] args) throws InterruptedException
    {
        SAVE_HOOK = new FinalizeEscageGC();
        SAVE_HOOK = null;
        System.gc();
        //因为finalize方法优先级很低，所以暂停0.5秒等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null)
        {
            SAVE_HOOK.isAlive();
        }
        else
        {
            System.out.println("gc");
        }

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null)
        {
            SAVE_HOOK.isAlive();
        }
        else
        {
            System.out.println("gc");
        }
    }

}
