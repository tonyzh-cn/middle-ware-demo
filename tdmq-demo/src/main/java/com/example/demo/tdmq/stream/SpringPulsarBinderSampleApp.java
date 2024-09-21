package com.example.demo.tdmq.stream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.core.PulsarAdministration;

/**
 * @author zhangtao
 * @since 2024/9/15 18:18
 */
@SpringBootApplication
public class SpringPulsarBinderSampleApp {

    private static final Logger LOG = LoggerFactory.getLogger(SpringPulsarBinderSampleApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringPulsarBinderSampleApp.class, args);
    }

    private AtomicInteger counter = new AtomicInteger();

    @Bean
    PulsarAdministration pulsarAdministration(PulsarAdminBuilder builder){
        return new PulsarAdministration(builder);
    }

    @Bean
    ApplicationRunner fooSupplier(StreamBridge streamBridge) {
        return (args) -> {
            for (int i = 0; i < 10; i++) {
                Foo foo = new Foo("fooSupplier:" + i);
                streamBridge.send("fooSupplier-out-0", foo);
                LOG.info("++++++SOURCE {}------", "fooSupplier:" + i);
            }
        };
    }

    @Bean
    public Function<Foo, Bar> fooProcessor() {
        return (foo) -> {
            Bar bar = new Bar(foo.value);
            LOG.info("++++++PROCESSOR {} --> {}------", foo, bar);
            return bar;
        };
    }

    @Bean
    public Consumer<Bar> barLogger() {
        return (bar) -> LOG.info("++++++SINK {}------", bar);
    }

    @Data
    @AllArgsConstructor
    public static class Foo{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Bar{
        private String value;
    }
}