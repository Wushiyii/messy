package com.wushiyii.messy.grammar.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandle extends ChannelInboundHandlerAdapter {

    //读内容
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        //读取内容
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) {
//                System.out.println((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
        //返回内容 (此处不用手动release,因为netty在写操作时会自动release)
        ctx.write(msg);//echo msg
        ctx.flush();//write操作只是把内容写到缓冲区，正在传输到网络是通过flush操作

    }

    //错误处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //发生异常时结束连接
        cause.printStackTrace();
        ctx.close();
    }
}
