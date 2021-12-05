package com.guide.common.rpc.demo;

import com.guide.common.rpc.client.RpcClient;
import com.guide.common.rpc.client.RpcClientConfig;

/**
 * 客户端测试
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 1:31
 */
public class ClientDemo
{
    public static void main(String[] args)
    {
        RpcClientConfig config = new RpcClientConfig();
        RpcClient rpcClient = new RpcClient(config);
        ServiceDemo serviceDemo = rpcClient.getProxy(ServiceDemo.class);
        int result = serviceDemo.add(1, 2);
        System.out.println(result);
    }
}
