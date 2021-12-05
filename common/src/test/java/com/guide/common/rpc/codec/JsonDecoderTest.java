package com.guide.common.rpc.codec;

import com.guide.common.model.Student;
import org.junit.Test;

/**
 * 序列化 单元测试
 */
public class JsonDecoderTest
{

    @Test
    public void decode()
    {
        JsonEncoder jsonEncoder = new JsonEncoder();
        byte[] encode = jsonEncoder.encode(new Student());
        JsonDecoder jsonDecoder = new JsonDecoder();
        Student decode = jsonDecoder.decode(encode, Student.class);
        System.out.println(decode);
    }
}
