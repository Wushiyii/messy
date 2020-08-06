package com.wushiyii.messy.grammar.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wgq
 * @date 2020/8/5 4:23 下午
 */
@Slf4j
public class NIOServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public NIOServer(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                //阻塞等待事件
                selector.select();
                //事件列表
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    iterator.remove();
                    dispatch(iterator.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            register(key);
        } else if (key.isReadable()) {
            read(key);
        } else if (key.isWritable()) {
            write(key);
        }
    }

    private void write(SelectionKey key) throws IOException {


    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = socketChannel.read(buffer);
        if (count < 0) {
            socketChannel.close();
            key.cancel();
            log.info("{}\t Read ended", socketChannel);
        }
        log.info("Received message {}", new String(buffer.array()));
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    private void register(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        //获取和客户端连接的通道
        SocketChannel socketChannel = server.accept();
        socketChannel.configureBlocking(false);
        //客户端channel注册到selector上
        socketChannel.register(selector, SelectionKey.OP_READ);

    }

}
