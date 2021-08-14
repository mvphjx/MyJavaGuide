package com.guide.common.rpc.client;

import com.guide.common.rpc.codec.Decoder;
import com.guide.common.rpc.codec.Encoder;
import com.guide.common.rpc.util.ReflectionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:27
 */
public class RpcClient
{
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient(RpcClientConfig config)
    {
        this.config = config;
        this.encoder = ReflectionUtil.newInstans(config.getEncoderClass());
        this.decoder = ReflectionUtil.newInstans(config.getDecoderClass());
        this.selector = ReflectionUtil.newInstans(config.getSelectorClass());
        this.selector.init(this.config.getPeerList(), this.config.getConnectCount(), this.config.getTransportClass());
    }

    public <T> T getProxy(Class<T> clazz)
    {
        RemoteInvoker remoteInvoker = new RemoteInvoker(clazz, encoder, decoder, selector);
        Object proxyInstance = Proxy
                .newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, remoteInvoker);
        return (T) proxyInstance;
    }
}
