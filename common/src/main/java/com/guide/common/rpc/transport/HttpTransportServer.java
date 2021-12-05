package com.guide.common.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/14 22:29
 */
@Slf4j
public class HttpTransportServer implements TransportServer
{
    private RequestHandler handler;
    private Server server;

    @Override
    public void init(int port, RequestHandler handler)
    {
        this.handler = handler;
        this.server = new Server(port);
        //servlet
        ServletContextHandler ctx = new ServletContextHandler();
        server.setHandler(ctx);
        ServletHolder servletHolder = new ServletHolder(new RequestServlet());
        ctx.addServlet(servletHolder, "/*");
    }

    @Override
    public void start()
    {
        try
        {
            server.start();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop()
    {
        try
        {
            server.stop();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }

    class RequestServlet extends HttpServlet
    {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
        {
            ServletInputStream inputStream = req.getInputStream();
            ServletOutputStream outputStream = resp.getOutputStream();
            if (handler != null)
            {
                handler.onRequest(inputStream, outputStream);
            }
            outputStream.flush();
        }
    }
}
