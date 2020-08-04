package com.wushiyii.messy.grammar.io.stream;

import lombok.SneakyThrows;

import java.io.*;
import java.net.URL;


public class InputStreamCase {

    @SneakyThrows
    private static InputStream getInputStream() {
        return new URL("http://www.baidu.com").openStream();
    }

    // 单个bit 读取
    @SneakyThrows
    public static void bitRead() {
        // init a stream
        InputStream in = getInputStream();
        StringBuilder res = new StringBuilder();
        int temp;
        while ((temp = in.read()) != -1) {
            res.append((char)temp);
        }
        System.out.println(res.toString());
    }

    // 数组读取
    @SneakyThrows
    public static void arrayRead() {
        // init a stream
        InputStream in = getInputStream();
        StringBuilder res = new StringBuilder();

        byte[] b = new byte[1024];
        int len;
        while ((len = (in.read(b))) != -1) {
            res.append(new String(b, 0, len));
        }
        System.out.println(res.toString());
    }


    public static void main(String[] args) {
        arrayRead();
    }


}
