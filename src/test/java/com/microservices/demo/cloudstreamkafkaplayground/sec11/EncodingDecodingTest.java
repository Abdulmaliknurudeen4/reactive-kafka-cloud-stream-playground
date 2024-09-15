package com.microservices.demo.cloudstreamkafkaplayground.sec11;

import com.microservices.demo.cloudstreamkafkaplayground.AbstractIntegrationTest;
import com.microservices.demo.cloudstreamkafkaplayground.sec11.dto.ContactMethod;
import com.microservices.demo.cloudstreamkafkaplayground.sec11.dto.Email;
import com.microservices.demo.cloudstreamkafkaplayground.sec11.dto.Phone;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@TestPropertySource(properties = {
        "sec=sec11",
        "spring.cloud.function.definition=consumer;cmProducer",
        "spring.cloud.stream.bindings.cmProducer-out-0.destination=input-topic",
        "spring.cloud.stream.bindings.cmProducer-out-0.producer.use-native-encoding=true",
        "spring.cloud.stream.kafka.bindings.cmProducer-out-0.producer.configuration.value.serializer=org.springframework.kafka.support.serializer.JsonSerializer"
})
class EncodingDecodingTest extends AbstractIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(EncodingDecodingTest.class);

    @Test
    public void encodingDecodingTest() throws InterruptedException {
        /*var sender = this.<Integer, Integer>createSender(o ->
                o.withKeySerializer(new IntegerSerializer())
                        .withValueSerializer(new IntegerSerializer()));

        Flux.range(1, 3)
                .map(i -> this.toSenderRecord("input-topic", i, i))
                .as(sender::send)
                .doOnNext(sr -> log.info("result {}", sr.correlationMetadata()))
                .subscribe();*/
        Thread.sleep(1_000);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public Supplier<Flux<ContactMethod>> cmProducer() {
            return () -> Flux.just(
                    new Email("abdulmaliknurudeen4@gmail.com"),
                    new Phone(12345)
            );
        }

    }

}
