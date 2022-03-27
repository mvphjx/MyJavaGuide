package com.guide.common.design.foraction.observer.eventbus;

import com.google.common.base.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *ObserverAction 类用来表示 @Subscribe 注解的方法，其中，target 表示观察者类，method 表示方法。
 * 它主要用在 ObserverRegistry 观察者注册表中。
 */
public class ObserverAction
{
    //观察者类
    private Object target;
    //方法
    private Method method;

    public ObserverAction(Object target, Method method)
    {
        this.target = Preconditions.checkNotNull(target);
        this.method = method;
        this.method.setAccessible(true);
    }

    public void execute(Object event)
    { // event是method方法的参数
        try
        {
            method.invoke(target, event);
        }
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
