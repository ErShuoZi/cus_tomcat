package com.Ls.tomcat.handler;


import com.Ls.tomcat.CusTomcatV4;
import com.Ls.tomcat.http.LsRequest;
import com.Ls.tomcat.http.LsResponse;
import com.Ls.tomcat.servlet.CusHttpServlet;

import java.io.*;
import java.net.Socket;

/**
 * 1.CusRequestHandler 对象是线程对象
 * 2.处理Http请求
 */
public class CusRequestHandler implements Runnable {
    private Socket socket = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;

    public CusRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //测试：不同线程
        System.out.println("当前线程 ===" + Thread.currentThread().getName());
        //对客户端进行交互
        try {
            //最终
            inputStream = socket.getInputStream();
            LsRequest lsRequest = new LsRequest(inputStream);
            outputStream = socket.getOutputStream();
            LsResponse lsResponse = new LsResponse(outputStream);
            //创建LsCalServlet对象
            //LsCalServlet lsCalServlet = new LsCalServlet();
            //lsCalServlet.doGet(lsRequest,lsResponse);
            //使用反射
            String uri = lsRequest.getUri();

            String servletName =  CusTomcatV4.servletUrlMapping.get(uri);
            if (servletName == null) {
                servletName = "";
            }
            //在通过servletName获取到对应的实例
            CusHttpServlet cusHttpServlet = CusTomcatV4.servletMapping.get(servletName);

            //调用service方法，动态绑定，调用运行类型的doPost/doGet！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
          if (cusHttpServlet != null){
              cusHttpServlet.service(lsRequest,lsResponse);
          }else {
              String resp = lsResponse.respHeader + "<h1>404 Not Found</h1>";
              OutputStream outputStream = lsResponse.getOutputStream();
              outputStream.write(resp.getBytes());
              outputStream.flush();
              outputStream.close();
          }

            //设置响应头
            //String respHeader = "HTTP/1.1 200\n" +
            //        "Content-Type: text/html;charset=UTF-8\r\n\r\n";
            //设置响应体
            //String respBody = respHeader + "customV2";

            //回送
            //outputStream = socket.getOutputStream();
            //outputStream.write(respBody.getBytes());

            //outputStream = socket.getOutputStream();
            //LsResponse lsResponse = new LsResponse(outputStream);
            //String resp = lsResponse.respHeader + "<h1>哈哈哈哈</h1>";
            //outputStream = lsResponse.getOutputStream();
            //outputStream.write(resp.getBytes());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("发生了异常，请联系管理员");
        }
    }
}
