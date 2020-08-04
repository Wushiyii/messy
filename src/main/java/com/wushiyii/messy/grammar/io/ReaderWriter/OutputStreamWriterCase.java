package com.wushiyii.messy.grammar.io.ReaderWriter;

import lombok.SneakyThrows;

import java.io.*;
import java.net.URL;

/**
 * @author wgq
 * @date 2020/8/4 11:13 上午
 */
public class OutputStreamWriterCase {

    @SneakyThrows
    private static InputStream getInputStream() {
        return new URL("http://www.baidu.com").openStream();
    }


    @SneakyThrows
    public static void out() {
        InputStreamReader is = new InputStreamReader(getInputStream());;
        OutputStreamWriter osw = new FileWriter("/Users/qudian/Desktop/a.txt");

        char[] buffer = new char[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            osw.write(new String(buffer, 0, len));
        }
        is.close();
        osw.close();
    }

    public static void main(String[] args) {
        out();
    }
}
