package com.wushiyii.messy.grammar.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioCase {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket client = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream());
        String content;
        while ((content = in.readLine()) != null) {
            // handle req
            System.out.println(content);
            break;
        }
        //process content
        //***

        //return content
        out.println("response");
    }
}
