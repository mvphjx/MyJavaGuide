package com.guide.common.rpc.proto;

import lombok.Data;

/**
 * 响应
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:00
 */
@Data
public class Response
{
    /**
     * 200成功
     * 500服务器错误
     */
    private int code=200;
    private String message;
    private Object data;
}
