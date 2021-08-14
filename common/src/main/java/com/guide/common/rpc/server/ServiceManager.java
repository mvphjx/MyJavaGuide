package com.guide.common.rpc.server;

import com.guide.common.rpc.proto.Request;
import com.guide.common.rpc.proto.ServiceDescriptor;
import com.guide.common.rpc.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务管理
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 20:50
 */
@Slf4j
public class ServiceManager
{
    private Map<ServiceDescriptor, ServiceInstance> services = new ConcurrentHashMap<>();

    /**
     * 注册远程接口
     */
    public <T> void register(Class<T> clazz, T obj)
    {
        Method[] publicMethods = ReflectionUtil.getPublicMethods(clazz);
        for (Method publicMethod : publicMethods)
        {
            ServiceDescriptor descriptor = ServiceDescriptor.from(clazz, publicMethod);
            ServiceInstance serviceInstance = new ServiceInstance(obj, publicMethod);
            services.put(descriptor, serviceInstance);
            log.info("register {} {}", clazz, publicMethod);
        }

    }

    /**
     * 获取接口
     */
    public ServiceInstance lookup(Request request)
    {
        ServiceDescriptor service = request.getService();
        return services.get(service);
    }
}
