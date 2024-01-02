package com.Ls.tomcat;

import com.Ls.tomcat.handler.CusRequestHandler;
import com.Ls.tomcat.http.LsRequest;
import com.Ls.tomcat.servlet.CusHttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//通过xml + 反射 初始化容器
public class CusTomcatV4 {
    /**
     * 两个容器:
     * 1.servletMapping: ConcurrentHashMap/HashMap
     *      key      :  value
     * servletName   :  servletInstance(实例)
     *
     * 2. servletUrlMapping: ConcurrentHashMap/HashMap
     *      key      :   value
     *      url      :   servletName
     */
    public static final ConcurrentHashMap<String, CusHttpServlet>
            servletMapping = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String,String >
            servletUrlMapping = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        CusTomcatV4 cusTomcatV4 = new CusTomcatV4();
        cusTomcatV4.init();
        //启动容器
        cusTomcatV4.run();
    }

    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("-----cusTomcat--------- is running at port 8080");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                CusRequestHandler cusRequestHandler = new CusRequestHandler(socket);
                Thread thread = new Thread(cusRequestHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //直接对两个容器进行初始化
    //@Test
    public void init() {
        //1.读取xml => dom4j
        //2.得到web.xml文件
        SAXReader saxReader = new SAXReader();
        String path = CusTomcatV4.class.getResource("/").getPath();
        try {
            Document document = saxReader.read(new File(path + "web.xml"));
            Element rootElement = document.getRootElement();
            //得到根元素所有元素
            List<Element> elements = rootElement.elements();
            //遍历过滤
            for (Element element : elements) {
                if ("servlet".equalsIgnoreCase(element.getName())){
                    //servlet配置
                    //装入servletMapping容器
                    //使用反射，将该servlet实例放入容器
                    Element servletNameElement = element.element("servlet-name");
                    Element servletInstanceNameElement = element.element("servlet-class");
                    //servletNameString: servletName   servletInstanceNameString:类的全路径
                    String servletNameString = servletNameElement.getText();
                    String servletInstanceNameString = servletInstanceNameElement.getText();
                    servletMapping.put(servletNameString, (CusHttpServlet) Class.forName(servletInstanceNameString).newInstance());
                }
                else if ("servlet-mapping".equalsIgnoreCase(element.getName())){
                    //servlet-mapping配置
                    //装入servletUrlMapping容器
                    Element servletMappingName= element.element("servlet-name");
                    Element servletUrl = element.element("url-pattern");
                    String servletMappingNameString = servletMappingName.getText();
                    String servletUrlString = servletUrl.getText();
                    servletUrlMapping.put( servletUrlString,servletMappingNameString);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
