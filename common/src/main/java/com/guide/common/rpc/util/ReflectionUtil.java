package com.guide.common.rpc.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:03
 */
public class ReflectionUtil
{
    /**
     * 创建对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstans(Class<T> clazz)
    {
        try
        {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公共方法
     *
     * @param clazz
     * @return
     */
    public static Method[] getPublicMethods(Class clazz)
    {
        return ReflectUtil.getPublicMethods(clazz);
    }

    /**
     * 方法调用
     *
     * @param object
     * @param method
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invoke(Object object, Method method, Object... args)
            throws InvocationTargetException, IllegalAccessException
    {
        return method.invoke(ClassUtil.isStatic(method) ? null : object, args);
    }
}
