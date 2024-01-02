package com.Ls.tomcat.http;

import java.io.OutputStream;

//响应
//等价于原生Servlet中的HttpServletResponse
public class LsResponse {
    private OutputStream outputStream = null;
    private String DEFAULT_CONTENT_TYPE = "Content-Type: text/html;charset=UTF-8";
    //http响应头
    public final String respHeader = "HTTP/1.1 200\n" +
            DEFAULT_CONTENT_TYPE + "\r\n\r\n";

    public LsResponse(OutputStream outputStream) {
        this.outputStream = outputStream;

    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getDEFAULT_CONTENT_TYPE() {
        return DEFAULT_CONTENT_TYPE;
    }

    public void setDEFAULT_CONTENT_TYPE(String DEFAULT_CONTENT_TYPE) {
        this.DEFAULT_CONTENT_TYPE = DEFAULT_CONTENT_TYPE;
    }
}
