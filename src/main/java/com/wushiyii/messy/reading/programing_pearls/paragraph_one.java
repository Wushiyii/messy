package com.wushiyii.messy.reading.programing_pearls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class paragraph_one {

    public static void main(String[] args) throws IOException {
        byte[] bitmap = new byte[1000000];

        //初始化位图，每位默认为0
        for (int i = 0, len = bitmap.length; i < len; i++) {
            bitmap[i] = (byte) 0;
        }

        //读取文件，并将位图对应下标修改
        FileInputStream fileInputStream = new FileInputStream("/Users/xxx/Desktop/input.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        inputChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            bitmap[(int) buffer.get()] = (byte) 0x01;
        }

        //输出文件
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/xxx/Desktop/output.txt");
        final FileChannel outputChannel = fileOutputStream.getChannel();
        buffer.clear();
        for (int i = 0, len = bitmap.length; i < len; i++) {
            if (bitmap[i] == 0x01) buffer.put((byte) i);
        }
        buffer.flip();
        outputChannel.write(buffer);
    }
}
