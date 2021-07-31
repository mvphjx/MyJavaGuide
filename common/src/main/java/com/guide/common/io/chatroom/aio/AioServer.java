package com.guide.common.io.chatroom.aio;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Aio聊天室服务端
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/1 0:30
 */
@Slf4j
public class AioServer
{
    final String LOCALHOST = "localhost";
    private static int DEFAULT_PORT = 8888;
    private static int BUFFER = 1024;
    private static int THREADPOOL_SIZE = 8;
    private static final String QUIT = "quit";

    private AsynchronousChannelGroup channelGroup;
    private AsynchronousServerSocketChannel serverChannel;
    private List<ClientHandler> connectedClients = new ArrayList<>();
    private Charset charset = Charset.forName("UTF-8");
    private ExecutorService service = Executors.newFixedThreadPool(THREADPOOL_SIZE);

    private static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void start()
    {
        try
        {
            log.info("构建AsynchronousServerSocketChannel");
            channelGroup = AsynchronousChannelGroup.withThreadPool(service);
            serverChannel = AsynchronousServerSocketChannel.open(channelGroup);
            serverChannel.bind(new InetSocketAddress(LOCALHOST, DEFAULT_PORT));
            log.info("服务启动");
            log.info("等待客户端连接");
            serverChannel.accept(null, new AcceptHandler());
            ThreadUtil.safeSleep(Integer.MAX_VALUE);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(serverChannel);
        }
    }

    private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object>
    {

        @Override
        public void completed(AsynchronousSocketChannel channel, Object attachment)
        {
            if (serverChannel.isOpen())
            {
                serverChannel.accept(null, this);
            }
            if (channel.isOpen())
            {
                log.info("建立客户端连接");
                ClientHandler handler = new ClientHandler(channel);
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
                addClient(handler);
                log.info("将客户端添加到列表,客户端数量：" + connectedClients.size());
                //第一个buffer表示从clientChannel中读取的信息写入到buffer缓冲区中
                //第二个buffer:但handler回调函数被调用时,buffer会被当做一个attachment参数传入到该回调函数中
                channel.read(buffer, buffer, handler);
            }
        }

        @Override
        public void failed(Throwable exc, Object attachment)
        {
            System.out.println("连接失败：" + exc);
        }
    }

    private class ClientHandler implements CompletionHandler<Integer, Object>
    {
        private AsynchronousSocketChannel clientChannel;

        ClientHandler(AsynchronousSocketChannel clientChannel)
        {
            this.clientChannel = clientChannel;
        }

        @Override
        public void completed(Integer result, Object attachment)
        {
            ByteBuffer buffer = (ByteBuffer) attachment;
            log.info("客户端消息处理");
            if (buffer != null)
            {
                //写操作
                if (result <= 0)
                {
                    log.info("客户端异常，移除客户列表");
                    connectedClients.remove(this);
                }
                else
                {
                    log.info("接收到客户端消息");
                    buffer.flip();
                    String fwdMsg = receive(buffer);
                    forwardMessage(clientChannel, fwdMsg);
                    log.info("清除缓冲区");
                    buffer.clear();
                    if (readyToQuit(fwdMsg)){
                        log.info("将客户从在线客户列表中去除");
                        connectedClients.remove(this);
                    }else {
                        log.info("继续等待读取客户端信息");
                        clientChannel.read(buffer,buffer,this);
                    }
                }
            }
            else
            {
                log.info("消息发送成功");
            }
        }

        @Override
        public void failed(Throwable exc, Object attachment)
        {
            log.info("消息发送异常");
        }
    }

    private String receive(ByteBuffer buffer)
    {
        return String.valueOf(charset.decode(buffer));
    }

    private void forwardMessage(AsynchronousSocketChannel clientChannel, String fwdMsg)
    {
        log.info("广播消息：" + fwdMsg);
        for (ClientHandler handler : connectedClients)
        {
            if (handler.clientChannel.equals(clientChannel))
            {
                continue;
            }
            log.info("发送消息to " + handler.clientChannel);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(charset.encode(fwdMsg));
            buffer.flip();
            handler.clientChannel.write(buffer, null, handler);
        }
    }

    private void addClient(ClientHandler handler)
    {
        connectedClients.add(handler);
    }

    /**
     * 当输入"quit"时表示客户退出
     * @param msg
     * @return
     */
    public boolean readyToQuit(String msg){
        return QUIT.equals(msg);
    }

    public static void main(String[] args)
    {
        new AioServer().start();
    }
}
