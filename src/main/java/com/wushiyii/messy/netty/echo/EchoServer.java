package com.wushiyii.messy.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @Author: wgq
 * @Date: 2021/5/8 16:36
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class) //指定使用NIO传输channel
                    .localAddress(new InetSocketAddress(port)) //使用本地地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());//echoServerHandler被标记了@Sharable 对于所有的客户端，都会使用同一个EchoServerHandler
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind().sync(); //加上.sync() 的future都会阻塞获取，直到完成
            channelFuture.channel().closeFuture().sync(); //阻塞获取当前bootstrap的channel，直到获取到对应的closeFuture
        } finally {
            group.shutdownGracefully().sync(); //关闭eventLoopGroup
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(7070).start();
    }

}
