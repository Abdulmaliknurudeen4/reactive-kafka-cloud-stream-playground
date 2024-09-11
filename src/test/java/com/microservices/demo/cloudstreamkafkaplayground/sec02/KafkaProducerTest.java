package com.microservices.demo.cloudstreamkafkaplayground.sec02;

import com.microservices.demo.cloudstreamkafkaplayground.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Consumer;

@TestPropertySource(properties = {
        "sec=sec02",
        "spring.cloud.function.definition=producer;testConsumer",
        "spring.cloud.stream.bindings.testConsumer-in-0.destination=input-topic"
})
public class KafkaProducerTest extends AbstractIntegrationTest {

    private static final Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

    @Test
    public void producerTest() {
        sink.asFlux()
                .take(2)
                //time out in tests so the test doesn't wait forever
                .timeout(Duration.ofSeconds(5))
                .as(StepVerifier::create)
                .consumeNextWith(s -> Assertions.assertTrue(s.contains("msg ")))
                .consumeNextWith(s -> Assertions.assertTrue(s.contains("msg ")))
                .verifyComplete();
    }


    @TestConfiguration
    static class TestConfig {
        @Bean
        public Consumer<Flux<String>> testConsumer() {
            return f -> f.doOnNext(sink::tryEmitNext).subscribe();
        }
    }
}
