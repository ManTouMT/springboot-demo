package org.springboot;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;
import java.util.Objects;

/**
 * 自定义生效条件
 * 通过判断是否导入了固定包来实现
 **/
public class LxyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(LxyConditionOnClass.class.getName());
        
        try {
            String className = (String)annotationAttributes.get("value");
            Objects.requireNonNull(context.getClassLoader()).loadClass(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
