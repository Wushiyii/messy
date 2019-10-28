package com.wushiyii.messy.grammar.nio;

import java.nio.IntBuffer;

public class BufferCase {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(2 * (i + 1));
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
