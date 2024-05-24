package com.guide.common.io.chatroom.nio;

import com.guide.common.io.chatroom.util.ChatRoomUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Netty Demo
 * BIO和NIO编程在网络事件发生后都需要进行处理数据的粘包拆包、
 * 对完整的数据包进行解码、触发各种统计类事件、对响应消息进行编码、
 * 监听各种网络异常、需要对底层网络和通信协议有一定的了解。
 * 用Netty编程时只需要设置Handler就能快速的进行业务开发而不用关心数据的读取及网络事件的分发处理.
 * 让开发者从底层网络通信中解放出来。
 * 从这些对比中我们可以看出Netty开发网络程序要求低，使用者无需太多关心和业务无关的信息。
 */
public class NettyServer
{

    public static void main(String[] args) throws Exception
    {
        int port = ChatRoomUtil.PORT; // 设置服务器端口
        new NettyServer(port).run();
    }

    private int port;

    public NettyServer(int port)
    {
        this.port = port;
    }

    public void run() throws Exception
    {
        // 创建两个 EventLoopGroup，bossGroup 用于处理接入的连接，workerGroup 用于处理 I/O 操作
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap b = new ServerBootstrap();
            // 使用 NIO 服务器套接字通道
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>()
                    {
                        @Override
                        public void initChannel(Channel ch)
                        {
                            ChannelPipeline p = ch.pipeline();
                            // 添加一个字符串解码器和编码器，用于处理字符串消息
                            p.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            p.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            // 添加一个自定义的服务器端业务处理类
                            p.addLast(new SimpleChatServerHandler());
                        }
                    });

            // 绑定到服务器端口并开始接受接入
            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server started and listening on port " + port);

            // 等待服务器端口关闭
            f.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 一个简单的服务器端业务处理类
    public class SimpleChatServerHandler extends ChannelInboundHandlerAdapter
    {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception
        {
            // 当新的客户端连接时，将其添加到广播列表中
            channels.add(ctx.channel());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception
        {
            // 当客户端断开连接时，将其从广播列表中移除
            channels.remove(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
        {
            System.out.println("Server received: " + msg);
            // 向客户端反馈消息
            ctx.writeAndFlush("Server received your message: " + msg);
            // 广播消息给所有客户端
            for (Channel channel : channels)
            {
                if (channel != ctx.channel())
                { // 排除发送消息的客户端
                    channel.writeAndFlush("Broadcast: " + msg);
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        {
            // 出现异常时关闭连接
            cause.printStackTrace();
            ctx.close();
        }
    }
}
