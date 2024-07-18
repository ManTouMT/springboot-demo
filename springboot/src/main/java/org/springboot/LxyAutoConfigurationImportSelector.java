package org.springboot;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class LxyAutoConfigurationImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        // 拿到自动配置类的名字，包括springBoot的和第三方的
        // 对spring来说就是拿到项目jar包中的自动配置类
        // jars---> META-INF/spring.factories 
        List<String> configurations = new ArrayList<>(SpringFactoriesLoader.loadFactoryNames(LxyEnableAutoConfiguration.class, getClass().getClassLoader()));
        return configurations.toArray(new String[0]);
    }
    
}
