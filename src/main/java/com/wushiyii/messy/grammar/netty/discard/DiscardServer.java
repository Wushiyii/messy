package com.wushiyii.messy.grammar.netty.discard;

import com.wushiyii.messy.grammar.netty.time.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 丢弃服务器（任何请求都被抛弃）
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void bootstrap() throws Exception {
        //NioEventLoopGroup是一个可以出炉
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)//指定使用NioServerSocketChannel作为channel以接受请求
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new DiscardServerHandle());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)//配置TCP选项
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定端口并开始接收数据
            ChannelFuture f = bootstrap.bind(port).sync();

            f.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new DiscardServer(8088).bootstrap();
    }
}