package com.Ls.tomcat.http;

import java.io.OutputStream;

//响应
//等价于原生Servlet中的HttpServletResponse
public class LsResponse {
    private OutputStream outputStream = null;

    public LsResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
