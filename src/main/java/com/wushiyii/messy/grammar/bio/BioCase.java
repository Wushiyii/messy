package com.wushiyii.messy.grammar.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioCase {

    public static void main(String[] args) throws IOException {
        //1.创建并监听端口请求
        ServerSocket serverSocket = new ServerSocket(8088);
        //2.accept阻塞请求，直到有新连接，此时会返回一个新Socket对象进行后续处理
        Socket client = serverSocket.accept();
        //3.新建一个BufferedReader用于读取新建socket中字符输入流中的文本
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //PrintWriter 打印格式化展示的对象读到本文输出流
        PrintWriter out = new PrintWriter(client.getOutputStream());
        String req, resp;
        //4.readLine阻塞读取字符串，直到读取字符串为换行或输入终止
        while ((req = in.readLine()) != null) {
            // handle req
            if ("Finish".equals(req)) {                         //5
                System.out.println(req);
                break;
            }
        }
        //process content
        //***

        //return content
        resp = "hello world";
        //响应客户端
        out.println(resp);
    }
}
