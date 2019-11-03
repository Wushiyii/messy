package com.wushiyii.messy.grammar.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当建立连接时，返回当前时间
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //netty提供的ByteBuf不需要flip方法进行翻转，因为ByteBuf两端都有指针，两个指针分别为读写操作
        final ByteBuf time = ctx.alloc().buffer(4);//由于时间为32位，所以需要初始化一个4字节的buffer
        time.writeInt((int) (System.currentTimeMillis() / 1000 + 2208988800L));

        //write/writeAndFlush为异步操作，所以会返回一个Future对象，具体在何时进行请求都是不可确定的
        final ChannelFuture channelFuture = ctx.writeAndFlush(time);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                ctx.close();
            }
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
