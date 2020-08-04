package com.wushiyii.messy.grammar.io.stream;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author wgq
 * @date 2020/8/4 10:43 上午
 */
public class OutputStreamCase {

    @SneakyThrows
    private static InputStream getInputStream() {
        return new URL("http://www.baidu.com").openStream();
    }


    @SneakyThrows
    public static void out() {
        InputStream is = getInputStream();
        OutputStream os = new FileOutputStream(new File("/Users/qudian/Desktop/a.txt"));

        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        is.close();

        os.flush();
        os.close();
    }

    public static void main(String[] args) {
        out();
    }


}
