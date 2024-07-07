package com.example.demo.tdmq;

/**
 * @author zhangtao
 * @since 2024/7/7 13:15
 */
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 生产者相关配置
 * 1.topic要提前在控制台中完成创建
 * 2.消息类型需要实现Serializable接口
 * 3.如果一个topic不能绑定不同的数据类型
 */
@Configuration
public class ProducerConfiguration {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                // topic1 生产者
                .addProducer("topic1")
                // topic2 生产者
                .addProducer("topic2");
    }
}