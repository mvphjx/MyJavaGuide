package com.guide.common.io.chatroom.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 服务器消息处理
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:24
 */
@Slf4j
public class ServerHandler implements Runnable
{
    private ChatServer server;
    private Socket socket;

    public ServerHandler(ChatServer server, Socket socket)
    {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            server.addClient(socket);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;
            while ((msg = reader.readLine()) != null)
            {
                String fwdMsg = "客户端[" + socket.getPort() + "]:" + msg + "\n";
                System.out.println(fwdMsg);
                server.forwardMessage(socket, fwdMsg);
                if (server.readyToQuit(msg))
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                server.removeClient(socket);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
