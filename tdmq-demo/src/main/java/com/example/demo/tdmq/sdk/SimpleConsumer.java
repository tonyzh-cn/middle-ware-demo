package com.example.demo.tdmq.sdk;

/**
 * @author zhangtao
 * @since 2024/7/7 16:12
 */
import org.apache.pulsar.client.api.*;

import java.util.concurrent.TimeUnit;

/**
 * 消费者
 */
public class SimpleConsumer {


    public static void main(String[] args) throws PulsarClientException {
        // 初始化pulsar客户端
        PulsarClient pulsarClient = Constant.initPulsarClient();
        // 构建消费者
        Consumer<String> consumer = pulsarClient.newConsumer(Schema.STRING)
                // topic完整路径，格式为persistent://集群（租户）ID/命名空间/Topic名称，从【Topic管理】处复制
                .topic("persistent://pulsar-24ean87xj9rn/commtest/test-partition")
                // 需要在控制台Topic详情页创建好一个订阅，此处填写订阅名
                .subscriptionName("sub1_topic1")
                // 声明消费模式为exclusive（独占）模式
                .subscriptionType(SubscriptionType.Shared)
//                .batchReceivePolicy(BatchReceivePolicy.builder().maxNumBytes(1024*1024).maxNumMessages(100).timeout(2000, TimeUnit.MILLISECONDS).build())
                // 配置从最早开始消费，否则可能会消费不到历史消息
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest).subscribe();
        System.out.println(">> pulsar consumer created.");
       while (true){
            // 接收当前offset对应的一条消息
            Message<String> msg = consumer.receive();
            MessageId msgId = msg.getMessageId();
            String value = new String(msg.getValue());
            System.out.println("receive msg " + msgId + ",value:" + value);
            // 接收到之后必须要ack，否则offset会一直停留在当前消息，导致消息积压
            consumer.acknowledge(msg);
        }
        // 关闭消费者
//        consumer.close();
        // 关闭客户端
//        pulsarClient.close();
    }
}

