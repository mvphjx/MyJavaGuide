package com.guide.common.io.chatroom.nio;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO聊天室服务器
 * 优点：只需要一个线程就可以批量处理客户端请求/连接
 * @author hjx
 * @version 1.0
 * @date 2021年7月31日19:36:52
 */
@Slf4j
public class NioServer
{
    private int DEFAULT_PORT = 8888;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public void start()
    {
        try
        {
            log.info("构建ServerSocketChannel：端口、非阻塞");
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", DEFAULT_PORT));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            log.info("构建多路复用器，开启客户端接入监听");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("服务器启动，等待客户端接入");
            while (true)
            {
                int count = selector.select();
                if (count > 0)
                {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext())
                    {
                        SelectionKey key = iterator.next();
                        serverHandler(key);
                        iterator.remove();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close();
        }
    }

    /**
     * 关闭连接，释放资源
     */
    private void close()
    {
        if (serverSocketChannel != null)
        {
            try
            {
                serverSocketChannel.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void serverHandler(SelectionKey key) throws Exception
    {
        if (key.isAcceptable())
        {
            log.info("客户端接入");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            log.info("获取客户端SocketChannel");
            SocketChannel channel = serverSocketChannel.accept();
            log.info("开启非阻塞模式");
            channel.configureBlocking(false);
            log.info("利用多路复用器，开启客户端消息监听");
            channel.register(selector, SelectionKey.OP_READ);
            log.info("使用ByteBuffer，返回欢迎信息");
            ByteBuffer buffer = ByteBuffer.wrap("欢迎加入聊天室".getBytes());
            channel.write(buffer);
        }
        if (key.isReadable())
        {
            log.info("客户端发来消息");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder msg = new StringBuilder();
            try
            {
                while (channel.read(buffer) > 0)
                {
                    msg.append(new String(buffer.array()));
                }
            }
            catch (IOException e)
            {
                //Connection reset 客户端连接关闭
                key.cancel();
                channel.close();
                log.info("XXX客户端下线");
                broadcastMsg("XXX客户端下线", key);
                return;
            }
            channel.register(selector, SelectionKey.OP_READ);
            if (StrUtil.isEmpty(msg.toString()))
            {
                //为什么为空
                log.warn("客户端信息为空");
                return;
            }
            broadcastMsg(msg.toString(), key);
        }
    }

    //广播给其他客户端
    private void broadcastMsg(String msg, SelectionKey key) throws IOException
    {
        log.info("广播信息："+msg);
        for (SelectionKey selectionKey : selector.keys())
        {
            if (selectionKey == key)
            {
                continue;
            }
            if (selectionKey.channel() instanceof SocketChannel)
            {
                SocketChannel channelOther = (SocketChannel) selectionKey.channel();
                ByteBuffer bufferOther = ByteBuffer.wrap(msg.getBytes());
                channelOther.write(bufferOther);
            }

        }

    }

    public static void main(String[] args)
    {
        NioServer server = new NioServer();
        server.start();
    }
}
