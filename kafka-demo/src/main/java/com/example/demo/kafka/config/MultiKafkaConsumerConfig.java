package com.example.demo.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 * @since 2025/4/9 23:43
 */
@Configuration
@EnableKafka
public class MultiKafkaConsumerConfig {

    // 第一个Kafka集群消费者配置
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory1() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.4.26:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "crm-new-processing");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean(name = "kafkaListenerContainerFactory1")
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory1() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory1());
//        return factory;
//    }

    // 第二个Kafka集群消费者配置
    @Bean
    public ConsumerFactory<String, String> consumerFactory2(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(props);
        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean(name = "kafkaListenerContainerFactory2")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory2(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2(customizers));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
