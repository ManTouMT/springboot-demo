package org.springboot;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;

public class LxySpringApplication {
    public static void run(Class<?> clazz){
        // 创建spring容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(clazz);
        context.refresh();
        
        // 启动容器
        WebServer webServer = getWebServer(context);
        webServer.start();
        // 启动tomcat
//        startTomcat(context);
    }
    
    private static WebServer getWebServer(WebApplicationContext applicationContext) {
        Map<String, WebServer> beansOfType = applicationContext.getBeansOfType(WebServer.class);
        if(beansOfType.isEmpty()){
            throw new NullPointerException("beansOfType is null");
        }
        if (beansOfType.size() > 1) {
            throw new IllegalArgumentException("multiple beans of type " + WebServer.class.getName());
        }
        return beansOfType.values().stream().findFirst().get();
    }
    
    private static void startTomcat(WebApplicationContext webApplicationContext) {
        System.out.println("hello tomcat");
  
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        
        Host host = new StandardHost();
        host.setName("localhost");
        host.addChild(context);

        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");
        engine.addChild(host);
        
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        service.setContainer(engine);

        Connector connector = new Connector();
        connector.setPort(8081);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*", "dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException("Tomcat start failed", e);
        }
    }
}
