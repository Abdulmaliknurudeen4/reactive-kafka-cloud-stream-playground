package com.microservices.demo.cloudstreamkafkaplayground.secAA.processor;

import com.microservices.demo.cloudstreamkafkaplayground.common.MessageConverter;
import com.microservices.demo.cloudstreamkafkaplayground.common.Record;
import com.microservices.demo.cloudstreamkafkaplayground.secAA.dto.DigitalDelivery;
import com.microservices.demo.cloudstreamkafkaplayground.secAA.dto.OrderEvent;
import com.microservices.demo.cloudstreamkafkaplayground.secAA.dto.PhysicalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrderRouter {

    private static final Logger log = LoggerFactory.getLogger(OrderRouter.class);
    public static final String DESTINATION_HEADER = "spring.cloud.stream.sendto.destination";
    private static final String DIGITAL_DELIVERY_CHANNEL = "digital-delivery-out";
    private static final String PHYSICAL_DELIVERY_CHANNEL = "physical-delivery-out";
 /*   private static final String DIGITAL_DELIVERY_CHANNEL = "digital-topic"; // automatically sends to the topics if it doesn't find the bindings
    private static final String PHYSICAL_DELIVERY_CHANNEL = "physical-topic";

*/

    @Bean
    public Function<Flux<Message<OrderEvent>>, Flux<Message<?>>> processor(){
        return flux-> flux.map(MessageConverter::toRecord)
                .concatMap(this::route);
    }

    private Flux<? extends Message<?>> route(Record<OrderEvent> event){
      var msg=  switch (event.message().orderType()){
            //in case they're more delivery types
            case DIGITAL -> this.toDigitalDelivery(event.message());
            case PHYSICAL -> this.toPhysicalDelivery(event.message());
        };
      event.acknowledgment().acknowledge();
      return msg;
    }

    private Flux<Message<DigitalDelivery>> toDigitalDelivery(OrderEvent event){
        var digitalDelivery = new DigitalDelivery(event.productId(), "%s@gmail.com".formatted(event.customerId()));
        return Flux.just(MessageBuilder
                .withPayload(digitalDelivery)
                .setHeader(DESTINATION_HEADER, DIGITAL_DELIVERY_CHANNEL)
                .build());
    }

    private Flux<Message<?>> toPhysicalDelivery(OrderEvent event){
        var phsicalDeliver = new PhysicalDelivery(event.productId(),
                "%s street".formatted(event.customerId()),
                "%s city".formatted(event.customerId()),
                "%s street".formatted(event.customerId()));
        var digitalDelivery = new DigitalDelivery(event.productId(), "%s@gmail.com".formatted(event.customerId()));
        return Flux.just(MessageBuilder
                .withPayload(phsicalDeliver)
                .setHeader(DESTINATION_HEADER, PHYSICAL_DELIVERY_CHANNEL)
                .build(), MessageBuilder
                .withPayload(digitalDelivery)
                .setHeader(DESTINATION_HEADER, DIGITAL_DELIVERY_CHANNEL)
                .build());
    }
}
