package com.guide.common.io.chatroom.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:25
 */
@Slf4j
public class ClientHandler implements Runnable
{
    private ChatClient chatClient;

    public ClientHandler(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @Override
    public void run()
    {
        try
        {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                String input = consoleReader.readLine();
                chatClient.send(input);
                if (chatClient.readyToQuit(input))
                {
                    break;
                }
            }
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String request = scanner.nextLine();
                log.info("发送消息："+request);
                chatClient.send(request);
                if (chatClient.readyToQuit(request))
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
            chatClient.close();
        }
    }
}
