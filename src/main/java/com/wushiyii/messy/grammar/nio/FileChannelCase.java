package com.wushiyii.messy.grammar.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelCase {

    public static void main(String[] args) throws Exception {
        //read op
//        FileInputStream fileInputStream = new FileInputStream("/Users/qudian/Desktop/test.txt");
//        FileChannel fc = fileInputStream.getChannel();
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        fc.read(buffer);
//        buffer.flip();
//        while (buffer.hasRemaining()) {
//            System.out.print((char) buffer.get());
//        }

        //write op
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/qudian/Desktop/test.txt");
        FileChannel fc = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Hello Hello".getBytes()).flip();
        fc.write(buffer);
        fc.close();

    }
}
