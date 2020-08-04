package com.wushiyii.messy.grammar.io.stream;

import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author wgq
 * @date 2020/8/4 11:31 上午
 */
public class BufferedInputStreamCase {

    @SneakyThrows
    private static InputStream getInputStream() {
        return new URL("http://www.baidu.com").openStream();
    }

    @SneakyThrows
    private static void bufferedRead() {
        BufferedInputStream bis = new BufferedInputStream(getInputStream());
        byte[] buf = new byte[1024];
        int len;
        StringBuilder res = new StringBuilder();
        while ((len = bis.read(buf)) != -1) {
            res.append(new String(buf, 0, len));
        }
        System.out.println(res.toString());
    }

    public static void main(String[] args) {
        bufferedRead();
    }
}
