package com.guide.common.io.socket;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/24 2:41
 */
@Slf4j
public class Server
{
    public static int port = 8081;
    public static String API = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=%s";

    public static void main(String[] args) throws Exception
    {
        //绑定监听端口
        ServerSocket serverSocket = new ServerSocket(port);
        log.info("启动服务");
        while (true)
        {
            //等待客户端连接
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true)
            {
                //读取客户端发送到消息
                String msg = reader.readLine();
                log.info(msg);
                if (msg != null)
                {
                    //返回消息，机器人
                    String result = HttpUtil.get(String.format(API, msg));
                    writer.write(result + "\n");
                    writer.flush();
                    log.info(result);
                }
            }

        }
    }
}
