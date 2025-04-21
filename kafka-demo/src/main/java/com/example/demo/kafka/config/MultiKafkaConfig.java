package com.example.demo.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
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
//    @Bean
//    public ProducerFactory<String, String> producerFactory1() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean(name = "kafkaTemplate1")
//    public KafkaTemplate<String, String> kafkaTemplate1() {
//        return new KafkaTemplate<>(producerFactory1());
//    }

    // 第二个Kafka集群配置
    @Bean
    public ProducerFactory<String, String> producerFactory2(ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(configProps);
        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean(name = "kafkaTemplate2")
    public KafkaTemplate<String, String> kafkaTemplate2(ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
        return new KafkaTemplate<>(producerFactory2(customizers));
    }
}