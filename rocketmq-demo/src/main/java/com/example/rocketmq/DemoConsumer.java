package com.example.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 配音完成的 consumer
 * @author dftd
 *
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "TopicTest", consumerGroup = "demo-consumer-group", consumeMode = ConsumeMode.ORDERLY, consumeThreadMax = 15)
public class DemoConsumer implements RocketMQListener<String>{

	@Override
	public void onMessage(String message) {
		log.info(Thread.currentThread().getName()+"-接收消息：{}", message);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		throw new RuntimeException();
	}
}
