package com.guide.common.io.chatroom.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:23
 */
@Slf4j
public class ChatClient
{
    private final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private final int DEFAULT_SERVER_PORT = 8888;
    private final String QUIT = "quit";

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public void send(String msg) throws IOException
    {
        if (!socket.isOutputShutdown())
        {
            //发送消息 需要加\n  因为读取规则是readLine
            writer.write(msg + "\n");
            writer.flush();
        }
    }

    public String receive() throws IOException
    {
        String msg = null;
        if (!socket.isInputShutdown())
        {
            msg = reader.readLine();
        }
        return msg;
    }

    /**
     * 是否满足退出条件
     *
     * @param msg
     * @return
     */
    public boolean readyToQuit(String msg)
    {
        return msg.equals(QUIT);
    }

    public void close()
    {
        if (socket != null)
        {
            try
            {
                socket.close();
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
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //处理用户输入
            new Thread(new ClientHandler(this)).start();
            //处理服务器消息
            String msg = null;
            while ((msg = receive()) != null)
            {
                log.info(msg);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
