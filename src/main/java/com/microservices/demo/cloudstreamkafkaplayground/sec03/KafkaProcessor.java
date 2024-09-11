package com.microservices.demo.cloudstreamkafkaplayground.sec03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class KafkaProcessor {
    private static final Logger log = LoggerFactory.getLogger(KafkaProcessor.class);


    @Bean
    public Function<Flux<String>, Flux<String>> processor() {
        return flux ->
                flux.doOnNext(s -> log.info("processor received {}", s))
                        .concatMap(this::process)
                        .doOnNext(s -> log.info("after processing {}", s));

    }

    //service layer impl
    private Mono<String> process(String input) {
        //could be a database/io operation
        return Mono.just(input)
                .map(String::toUpperCase);
    }
}
