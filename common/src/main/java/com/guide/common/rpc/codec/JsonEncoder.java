package com.guide.common.rpc.codec;

import cn.hutool.json.JSONUtil;

/**
 * json序列化
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:16
 */
public class JsonEncoder implements Encoder
{
    @Override
    public byte[] encode(Object obj)
    {
        return JSONUtil.toJsonStr(obj).getBytes();
    }
}
