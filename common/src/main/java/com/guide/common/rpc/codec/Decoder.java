package com.guide.common.rpc.codec;

/**
 * εεΊεε
 */
public interface Decoder
{
    <T> T decode(byte[] data, Class<T> clazz);
}
