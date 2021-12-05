package com.guide.common.rpc.transport;

/**
 * 服务网络协议封装
 * 1启动、监听
 * 2接收请求
 * 3关闭
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:35
 */
public interface TransportServer
{
    void init(int port,RequestHandler handler);
    void start();
    void stop();
}
