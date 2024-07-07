package com.example.demo.tdmq.springboot;

/**
 * @author zhangtao
 * @since 2024/7/7 13:16
 */
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import io.github.majusko.pulsar.constant.Serialization;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Service;

/**
 * 消费者配置
 */
@Service
public class MyConsumer {

    @PulsarConsumer(topic = "topic1",  // 订阅topic名称
            subscriptionName = "sub_topic1", // 订阅名称
            serialization = Serialization.JSON, // 序列化方式
            subscriptionType = SubscriptionType.Shared, // 订阅模式，默认为独占模式
            consumerName = "firstTopicConsumer", // 消费者名称
            maxRedeliverCount = 3, // 最大重试次数
            deadLetterTopic = "sub_topic1-DLQ" // 死信topic名称
    )
    public void firstTopicConsume(byte[] msg) {
        // TODO process your message
        System.out.println("Received a new message. content: [" + new String(msg) + "]");
        // 如果消费失败，请抛出异常，这样消息会进入重试队列，之后可以重新消费，直到达到最大重试次数之后，进入死信队列。前提是要创建重试和死信topic
    }


    /**
     * 顺序类型的消息可借助顺序类型的topic来完成，支持全局顺序和局部顺序两种类型
     */
    @PulsarConsumer(topic = "topic2", subscriptionName = "sub_topic2")
    public void orderTopicConsumer(byte[] msg) {
        // TODO process your message
        System.out.println("Received a order message. content: [" + new String(msg) + "]");
    }


    /**
     * 监听死信topic，处理死信消息
     */
    @PulsarConsumer(topic = "sub_topic1-DLQ", subscriptionName = "dead_sub")
    public void deadTopicConsumer(byte[] msg) {
        // TODO process your message
        System.out.println("Received a dead message. content: [" + new String(msg) + "]");
    }
}
