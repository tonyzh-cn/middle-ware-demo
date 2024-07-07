package com.example.demo.tdmq.springboot;

/**
 * @author zhangtao
 * @since 2024/7/7 13:16
 */
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
public class MyProducer {

    /**
     * 1.发送消息的topic是在生产者配置中已经声明的topic
     * 2.PulsarTemplate类型应于发送消息的类型一致
     * 3.发送消息到指定topic时，消息类型需要与生产者工厂配置中的topic绑定的消息类型对应
     */

    @Autowired
    private PulsarTemplate<byte[]> defaultProducer;

    public void syncSendMessage() throws PulsarClientException {
        defaultProducer.send("topic1", "Hello pulsar client.".getBytes(StandardCharsets.UTF_8));
    }

    public void asyncSendMessage() {
        String msg = "Hello pulsar client.";
        CompletableFuture<MessageId> completableFuture = defaultProducer.sendAsync("topic1", msg.getBytes(StandardCharsets.UTF_8));
        // 通过异步回调得知消息发送成功与否
        completableFuture.whenComplete(((messageId, throwable) -> {
            if( null != throwable ) {
                System.out.println("delivery failed, value: " + msg );
                // 此处可以添加延时重试的逻辑
            } else {
                System.out.println("delivered msg " + messageId + ", value:" + msg);
            }
        }));
    }

    /**
     * 顺序消息需要使用顺序类型topic来完成，顺序类型的topic支撑全局顺序和局部顺序两种类型，根据实际情况选择合适的类型即可
     */
    public void sendOrderMessage() throws PulsarClientException {
        for (int i = 0; i < 5; i++) {
            defaultProducer.send("topic2", ("Hello pulsar client, this is a order message" + i + ".").getBytes(StandardCharsets.UTF_8));
        }
    }
}

