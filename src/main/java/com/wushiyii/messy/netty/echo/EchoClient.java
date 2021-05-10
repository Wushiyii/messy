package com.wushiyii.messy.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: wgq
 * @Date: 2021/5/8 17:08
 */
public class EchoClient {
    private final int port;

    public EchoClient(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) //NIO传输，客户端类型
                    .remoteAddress(new InetSocketAddress(port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();// 连接远程节点，阻塞到完成
            channelFuture.channel().closeFuture().sync(); //阻塞获取closeFuture 直至关闭channel
        } finally {
            eventLoopGroup.shutdownGracefully().sync(); //优雅停机
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient(7070).start();
    }

}
