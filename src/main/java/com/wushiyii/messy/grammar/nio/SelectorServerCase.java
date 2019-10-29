package com.wushiyii.messy.grammar.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SelectorServerCase {

    public static void main(String[] args) throws Exception {
        new SelectorServerCase().select();
    }

    public void select() throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8088), 1024);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //本次轮询后可用通道数量
            int availableNum = selector.select();
            if (availableNum == 0) {
                TimeUnit.SECONDS.sleep(1);
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                handle(selector, key);
                iterator.remove();

            }
        }
    }
    private void handle (Selector selector, SelectionKey selectionKey) throws Exception {
        if (selectionKey.isValid()) {
            //当selectionKey为accept时，注册key对应channel对应的socket为可读取状态
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
        }
        //如果selectionKey为可读取状态，则读取其中数据
        if (selectionKey.isReadable()) {
            //注意可读状态下，OP_READ的key对应的channel为
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = sc.read(buffer);
            if (read > 0) {
                buffer.flip();
                System.out.println("Server receive : " + new String(buffer.array(), StandardCharsets.UTF_8));
                //返回内容
                ByteBuffer res = ByteBuffer.allocate("Response Hello".length());
                res.put("Response Hello".getBytes());;
                res.flip();
                sc.write(res);
            } else if(read < 0) {
                selectionKey.cancel();
                sc.close();
            }
        }
    }
}
