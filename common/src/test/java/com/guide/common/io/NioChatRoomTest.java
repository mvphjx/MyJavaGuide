package com.guide.common.io;

import com.guide.common.io.chatroom.bio.ChatClient;
import com.guide.common.io.chatroom.nio.NioClient;
import com.guide.common.io.chatroom.nio.NioServer;
import org.junit.Test;

import java.io.IOException;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:46
 */
public class NioChatRoomTest
{
    @Test
    public void serverStart()
    {
        NioServer server = new NioServer();
        server.start();
    }

    @Test
    public void clientsStart() throws InterruptedException
    {
        /**
         * nio的优点，一个线程，利用多路复用器就可以处理多个客户端连接
         */
        int num = 20;
        for (int i = 0; i < num; i++)
        {
            String name = i + "";
            Thread thread = new Thread(() -> {
                try
                {
                    new NioClient().start(name);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
