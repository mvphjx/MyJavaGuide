package com.guide.common.io.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.ByteBuffer;

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
    public static void main(String[] args)
    {
        byte[] bytes = { 1, 2, 3 };
        //工厂方法
        Buffer buffer = ByteBuffer.wrap(bytes);
        log.info("capacity（容量）" + buffer.capacity());
        log.info("limit（限制）" + buffer.limit());
        log.info("position（位置）" + buffer.position());
        log.info("直接缓冲区" + buffer.isDirect());
        limit((ByteBuffer) buffer);
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
}
