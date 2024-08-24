package com.example.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtao
 * @since 2024/8/24 23:27
 */
@RestController("")
public class ProducerController {
    @Resource
    private RocketMQTemplate rocketMqTemplate;

    @GetMapping("/produce")
    @ResponseBody
    public String produce(){
        rocketMqTemplate.syncSend("TopicTest", MessageBuilder.withPayload(System.currentTimeMillis()).build());
        return "success";
    }
}
