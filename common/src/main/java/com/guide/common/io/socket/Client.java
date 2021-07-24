package com.guide.common.io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/24 2:41
 */
@Slf4j
public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = null;
        socket = new Socket("127.0.0.1", Server.port);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //等待用户输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            //发送消息 需要加\n  因为读取规则是readLine
            log.info("发送消息："+request);
            writer.write(request + "\n");
            writer.flush();
            //回复消息
            String msg = reader.readLine();
            if (msg != null)
            {
                System.out.println(msg);
            }
        }
        socket.close();
        writer.close();
    }
}
