package me.huding.io.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created By hujianhong
 * Date: 2018/11/24
 */
public class TimeServer {


    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // NIO服务辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 如何接收进来的连接
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TimeEncoder())
                                    .addLast(new TimeServerHandler());
                        }
                    })
                    // 设置指定Channel实现的参数
                    // option() 是提供给NioServerSocketChannel 用来接收进来的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // childOption() 是提供给由父管道 ServerChannel 接收到的连接，在这个例子中也是 NioServerSocketChannel
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(this.port).sync();


            // 等待socket关闭，这里不会发生，
            f.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int port = 7878;

        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new TimeServer(port).run();
    }
}
