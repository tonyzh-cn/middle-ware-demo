package com.example.demo.tdmq.sdk;

/**
 * @author zhangtao
 * @since 2024/7/7 16:12
 */
import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * 同步发送消息
 */
public class SimpleProducer {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        // 初始化pulsar客户端
        PulsarClient pulsarClient = Constant.initPulsarClient();
        // 构建生产者
        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                // topic完整路径，格式为persistent://集群（租户）ID/命名空间/Topic名称
                .topic("persistent://pulsar-24ean87xj9rn/commtest/test-partition").create();
        System.out.println(">> pulsar producer created.");
        for (int i = 0; i < 10; i++) {
            String value = "my-sync-message-" + i;
            // 发送消息
            CompletableFuture<MessageId> msgId = producer.sendAsync(value);
//            System.out.println("deliver msg " + msgId + ",value:" + value);

//            Thread.sleep(500);
        }
        // 关闭生产者
        producer.close();
        // 关闭客户端
        pulsarClient.close();
    }
}

