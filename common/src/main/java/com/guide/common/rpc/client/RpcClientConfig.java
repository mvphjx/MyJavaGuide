package com.guide.common.rpc.client;

import com.guide.common.rpc.codec.Decoder;
import com.guide.common.rpc.codec.Encoder;
import com.guide.common.rpc.codec.JsonDecoder;
import com.guide.common.rpc.codec.JsonEncoder;
import com.guide.common.rpc.proto.Peer;
import com.guide.common.rpc.transport.HttpTransportClient;
import com.guide.common.rpc.transport.TransportClient;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:27
 */
@Data
public class RpcClientConfig
{
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> peerList;

    {
        Peer peer = new Peer();
        peer.setHost("127.0.0.1");
        peerList = Arrays.asList(peer);
    }
}
