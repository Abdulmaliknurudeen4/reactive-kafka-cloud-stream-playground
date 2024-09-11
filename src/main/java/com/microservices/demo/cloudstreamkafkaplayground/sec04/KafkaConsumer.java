package com.microservices.demo.cloudstreamkafkaplayground.sec04;

import com.microservices.demo.cloudstreamkafkaplayground.common.MessageConverter;
import com.microservices.demo.cloudstreamkafkaplayground.common.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Bean
    public Consumer<Flux<Message<String>>> consumer() {
        return flux ->
                flux
                        .map(MessageConverter::toRecord)
                        .doOnNext(this::printMessageDetails)
                        .doOnNext(s -> log.info("Consumer received {}", s)).subscribe();
    }

    private void printMessageDetails(Record<String> record) {
        log.info("payload: {}", record.message());
        log.info("key: {}", record.key());
        record.acknowledgment().acknowledge();
    }
}
