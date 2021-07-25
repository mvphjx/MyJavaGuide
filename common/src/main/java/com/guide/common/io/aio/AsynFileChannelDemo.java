package com.guide.common.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 1:45
 */
public class AsynFileChannelDemo
{
    public static String PATH = "d:\\temp\\chanelTest.txt";

    public static void main(String[] args) throws Exception
    {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(PATH), StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.wrap("asdfghjk".getBytes());
        fileChannel.write(buffer, fileChannel.size(), "append 123", new CompletionHandler<Integer, String>()
        {
            @Override
            public void completed(Integer result, String attachment)
            {
                System.out.println("result-" + result);
                System.out.println("attachment-" + attachment);
            }

            @Override
            public void failed(Throwable exc, String attachment)
            {
                System.out.println("attachment-" + attachment);
                System.out.println(exc.getMessage());
            }
        });
        fileChannel.close();
        Thread.sleep(2000);
    }
}
