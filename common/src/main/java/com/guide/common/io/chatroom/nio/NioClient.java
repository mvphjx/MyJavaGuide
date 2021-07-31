package com.guide.common.io.chatroom.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/31 20:17
 */
public class NioClient
{

    /**
     * 启动
     */
    public void start(String nickname) throws IOException
    {
        /**
         * 连接服务器端
         */
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

        /**
         * 接收服务器端响应
         */
        // 新开线程，专门负责来接收服务器端的响应数据
        // selector ， socketChannel ， 注册
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new NioClientHandler(selector)).start();

        /**
         * 向服务器端发送数据
         */
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

    public static void main(String[] args) throws IOException
    {
        //        new NioClient().start();
    }

}
