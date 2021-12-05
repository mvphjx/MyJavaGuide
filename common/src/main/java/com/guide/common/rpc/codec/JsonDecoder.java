package com.guide.common.rpc.codec;

import cn.hutool.json.JSONUtil;

/**
 * json反序列化
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:16
 */
public class JsonDecoder implements Decoder
{

    @Override
    public <T> T decode(byte[] data, Class<T> clazz)
    {
        return JSONUtil.toBean(new String(data), clazz);
    }
}
