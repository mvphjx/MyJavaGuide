package com.guide.common.rpc.codec;

/**
 * 反序列化
 */
public interface Decoder
{
    <T> T decode(byte[] data, Class<T> clazz);
}
