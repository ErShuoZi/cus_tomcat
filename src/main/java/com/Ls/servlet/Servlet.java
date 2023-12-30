package com.Ls.servlet;

import com.Ls.WebUtils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受提交的数据，进行计算
        String strNum1 = request.getParameter("num1");
        String strNum2= request.getParameter("num2");
        //把strNum1/strNum2 转为int
        int num1 = WebUtils.parseInt(strNum1, 0);
        int num2 = WebUtils.parseInt(strNum2, 0);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        int result = num1 + num2;
        writer.print("<h1>" + num1 + "+" + num2 + "=" + result +  "</h1>");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
