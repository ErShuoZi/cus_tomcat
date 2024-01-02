package com.Ls.tomcat.handler;


import com.Ls.tomcat.http.LsRequest;

import java.io.*;
import java.net.Socket;

/**
 * 1.CusRequestHandler 对象是线程对象
 * 2.处理Http请求
 */
public class CusRequestHandler implements Runnable{
    private Socket socket = null;
    public CusRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //测试：不同线程
        System.out.println("当前线程 ===" + Thread.currentThread().getName());
        //对客户端进行交互
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();

            LsRequest lsRequest = new LsRequest(inputStream);
            //设置响应头
            String respHeader = "HTTP/1.1 200\n" +
                    "Content-Type: text/html;charset=UTF-8\r\n\r\n";
            //设置响应体
            String respBody = respHeader + "customV2";

            //回送
            outputStream = socket.getOutputStream();
            outputStream.write(respBody.getBytes());

        } catch (Exception e) {
            System.out.println("发生了异常，请联系管理员");
        } finally {
           //确保socket关闭
            if (socket != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}