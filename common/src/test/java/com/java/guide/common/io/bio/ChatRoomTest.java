package com.java.guide.common.io.bio;

import com.guide.common.io.chatroom.bio.ChatClient;
import com.guide.common.io.chatroom.bio.ChatServer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:46
 */
public class ChatRoomTest
{
    @Test
    public void serverStart()
    {
        ChatServer server = new ChatServer();
        server.start();
    }

    @Test
    public void client1Start()
    {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }

    /**
     * 创建客户端
     * 有个问题：如何让主线程等待子线程执行结束
     * 方法1 如果只有一个子线程可以用 join
     * 方法2 CountDownLatch，但是子线程需要调用countDown有耦合
     * 方法3 简单点 sleep很久很久。。。
     * 方法4 Future
     * 方法5 BlockingQueue
     * 方法6 CyclicBarrier
     * 方法7
     * @throws InterruptedException
     */
    @Test
    public void clientsStart() throws InterruptedException
    {
        //由于服务器有10个线程，只能接待10个客户端
        int num = 20;
        for (int i = 0; i < num; i++)
        {
            Thread thread = new Thread(() -> {
                ChatClient chatClient = new ChatClient();
                chatClient.start();
            });
            thread.start();
        }
        Thread.sleep(Integer.MAX_VALUE);

    }
}
