package com.guide.common.io.chatroom.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 聊天室服务器
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:22
 */
@Slf4j
public class ChatServer
{
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";

    private ServerSocket serverSocket;
    //客户端连接
    private Map<Integer, Writer> connectedClients = new HashMap<>();
    //多线程处理客户端连接
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    ;

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
            serverSocket = new ServerSocket(DEFAULT_PORT);
            log.info("服务器启动，等待客户端接入");
            while (true)
            {
                Socket socket = serverSocket.accept();
                executorService.execute(new ServerHandler(this, socket));
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

    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        server.start();
    }
}
