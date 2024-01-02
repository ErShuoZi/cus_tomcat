package com.Ls.tomcat.servlet;

import com.Ls.tomcat.http.LsRequest;
import com.Ls.tomcat.http.LsResponse;

import java.io.IOException;

public abstract class CusHttpServlet implements CusServlet{
    @Override
    public void service(LsRequest request, LsResponse response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request,response);
        }else if ("POST".equalsIgnoreCase(request.getMethod())) {
            this.doPost(request,response);
        }
    }

    //这里是模板设计模式
    //让子类实现
    public abstract void doGet(LsRequest request,LsResponse response);
    public abstract void doPost(LsRequest request,LsResponse response);


}
