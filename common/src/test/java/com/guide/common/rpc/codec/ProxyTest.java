package com.guide.common.rpc.codec;

import cn.hutool.aop.ProxyUtil;
import com.guide.common.rpc.client.RemoteInvoker;
import com.guide.common.rpc.demo.ServiceDemo;
import com.guide.common.rpc.demo.ServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 1:51
 */
public class ProxyTest
{
    public static void main(String[] args)
    {
        Class<ServiceDemo> serviceDemoClass = ServiceDemo.class;
        Object proxyInstance = Proxy
                .newProxyInstance(serviceDemoClass.getClassLoader(), new Class[] { serviceDemoClass },
                        new InvocationHandler()
                        {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                            {
                                return new ServiceImpl();
                            }
                        });
        System.out.println(proxyInstance);

    }
}
