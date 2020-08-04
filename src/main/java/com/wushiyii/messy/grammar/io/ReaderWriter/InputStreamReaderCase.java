package com.wushiyii.messy.grammar.io.ReaderWriter;

import lombok.SneakyThrows;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author wgq
 * @date 2020/8/4 10:26 上午
 */
public class InputStreamReaderCase {

    @SneakyThrows
    private static InputStream getInputStream() {
        return new URL("http://www.baidu.com").openStream();
    }

    @SneakyThrows
    public static void bitRead() {
        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream());
        StringBuilder res = new StringBuilder();
        int temp;
        while ((temp = inputStreamReader.read()) != -1) {
            res.append((char)temp);
        }
        System.out.println(res.toString());
    }

    @SneakyThrows
    public static void arrayRead() {
        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream());
        StringBuilder res = new StringBuilder();
        char[] buffer = new char[1024];
        int len;
        while ((len = inputStreamReader.read(buffer)) != -1) {
            res.append(new String(buffer, 0, len));
        }
        System.out.println(res.toString());
    }


    public static void main(String[] args) {
        arrayRead();
    }

}
