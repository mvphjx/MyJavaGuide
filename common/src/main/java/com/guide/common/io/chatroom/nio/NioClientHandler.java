package com.guide.common.io.chatroom.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端线程类，专门接收服务器端响应信息
 */
@Slf4j
public class NioClientHandler implements Runnable
{
    private Selector selector;

    public NioClientHandler(Selector selector)
    {
        this.selector = selector;
    }

    @Override
    public void run()
    {

        try
        {
            for (; ; )
            {
                log.info("等待服务端消息");
                int readyChannels = selector.select();
                if (readyChannels == 0)
                {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext())
                {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable())
                    {
                        readHandler(selectionKey, selector);
                    }
                    iterator.remove();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 可读事件处理器
     */
    private void readHandler(SelectionKey selectionKey, Selector selector) throws IOException
    {
        log.info("接收到服务端，发送的消息");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        log.info("循环读取服务器端响应信息");
        String response = "";
        while (socketChannel.read(byteBuffer) > 0)
        {
            log.info("切换buffer为读模式");
            byteBuffer.flip();
            log.info("读取buffer中的内容");
            response += Charset.forName("UTF-8").decode(byteBuffer);
        }
        log.info("将服务器端响应信息打印到本地");
        if (response.length() > 0)
        {
            System.out.println(response);
        }
        log.info("将channel再次注册到selector上，监听新的可读事件/消息");
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
