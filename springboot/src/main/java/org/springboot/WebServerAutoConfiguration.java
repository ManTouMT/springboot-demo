package org.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerAutoConfiguration {
    // 多个在使用时会报错，需要加以限制 自定义生效条件
    @Bean
    @Conditional(TomcatCondition.class)
    public TomcatWebServer tomcatWebServer() {
        return new TomcatWebServer();
    }
    
    @Bean
    @Conditional(JettyCondition.class)
    public JettyWebServer jettyWebServer() {
        return new JettyWebServer();
    }
}
