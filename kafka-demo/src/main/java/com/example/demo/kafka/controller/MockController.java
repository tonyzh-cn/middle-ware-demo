package com.example.demo.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("mock")
@Slf4j
public class MockController {
    @Resource(name = "kafkaTemplate1")
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("test")
    @ResponseBody
    public String test(){
        kafkaTemplate.send("rule_telesale_result", "test");
        return "success";
    }
}
