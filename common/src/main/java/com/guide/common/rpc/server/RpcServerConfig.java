package com.guide.common.rpc.server;

import com.guide.common.rpc.codec.Decoder;
import com.guide.common.rpc.codec.Encoder;
import com.guide.common.rpc.codec.JsonDecoder;
import com.guide.common.rpc.codec.JsonEncoder;
import com.guide.common.rpc.proto.Peer;
import com.guide.common.rpc.transport.HttpTransportServer;
import com.guide.common.rpc.transport.TransportServer;
import lombok.Data;

/**
 * 服务器配置
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:30
 */
@Data
public class RpcServerConfig
{
    private Class<? extends TransportServer> tranportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decodeClass = JsonDecoder.class;
    private int port = Peer.defaultPort;
}
