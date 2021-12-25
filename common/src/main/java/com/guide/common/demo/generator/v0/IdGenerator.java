package com.guide.common.demo.generator.v0;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 *  ID 生成器
 *  给每个请求分配一个唯一 ID,可以通过请求 ID 来搜索同一个请求的所有日志。
 *  第一部分是本机名的最后一个字段。
 *  第二部分是当前时间戳，精确到毫秒。
 *  第三部分是 8 位的随机字符串，包含大小写字母和数字。
 * @author hjx
 * @version 1.0
 * @date 2021/12/25 10:59
 */
@Slf4j
public class IdGenerator
{
    public static String generate()
    {
        String id = "";
        try
        {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            if (tokens.length > 0)
            {
                //hostName 变量不应该被重复使用，尤其当这两次使用时的含义还不同的时候
                hostName = tokens[tokens.length - 1];
            }
            char[] randomChars = new char[8];
            int count = 0;
            Random random = new Random();
            // 代码没有注释、魔法值较多，可读性低。
            while (count < 8)
            {
                int randomAscii = random.nextInt(122);
                if (randomAscii >= 48 && randomAscii <= 57)
                {
                    randomChars[count] = (char) ('0' + (randomAscii - 48));
                    count++;
                }
                else if (randomAscii >= 65 && randomAscii <= 90)
                {
                    randomChars[count] = (char) ('A' + (randomAscii - 65));
                    count++;
                }
                //存在性能问题，需要优化
                else if (randomAscii >= 97 && randomAscii <= 122)
                {
                    randomChars[count] = (char) ('a' + (randomAscii - 97));
                    count++;
                }
            }
            id = String.format("%s-%d-%s", hostName, System.currentTimeMillis(), new String(randomChars));
        }
        catch (UnknownHostException e)
        {
            //异常处理
            log.warn("Failed to get the host name.", e);
        }

        return id;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 3; i++)
        {
            log.info(IdGenerator.generate());
        }
    }
}
