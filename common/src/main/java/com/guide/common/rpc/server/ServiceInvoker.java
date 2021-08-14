package com.guide.common.rpc.server;

import com.guide.common.rpc.proto.Request;
import com.guide.common.rpc.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 0:02
 */
public class ServiceInvoker
{
    public Object invoke(ServiceInstance serviceInstance, Request request)
    {
        try
        {
            return ReflectionUtil.invoke(serviceInstance.getObject(), serviceInstance.getMethod(), request.getParams());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
