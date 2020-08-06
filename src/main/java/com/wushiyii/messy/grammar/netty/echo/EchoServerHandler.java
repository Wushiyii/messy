package com.wushiyii.messy.grammar.netty.echo;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 可以支持Rest请求
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            JSONObject jsonObject = new JSONObject();
            FullHttpRequest request = (FullHttpRequest) msg;
            log.info("request:{}", request);
            log.info("content:{}", request.content().toString(CharsetUtil.UTF_8));
            log.info("method:{}", request.method());
            log.info("header:{}", request.headers());
            log.info("uri:{}", request.uri());

            jsonObject.put("uri", request.uri());
            jsonObject.put("header", request.headers());
            jsonObject.put("content", request.content().toString(CharsetUtil.UTF_8));
            jsonObject.put("hello", "world");

            // do dispatch
            if (request.uri().equals("/api")) {
                // ...
                log.info("request path :{}", request.uri());
            }

            byte[] jsonBytes = jsonObject.toString().getBytes();
            ByteBuf byteBuf = Unpooled.wrappedBuffer(jsonBytes);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(CONTENT_TYPE, "application/json");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            if (HttpUtil.isKeepAlive(request)) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }

        } else {
            ByteBuf in = (ByteBuf) msg;
            System.out.println("receive : " + in.toString(CharsetUtil.UTF_8));
            ctx.write(in);//将消息返回（此时消息还未flush，并没有发送）
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);//flush所有消息到远程节点
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//关闭通道
    }
}
