package com.wushiyii.messy.parser.jsonparser.tokenizer;

import lombok.Data;

import java.io.IOException;
import java.io.Reader;

/**
 * @author wgq
 * @date 2020/2/25 7:44 下午
 */
@Data
public class CharReader {

    private static final int BUFFER_SIZE = 1024;

    private Reader reader;

    private char[] buffer;

    private int pos;

    private int size;

    CharReader(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    public boolean hasMore() throws IOException {
        if (pos < size) {
            return true;
        }
        fillBuffer();
        return pos < size;
    }

    public void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }
        pos = 0;
        size = n;
    }

    /**
     * 返回 pos 下标处的字符
     * @return pos 下标处的字符
     */
    public char peek() {
        if (pos - 1 >= size) {
            return (char) -1;
        }
        return buffer[Math.max(0, pos - 1)];
    }


    public char next() throws IOException {
        if (!hasMore()) {
            return (char) -1;
        }
        return buffer[pos++];

    }

    public void back() {
        pos = Math.max(0, --pos);
    }


}
