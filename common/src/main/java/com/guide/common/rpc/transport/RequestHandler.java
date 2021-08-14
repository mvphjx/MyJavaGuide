package com.guide.common.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/7 19:38
 */
public interface RequestHandler
{
    void onRequest(InputStream inputStream, OutputStream outputStream);
}
