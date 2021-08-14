package com.guide.common.rpc.client;

import com.guide.common.rpc.proto.Peer;
import com.guide.common.rpc.transport.TransportClient;
import com.guide.common.rpc.transport.TransportServer;

import java.util.List;

/**
 * 网络连接管理
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:28
 */
public interface TransportSelector
{
    /**
     * 初始化
     */
    void init(List<Peer> peerList, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个Transport 与 server做交互
     *
     * @return
     */
    TransportClient select();

    /**
     * 释放
     *
     * @param client
     */
    void release(TransportClient client);

    void close();

}
