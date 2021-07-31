package com.guide.common.io.chatroom.nio;

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
 * 聊天室服务器
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月31日19:36:52
 */
@Slf4j
public class NioServer
{
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";

    private ServerSocket serverSocket;
    //客户端连接
    private Map<Integer, Writer> connectedClients = new HashMap<>();
    //多线程处理客户端连接
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public synchronized void addClient(Socket socket) throws IOException
    {
        if (socket != null)
        {
            int port = socket.getPort();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            connectedClients.put(port, writer);
            log.info(port + "上线！");
        }
    }

    public synchronized void removeClient(Socket socket) throws IOException
    {
        if (socket != null)
        {
            int port = socket.getPort();
            if (connectedClients.containsKey(port))
            {
                connectedClients.get(port).close();
            }
            connectedClients.remove(port);
            log.info(port + "下线！");
        }
    }

    public synchronized void forwardMessage(Socket socket, String fwdMsg) throws IOException
    {
        for (Integer id : connectedClients.keySet())
        {
            if (!id.equals(socket.getPort()))
            {
                Writer writer = connectedClients.get(id);
                writer.write(fwdMsg);
                writer.flush();
            }
        }
    }

    public boolean readyToQuit(String msg)
    {
        if (msg.equals(QUIT))
        {
            return true;
        }
        return false;
    }

    public synchronized void close()
    {
        if (serverSocket != null)
        {
            try
            {
                serverSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void start()
    {
        try
        {
            log.info("服务器启动，等待客户端接入");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", DEFAULT_PORT));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            log.info("多路复用器，开启客户端接入监听");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
                        serverHandler(key, selector);
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

    private void serverHandler(SelectionKey key, Selector selector) throws Exception
    {
        if (key.isAcceptable())
        {
            log.info("客户端接入");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            log.info("多路复用器，开启客户端消息监听");
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            ByteBuffer buffer = ByteBuffer.wrap("欢迎加入聊天室".getBytes());
            channel.write(buffer);
        }
        if (key.isReadable())
        {
            log.info("客户端发来消息");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String msg = "";
            try
            {
                while (channel.read(buffer) > 0)
                {
                    msg = msg + new String(buffer.array());
                }
            }
            catch (IOException e)
            {
                //Connection reset 客户端连接关闭
                key.cancel();
                channel.close();
                log.info("XXX客户端下线");
                broadcastMsg(selector, "XXX客户端下线", key);
                return;
            }
            channel.register(selector, SelectionKey.OP_READ);
            if ("".equals(msg))
            {
                //为什么为空
                return;
            }
            broadcastMsg(selector, msg, key);
        }
    }

    //广播给其他客户端
    private void broadcastMsg(Selector selector, String msg, SelectionKey key) throws IOException
    {

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
