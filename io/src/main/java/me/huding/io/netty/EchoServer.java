package me.huding.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {


    private int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 每个新连接创建后都会调用initChannel方法添加处理器到channel的pipeline中
                    // TODO 断言每个添加的handler是线程安全的
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind().sync();

            System.out.println(EchoServer.class.getName() + " started and Listened on " + future.channel().localAddress());

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) {
        try {
            new EchoServer(8085).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
