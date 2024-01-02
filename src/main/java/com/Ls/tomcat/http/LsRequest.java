package com.Ls.tomcat.http;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//封装Http请求数据 method/uri/param
//等价于原生Servlet中的HttpServletRequest
public class LsRequest {
    private String method;
    private String uri;
    //参数列表
    private HashMap<String, String> parametersMapping =
            new HashMap<>();

    //inputStream 是和对应的请求的socket关联
    public LsRequest(InputStream inputStream) {
        init(inputStream);
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public String getParameter(String name) {
        if (parametersMapping.containsKey(name)){
            return parametersMapping.get(name);
        }else {
            return "";
        }
    }


    private void init(InputStream inputStream) {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            System.out.println("----------------------------------------");
            //读取
            String firstLine = bufferedReader.readLine();
            System.out.println(firstLine);
            method = firstLine.split(" ")[0];
            //获取uri
            String regex = "^([^?#]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(firstLine.split(" ")[1]);
            if (matcher.find()){
                uri = matcher.group(1);
            }

            String tempUri = firstLine.split(" ")[1];
            //获取参数列表
            if (tempUri.indexOf("?") != -1) {
                String[] tempUriSplited = tempUri.split("\\?");
                String params = tempUriSplited[1];
                String[] KeyValuePair = params.split("&");
                for (String pair : KeyValuePair) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        //代表属性名有对应的值
                        parametersMapping.put(keyValue[0],keyValue[1]);
                    }
                }

            }

            String message = null;
            //循环的读取
            while ((message = bufferedReader.readLine()) != null) {
                //判断message长度是否为0
                if (message.length() == 0) {
                    //读取完成
                    break;
                }
                System.out.println("接收到客户端的数据=" + message);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
