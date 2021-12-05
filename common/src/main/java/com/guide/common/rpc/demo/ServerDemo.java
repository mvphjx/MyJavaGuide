package com.guide.common.rpc.demo;

import com.guide.common.rpc.server.RpcServer;
import com.guide.common.rpc.server.RpcServerConfig;

/**
 * 服务器测试
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 1:31
 */
public class ServerDemo
{
    public static void main(String[] args)
    {
        RpcServerConfig config = new RpcServerConfig();
        RpcServer rpcServer = new RpcServer(config);
        rpcServer.register(ServiceDemo.class, new ServiceImpl());
        rpcServer.start();
    }
}
