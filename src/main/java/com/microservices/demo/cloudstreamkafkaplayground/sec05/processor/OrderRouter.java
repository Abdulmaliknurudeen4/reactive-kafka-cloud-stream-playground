package com.microservices.demo.cloudstreamkafkaplayground.sec05.processor;

import com.microservices.demo.cloudstreamkafkaplayground.common.MessageConverter;
import com.microservices.demo.cloudstreamkafkaplayground.sec05.dto.DigitalDelivery;
import com.microservices.demo.cloudstreamkafkaplayground.sec05.dto.OrderEvent;
import com.microservices.demo.cloudstreamkafkaplayground.sec05.dto.PhysicalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class OrderRouter {

    private static final Logger log = LoggerFactory.getLogger(OrderRouter.class);
//    private static final String DIGITAL_DELIVERY_CHANNEL = "digital-delivery-out";
//    private static final String PHYSICAL_DELIVERY_CHANNEL = "physical-delivery-out";
    private static final String DIGITAL_DELIVERY_CHANNEL = "digital-topic"; // automatically sends to the topics if it doesn't find the bindings
    private static final String PHYSICAL_DELIVERY_CHANNEL = "physical-topic";

    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Function<Flux<Message<OrderEvent>>, Mono<Void>> processor(){
        return flux-> flux.map(MessageConverter::toRecord)
                .doOnNext(r -> this.route(r.message()))
                .doOnNext(r->r.acknowledgment().acknowledge())
                .then();
    }

    private void route(OrderEvent event){
        switch (event.orderType()){
            //in case they're more delivery types
            case DIGITAL -> this.toDigitalDelivery(event);
            case PHYSICAL -> this.toPhysicalDelivery(event);
        }
    }

    private void toDigitalDelivery(OrderEvent event){
        var digitalDelivery = new DigitalDelivery(event.productId(), "%s@gmail.com".formatted(event.customerId()));
        this.streamBridge.send(DIGITAL_DELIVERY_CHANNEL,digitalDelivery);
    }

    private void toPhysicalDelivery(OrderEvent event){
        var phsicalDeliver = new PhysicalDelivery(event.productId(),
                "%s street".formatted(event.customerId()),
                "%s city".formatted(event.customerId()),
                "%s street".formatted(event.customerId()));
        this.streamBridge.send(PHYSICAL_DELIVERY_CHANNEL,phsicalDeliver);
    }
}
