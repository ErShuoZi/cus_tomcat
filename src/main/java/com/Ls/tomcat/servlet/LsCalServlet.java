package com.Ls.tomcat.servlet;

import com.Ls.tomcat.http.LsRequest;
import com.Ls.tomcat.http.LsResponse;
import com.Ls.tomcat.util.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

public class LsCalServlet extends CusHttpServlet {
    @Override
    public void doGet(LsRequest request, LsResponse response) {
        String strNum1 = request.getParameter("num1");
        String strNum2 = request.getParameter("num2");
        int num1 = WebUtils.parseInt(strNum1, 0);
        int num2 = WebUtils.parseInt(strNum2, 0);
        int sum = num1 + num2;
        String resp = response.respHeader + "<h1>" + "自定义Servlet ：" + num1 + "+" + num2 + "="  + sum +  "</h1>";
        OutputStream outputStream = response.getOutputStream();
        try {
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(LsRequest request, LsResponse response) {
        this.doGet(request,response);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
