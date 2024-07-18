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

public class LxySpringApplication {
    public static void run(Class<?> clazz){
        // 启动tomcat
        startTomcat();
    }
    
    private static void startTomcat() {
        System.out.println("hello tomcat");
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();

        Connector connector = new Connector();
        connector.setPort(8081);

        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");

        Host host = new StandardHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        Service service = server.findService("Tomcat");
        service.setContainer(engine);
        service.addConnector(connector);

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException("Tomcat start failed", e);
        }
    }
}
