package com.microservices.demo.cloudstreamkafkaplayground.sec11;

import com.microservices.demo.cloudstreamkafkaplayground.sec11.dto.ContactMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Bean
    public Consumer<Flux<ContactMethod>> consumer() {
        return flux ->
                flux.doOnNext(s -> log.info("Consumer received {}", s))
                        .subscribe();
    }
}
