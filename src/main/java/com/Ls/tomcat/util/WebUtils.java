package com.Ls.tomcat.util;

public class WebUtils {
    public static int parseInt(String strNum,int defaultVal){
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "格式不正确，转换失败");
        }
        return defaultVal;
    }
}
