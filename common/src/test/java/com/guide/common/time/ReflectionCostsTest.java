package com.guide.common.time;

import cn.hutool.core.bean.BeanUtil;
import com.guide.common.mapstruct.entity.User;

/**
 * 测试反射与赋值性能对比
 * 1 毫秒=1000000 纳秒
 * 结论：
 * 1）虽然性能相差100倍，但是耗时约0.1毫秒
 * 2）第一次反射耗时105毫秒，应该是存在初始化，所以耗时较长
 */
public class ReflectionCostsTest
{
    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            directAssignment(new User());
            reflectionAssignment(new User());
        }

    }

    public static void reflectionAssignment(User model)
    {
        long l = System.nanoTime();
        BeanUtil.setProperty(model, "username", "Name");
        BeanUtil.setProperty(model, "sex", 1);
        BeanUtil.setProperty(model, "config", "Config");
        System.out.println("reflectionAssignment:" + (System.nanoTime() - l));
    }

    public static void directAssignment(User model)
    {
        long l = System.nanoTime();
        model.setUsername("Name");
        model.setSex(1);
        model.setConfig("Config");
        System.out.println("directAssignment:" + (System.nanoTime() - l));

    }
}
