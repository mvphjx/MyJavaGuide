package com.guide.common.io.nio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.NioUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Buffer
 * 在NIO技术的缓冲区中，存在4个核心技术点，
 * 分别是：
 * ❑capacity（容量）
 * ❑limit（限制） 代表第一个不应该读取或写入元素的index（索引）。
 * ❑position（位置）
 * ❑mark（标记）
 * Invariants: mark <= position <= limit <= capacity
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/24 19:42
 */
@Slf4j
public class BufferDemo
{
    public static void main(String[] args) throws Exception
    {
        byte[] bytes = { 1, 2, 3 };
        //工厂方法
        Buffer buffer = ByteBuffer.wrap(bytes);
        log.info("capacity（容量）" + buffer.capacity());
        log.info("limit（限制）" + buffer.limit());
        log.info("position（位置）" + buffer.position());
        log.info("直接缓冲区" + buffer.isDirect());
        limit((ByteBuffer) buffer);
        //读和写
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        write(byteBuffer);
        read1(byteBuffer);
        read2(byteBuffer);
    }

    /**
     * limit（限制） 代表第一个不应该读取或写入元素的index（索引）。
     * Checks the given index against the limit, throwing an
     * {@link IndexOutOfBoundsException}
     */
    public static void limit(ByteBuffer buffer)
    {
        buffer.limit(2);
        //以绝对位置和相对位置读写单个字节的get()和put()方法
        buffer.put(0, (byte) 4);
        buffer.put(1, (byte) 5);
        log.info(buffer.mark().toString());
        try
        {
            buffer.put(2, (byte) 6);
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 写模式
     *
     * @param buffer
     */
    public static void write(ByteBuffer buffer) throws Exception
    {
        FileInputStream fileInputStream = new FileInputStream(new File(ChannelDemo.PATH));
        try (FileChannel channel = fileInputStream.getChannel())
        {
            //读取内容写入buffer
            channel.read(buffer);
            //从buffer读取内容
            //写模式 转变为 读模式

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        fileInputStream.close();
    }

    /**
     * 读模式 方式1
     *
     * @param buffer
     */
    public static void read1(ByteBuffer buffer) throws Exception
    {
        //从buffer读取内容
        String text = new String(buffer.array());
        System.out.println(text);
    }

    /**
     * 读模式 方式2 flip
     *
     * @param buffer
     */
    public static void read2(ByteBuffer buffer) throws Exception
    {
        //从buffer读取内容
        buffer.flip();
        CharBuffer charBuffer = Charset.forName("UTF-8").decode(buffer);
        System.out.println(charBuffer);
    }

}
