package com.guide.common.rpc.proto;

import lombok.Data;

/**
 * 请求
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:00
 */
@Data
public class Request
{
    private ServiceDescriptor service;
    private Object[] params;
}
