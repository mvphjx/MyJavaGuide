package com.guide.common.io.chatroom.util;

import cn.hutool.http.HttpUtil;

/**
 * 帮助类
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 23:32
 */
public class ChatRoomUtil
{
    public static String HOST = "127.0.0.1";
    public static int PORT = 8888;
    public static String API = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=%s";

    /**
     * 获得自动回复
     * @param msg
     * @return
     */
    public static String getMsg(String msg)
    {
        //返回消息，机器人
        String result = HttpUtil.get(String.format(API, msg));
        return result;
    }
}
