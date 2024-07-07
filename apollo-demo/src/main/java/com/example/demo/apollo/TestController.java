package com.example.demo.apollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangtao
 * @since 2024/7/7 12:02
 */
@RestController("/")
public class TestController {
    @Value("${timeout}")
    String timeout;

    @GetMapping("/timeout")
    public String timeout(){
        return timeout;
    }
}
