package com.guide.common.rpc.proto;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 服务描述
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 18:58
 */
@Data
public class ServiceDescriptor
{
    private String clazz;
    private String method;
    private String returnType;
    private String[] paramtypes;

    public static ServiceDescriptor from(Class clazz, Method method)
    {
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setClazz(clazz.getName());
        descriptor.setMethod(method.getName());
        descriptor.setReturnType(method.getReturnType().getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        Stream<String> stringStream = Arrays.stream(parameterTypes).map(Class::getName);
        String[] paramtypes = Arrays.stream(parameterTypes).map(Class::getName).toArray(String[]::new);
        descriptor.setParamtypes(paramtypes);
        return descriptor;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ServiceDescriptor that = (ServiceDescriptor) o;
        return Objects.equals(clazz, that.clazz) && Objects.equals(method, that.method) && Objects
                .equals(returnType, that.returnType) && Arrays.equals(paramtypes, that.paramtypes);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(clazz, method, returnType);
        result = 31 * result + Arrays.hashCode(paramtypes);
        return result;
    }
}
