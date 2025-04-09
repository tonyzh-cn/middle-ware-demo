package com.example.demo.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiKafkaConfig {
    
    // 第一个Kafka集群配置
    @Bean
    public ProducerFactory<String, String> producerFactory1() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.4.26:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "kafkaTemplate1")
    public KafkaTemplate<String, String> kafkaTemplate1() {
        return new KafkaTemplate<>(producerFactory1());
    }

    // 第二个Kafka集群配置
//    @Bean
    public ProducerFactory<String, String> producerFactory2() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.4.26:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

//    @Bean(name = "kafkaTemplate2")
    public KafkaTemplate<String, String> kafkaTemplate2() {
        return new KafkaTemplate<>(producerFactory2());
    }
}