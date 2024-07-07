package com.example.demo.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangtao
 * @since 2024/7/7 12:01
 */
@SpringBootApplication
@EnableApolloConfig
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
