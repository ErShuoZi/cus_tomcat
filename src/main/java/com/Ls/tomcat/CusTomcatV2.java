package com.Ls.tomcat;

import com.Ls.tomcat.handler.CusRequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CusTomcatV2 {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocket,监听8080
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("======customcatV2 is running at port 8080");
        //如果连接没有关闭则一致等待连接
        while (!serverSocket.isClosed()){
            //等待客户端连接
            //如果有连接过来，就创建一个soket
            //这个soket就是服务端和客户端的数据通道
            Socket socket = serverSocket.accept();
            CusRequestHandler cusRequestHandler = new CusRequestHandler(socket);
            //创建线程对象
            Thread thread = new Thread(cusRequestHandler);
            //开启线程任务
            thread.start();

        }
    }
}
