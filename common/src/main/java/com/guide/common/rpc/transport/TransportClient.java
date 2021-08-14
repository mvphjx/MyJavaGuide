package com.guide.common.rpc.transport;

import com.guide.common.rpc.proto.Peer;

import java.io.InputStream;

/**客户端网络协议封装
 * 1创建连接
 * 2发送数据，等待响应
 * 3关闭连接
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:35
 */
public interface TransportClient
{
    void connect(Peer peer);
    InputStream write(InputStream stream);
    void close();
}
