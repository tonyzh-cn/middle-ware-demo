package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * @author zhangtao
 * @since 2024/8/24 23:27
 */
@RestController("")
@Slf4j
public class ProducerController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/consumer")
    @ResponseBody
    public String produce(){
        List<MapRecord<String, Object, Object>> messages = redisTemplate.opsForStream()
                .read(Consumer.from("consumerGroup", "consumerName"), StreamOffset.create("mystream", ReadOffset.lastConsumed()));
        log.info("messages:{}",messages);
        return "success";
    }
}
