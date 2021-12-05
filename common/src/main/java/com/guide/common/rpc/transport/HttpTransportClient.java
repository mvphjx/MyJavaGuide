package com.guide.common.rpc.transport;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.guide.common.rpc.proto.Peer;
import org.eclipse.jetty.http.HttpComplianceSection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * HTTP实现
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:29
 */
public class HttpTransportClient implements TransportClient
{
    private String url;

    @Override
    public void connect(Peer peer)
    {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream stream)
    {

        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            IoUtil.copy(stream, outputStream);
            int status = connection.getResponseCode();
            if (status == HttpStatus.HTTP_OK)
            {
                return connection.getInputStream();
            }
            else
            {
                return connection.getErrorStream();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close()
    {

    }
}
