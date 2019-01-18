package com.json;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextListener;

@org.springframework.boot.test.context.TestConfiguration
@ComponentScan(
        basePackages = {
                "com.json.services",
                "com.json.webapp.controllers"
        }
)
@EnableAutoConfiguration
public class TestConfiguration {

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
