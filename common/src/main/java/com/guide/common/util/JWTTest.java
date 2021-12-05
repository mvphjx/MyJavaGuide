package com.guide.common.util;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

/**
 * JWT就是一种网络身份认证和信息交换格式
 * <p>
 * Header 头部信息，主要声明了JWT的签名算法等信息
 * Payload 载荷信息，主要承载了各种声明并传递明文数据
 * Signature 签名，拥有该部分的JWT被称为JWS，也就是签了名的JWS，用于校验数据
 */
public class JWTTest
{
    // 密钥
    public static byte[] KEY = "Hi".getBytes();

    public static void main(String[] args)
    {
        String token = create();
        System.out.println(token);
        parse(token);
    }

    public static String create()
    {
        String token = JWT.create().setPayload("sub", "HelloWorld").setPayload("name", "test").setPayload("admin", true)
                .setKey(KEY).sign();
        return token;
    }

    /**
     * JWT 解析、验证
     *
     * @param token
     */
    public static void parse(String token)
    {
        JWT jwt = JWT.of(token);
        JWTPayload payload = jwt.getPayload();
        JSONObject claimsJson = payload.getClaimsJson();
        System.out.println(claimsJson.toString());
        //验证
        jwt.setKey(KEY);
        System.out.println("verify:" + jwt.verify());
    }
}
