package com.Ls.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CusTomcatV1 {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocket,监听8080
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("======customcat is running at port 8080");
        //如果连接没有关闭则一致等待连接
        while (!serverSocket.isClosed()){
            //等待客户端连接
            //如果有连接过来，就创建一个soket
            //这个soket就是服务端和客户端的数据通道
            Socket socket = serverSocket.accept();
            //接受客户端发送的数据
            //inputStream是字节流，为了读取方便，转成 BufferedReader()  字符流
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String message = null;
            //循环的读取
            while ((message = bufferedReader.readLine()) != null) {
                //判断message长度是否为0
                if (message.length() == 0) {
                    //读完了
                    break;
                }
                System.out.println("接收到客户端的数据=" + message);
            }


            //回送 - http响应
            OutputStream outputStream = socket.getOutputStream();
            //构建Http响应头
            String respHeader = "HTTP/1.1 200\n" +
                    "Content-Type: text/html;charset=UTF-8\r\n\r\n";
            //构建响应体
            String resp = respHeader +  "hi,custom~";
            System.out.println(resp);
            //回送 - outputStream字节输出流，要按照字节数组的形式返回
            outputStream.write(resp.getBytes());

            //释放
            outputStream.flush();
            outputStream.close();
            socket.close();
            inputStream.close();
        }
    }
}
