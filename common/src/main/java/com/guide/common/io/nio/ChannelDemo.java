package com.guide.common.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道（Channel）
 * <p>
 * {@link java.nio.channels.Channel}主要子类如下：
 * <p>
 * AsynchronousChannel接口的主要作用是使通道支持异步I/O操作。
 * ReadableByteChannel接口的主要作用是使通道允许对字节进行读操作。
 * ScatteringByteChannel接口的主要作用是可以从通道中读取字节到多个缓冲区中。
 * WritableByteChannel接口的主要作用是使通道允许对字节进行写操作。
 * GatheringByteChannel接口的主要作用是可以将多个缓冲区中的数据写入到通道中。
 * ByteChannel接口的主要作用是将ReadableByteChannel（可读字节通道）与WritableByte Channel（可写字节通道）的规范进行了统一
 * SeekableByteChannel接口的主要作用是在字节通道中维护position（位置），以及允许position发生改变。
 * NetworkChannel接口的主要作用是使通道与Socket进行关联，使通道中的数据能在Socket技术上进行传输。
 * MulticastChannel接口的主要作用是使通道支持InternetProtocol（IP）多播。
 * InterruptibleChannel接口的主要作用是使通道能以异步的方式进行关闭与中断。
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 0:18
 */
public class ChannelDemo
{
    public static String PATH = "d:\\temp\\chanelTest.txt";
    public static void main(String[] args) throws Exception
    {
        write();
        read();
    }

    public static void write() throws Exception
    {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(PATH));
        try (FileChannel channel = fileOutputStream.getChannel())
        {
            ByteBuffer buffer = ByteBuffer.wrap("123456789".getBytes());
            System.out.println("position-"+channel.position());
            int write = channel.write(buffer);
            System.out.println("write-"+write);
            System.out.println("position-"+channel.position());
            channel.position(2);
            buffer.rewind();
            int write1 = channel.write(buffer);
            System.out.println("write1-"+write1);
            System.out.println("position-"+channel.position());
            System.out.println(channel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        fileOutputStream.close();
    }

    public static void read() throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File(PATH));
        try (FileChannel channel = fileInputStream.getChannel())
        {
            //缺省从0开始，读取5个字节
            ByteBuffer buffer = ByteBuffer.allocate(5);
            channel.read(buffer);
            String text = new String(buffer.array());
            System.out.println(text);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        fileInputStream.close();
    }
}
