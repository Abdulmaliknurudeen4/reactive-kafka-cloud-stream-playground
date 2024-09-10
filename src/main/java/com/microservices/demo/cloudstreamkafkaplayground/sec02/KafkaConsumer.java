package com.microservices.demo.cloudstreamkafkaplayground.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Bean
    public Consumer<Flux<String>> consumer(){
        return flux->
                flux.doOnNext(s -> log.info("Consumer received {}",s)).subscribe();
    }
}
