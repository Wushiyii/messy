package com.wushiyii.messy.grammar.io;

import java.io.*;

/**
 *
 * 装饰者实现：将输入流中的所有小写字母变成大写字母。
 */
public class UpperCaseInputStream extends FilterInputStream {
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    protected UpperCaseInputStream(InputStream in) {
        super(in);
    }


    @Override
    public int read() throws IOException {
        int c = super.read();
        return c == -1 ? c : Character.toUpperCase(c);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int c = super.read(b, off, len);
        for (int i = 0; i < c; i++) {
            b[i + off] = (byte) Character.toUpperCase(b[i + off]);
        }
        return c;
    }

    public static void main(String[] args) throws FileNotFoundException {
        UpperCaseInputStream in = new UpperCaseInputStream(new FileInputStream("/Users/qudian/Desktop/a.txt"));

        int c;
        try {
            while ((c = in.read()) != -1) {
                System.out.println((char)c);
            }

        } catch (Exception e) {

        }
    }



}
