package com.guide.common.rpc.client;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpStatus;
import com.guide.common.rpc.codec.Decoder;
import com.guide.common.rpc.codec.Encoder;
import com.guide.common.rpc.proto.Request;
import com.guide.common.rpc.proto.Response;
import com.guide.common.rpc.proto.ServiceDescriptor;
import com.guide.common.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 远程方法代理类
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:27
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler
{
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector)
    {
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParams(args);
        Response response = invokeRemote(request);
        if (response.getCode() != HttpStatus.HTTP_OK)
        {
            throw new RuntimeException("远程方法返回错误信息:"+response.getMessage());
        }
        log.info("远程方法调用成功！{}",response);
        return response.getData();
    }

    private Response invokeRemote(Request request)
    {
        Response response;
        TransportClient client = null;
        try
        {
            log.info("开始调用远程方法{}",request);
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream recive = client.write(new ByteArrayInputStream(outBytes));
            byte[] bytes = IoUtil.readBytes(recive);
            response = decoder.decode(bytes, Response.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.warn(e.getMessage());
            response = new Response();
            response.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
            response.setMessage("远程方法调用失败 :" + e.getMessage());
        }
        finally
        {
            if (selector != null)
            {
                selector.release(client);
            }
        }

        return response;
    }
}
