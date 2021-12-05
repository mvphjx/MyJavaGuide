package com.guide.common.rpc.client;

import com.guide.common.rpc.proto.Peer;
import com.guide.common.rpc.transport.TransportClient;
import com.guide.common.rpc.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 0:53
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector
{
    private int count;
    /**
     * 已经连接好的client
     */
    private List<TransportClient> clients = new ArrayList<>();

    /**
     * 初始化
     *
     * @param peerList
     * @param count
     * @param clazz
     */
    @Override
    public synchronized void init(List<Peer> peerList, int count, Class<? extends TransportClient> clazz)
    {
        count = Math.max(count, 1);
        for (Peer peer : peerList)
        {
            for (int i = 0; i < count; i++)
            {
                TransportClient client = ReflectionUtil.newInstans(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("client.connect {}", peer);
        }
    }

    /**
     * 选择一个Transport 与 server做交互
     *
     * @return
     */
    @Override
    public synchronized TransportClient select()
    {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    /**
     * 释放
     *
     * @param client
     */
    @Override
    public synchronized void release(TransportClient client)
    {
        clients.add(client);
    }

    @Override
    public synchronized void close()
    {
        for (TransportClient client : clients)
        {
            client.close();
        }
    }
}
