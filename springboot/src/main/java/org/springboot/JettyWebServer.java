package org.springboot;

public class JettyWebServer implements WebServer{
    @Override
    public void start() {
        System.out.println("JettyWebServer start");
    }

    @Override
    public void stop() {
        System.out.println("JettyWebServer stop");
    }
}
