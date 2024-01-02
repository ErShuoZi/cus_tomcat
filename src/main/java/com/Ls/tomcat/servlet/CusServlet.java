package com.Ls.tomcat.servlet;

import com.Ls.tomcat.http.LsRequest;
import com.Ls.tomcat.http.LsResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public interface CusServlet {
    void init() throws Exception;
    void service(LsRequest request, LsResponse response) throws IOException;
    void destroy();
}
