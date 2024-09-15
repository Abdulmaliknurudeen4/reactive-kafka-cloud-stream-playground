package com.microservices.demo.cloudstreamkafkaplayground.sec11;

import com.microservices.demo.cloudstreamkafkaplayground.AbstractIntegrationTest;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

@TestPropertySource(properties = {
        "sec=sec11",})
class EncodingDecodingTest extends AbstractIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(EncodingDecodingTest.class);

    @Test
    public void encodingDecodingTest() throws InterruptedException {
        var sender = this.<Integer, Integer>createSender(o ->
                o.withKeySerializer(new IntegerSerializer())
                        .withValueSerializer(new IntegerSerializer()));

        Flux.range(1, 3)
                .map(i -> this.toSenderRecord("input-topic", i, i))
                .as(sender::send)
                .doOnNext(sr -> log.info("result {}", sr.correlationMetadata()))
                .subscribe();
        Thread.sleep(5_000);
    }

}
