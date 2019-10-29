package com.wushiyii.messy.grammar.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ServerSocketChannelCase {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress(8089));

        ByteBuffer wrap = ByteBuffer.wrap("client".getBytes());
        ByteBuffer in = ByteBuffer.allocate(1024);

        System.out.println("Waiting for connection...");
        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                TimeUnit.SECONDS.sleep(1);
            } else {
                System.out.println("Server get connection");
                sc.read(in);
                in.flip();
                System.out.println("Get msg:" + new String(in.array(), StandardCharsets.UTF_8));

                wrap.rewind();
                sc.write(wrap);
            }
        }



    }
}
