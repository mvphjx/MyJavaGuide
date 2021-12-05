package com.guide.common.rpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 服务实现
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 20:50
 */

@AllArgsConstructor
@Data
public class ServiceInstance
{
    private Object object;
    private Method method;
}
