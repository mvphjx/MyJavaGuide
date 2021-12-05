package com.guide.common.rpc.server;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpStatus;
import com.guide.common.rpc.codec.Decoder;
import com.guide.common.rpc.codec.Encoder;
import com.guide.common.rpc.codec.JsonDecoder;
import com.guide.common.rpc.codec.JsonEncoder;
import com.guide.common.rpc.proto.Request;
import com.guide.common.rpc.proto.Response;
import com.guide.common.rpc.transport.RequestHandler;
import com.guide.common.rpc.transport.TransportServer;
import com.guide.common.rpc.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:30
 */
@Slf4j
public class RpcServer
{
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config)
    {
        this.config = config;
        this.net = ReflectionUtil.newInstans(config.getTranportClass());
        this.net.init(this.config.getPort(), handler);
        this.encoder = new JsonEncoder();
        this.decoder = new JsonDecoder();
        this.serviceInvoker = new ServiceInvoker();
        this.serviceManager = new ServiceManager();
    }

    /**
     * 注册远程接口
     */
    public <T> void register(Class<T> clazz, T obj)
    {
        this.serviceManager.register(clazz, obj);
    }

    public void start()
    {
        this.net.start();
    }

    private void stop()
    {
        this.net.stop();
    }

    private RequestHandler handler = new RequestHandler()
    {
        Response response = new Response();

        @Override
        public void onRequest(InputStream inputStream, OutputStream outputStream)
        {
            try
            {
                byte[] bytes = IoUtil.readBytes(inputStream);
                Request request = decoder.decode(bytes, Request.class);
                log.info("get request: {}", request);
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                if (serviceInstance == null)
                {
                    log.warn("请求方法不存在");
                    throw new RuntimeException("请求方法不存在");
                }
                Object result = serviceInvoker.invoke(serviceInstance, request);
                response.setData(result);
            }
            catch (Exception e)
            {
                log.warn(e.getMessage());
                e.printStackTrace();
                response.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
                response.setMessage("Server error " + e.toString());
            }
            finally
            {
                byte[] encode = encoder.encode(response);
                try
                {
                    outputStream.write(encode);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
    };
}
