package com.guide.common.io.nio;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 选择器（Selector）
 * 选择器结合SelectableChannel实现了非阻塞的效果，大大提高了程序运行的效率。
 * 选择器实现了I/O通道的多路复用，使用最少的线程去操作更多的通道。使用它可以节省CPU资源，提高程序运行效率。
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 1:16
 */
public class SelectorDemo
{
    public static void main(String[] args) throws Exception
    {
        new Thread(SelectorDemo::server).start();
        Thread.sleep(1000);
        new Thread(SelectorDemo::client).start();
    }

    public static void server()
    {
        try
        {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            System.out.println(1);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
            System.out.println(2);
            serverSocketChannel.configureBlocking(false);
            System.out.println(3);
            Selector selector = Selector.open();
            System.out.println(4);
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(5+" Blocking Wait For Client");
            int keyCount = selector.select();
            System.out.println(6);
            System.out.println("selectionKey-"+selectionKey);
            System.out.println("keyCount-"+keyCount);
            serverSocketChannel.close();
            System.out.println(7);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void client()
    {
        try
        {
            System.out.println("Client Start");
            Socket socket = new Socket("127.0.0.1", 8888);
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
