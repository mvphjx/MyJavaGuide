package com.guide.common.io.chatroom.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * NIO客户端：接受消息、发送消息
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/31 20:17
 */
@Slf4j
public class NioClient
{

    /**
     * 启动
     */
    public void start(String nickname) throws IOException
    {
        log.info("连接聊天室服务器");
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        log.info("新开线程，处理服务端相应的消息");
        new Thread(new NioClientHandler(selector)).start();
        log.info("阻塞命令行，用来发送消息");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine())
        {
            String request = scanner.nextLine();
            if (request != null && request.length() > 0)
            {
                socketChannel.write(Charset.forName("UTF-8").encode(nickname + " : " + request));
            }
        }

    }
}
