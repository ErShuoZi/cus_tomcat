package com.Ls.WebUtils;

public class WebUtils {

    /**
     * 将字符串转为数字类型
     * @param strNum
     * @param defaultVal
     * @return
     */
    public static int parseInt(String strNum,int defaultVal){
        try {
            System.out.println(strNum);
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "格式不正确，转换失败");
        }
        return defaultVal;
    }
}
