package com.guide.common.utli;

import com.guide.common.util.Aes128Util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class MsgAESTest
{
    /**
     WebTicket加密、解密测试
     原文格式为 用户名|密码
     密文格式为 用户名|密码->Aes加密->Base64编码
     *
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        String userStr = "xjpter|1qaz!QAZ";
        test(userStr);
    }

    private static void test(String userStr)
    {
        System.out.println("\n");
        System.out.println("原文："+userStr);
        String encrypt = Aes128Util.encrypt(userStr);
        System.out.println("Aes加密：" + encrypt);
        byte[] encode = Base64.getEncoder().encode(encrypt.getBytes());
        String encodeStr = new String(encode);
        System.out.println("Aes加密+Base64编码：" + encodeStr);
        byte[] decode = Base64.getDecoder().decode(encodeStr);
        String decodeStr = new String(decode);
        System.out.println("Base64解码：" + decodeStr);
        String decrypt = Aes128Util.decrypt(encrypt);
        System.out.println("Base64解码+Aes解密：" + decrypt);
    }

}
