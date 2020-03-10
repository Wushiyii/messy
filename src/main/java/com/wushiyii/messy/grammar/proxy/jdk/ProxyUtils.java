package com.wushiyii.messy.grammar.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wgq
 * @date 2020/3/10 3:16 下午
 */
public class ProxyUtils {

    public static void generateClassFile(String proxyName, Class clazz) {
        byte[] bytes = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String path = clazz.getResource(".").getPath();
        path += proxyName + ".class";
        try (OutputStream out = new FileOutputStream(path)) {
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestInterfaceImpl testInterface = new TestInterfaceImpl();
        ProxyUtils.generateClassFile("TestGenerate", testInterface.getClass());
    }

}
