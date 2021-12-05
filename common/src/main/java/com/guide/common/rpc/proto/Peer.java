package com.guide.common.rpc.proto;

import lombok.Data;

/**
 * 网络传输的一个端点
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 18:57
 */
@Data
public class Peer
{
    public static int defaultPort = 8080;

    private String host;
    private int port =defaultPort ;
}
