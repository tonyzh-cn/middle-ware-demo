package com.example.demo.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "t", concurrency = "2", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<byte[], byte[]> record, Acknowledgment ack) throws Exception {
        log.info("topic:{},partition:{},offset:{},key:{},value:{}", record.topic(), record.partition(), record.offset(), record.key(), record.value());
        ack.acknowledge();
    }

    @KafkaListener(topics = "tt", concurrency = "2",containerFactory="kafkaListenerContainerFactory2")
    public void onMessage2(ConsumerRecord<byte[], byte[]> record, Acknowledgment ack) throws Exception {
        log.info("topic:{},partition:{},offset:{},key:{},value:{}", record.topic(), record.partition(), record.offset(), record.key(), record.value());
        ack.acknowledge();
    }
}
