package com.wushiyii.messy.grammar.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private final static int PORT = 8088;

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)//指定使用NIO客户端channel
                    .remoteAddress(new InetSocketAddress(PORT))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());//当建立通道时，创建客户端请求处理器实例到通道pipeline中
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            //连接到远程，阻塞等待连接完成
            ChannelFuture f = b.connect().sync();

            //一直阻塞，直到channel关闭
            f.channel().closeFuture().sync();

        } finally {
            //阻塞等待关闭线程池等资源
          group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient().run();
    }

}
