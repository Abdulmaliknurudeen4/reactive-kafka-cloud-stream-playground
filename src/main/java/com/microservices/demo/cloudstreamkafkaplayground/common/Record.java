package com.microservices.demo.cloudstreamkafkaplayground.common;

import reactor.kafka.receiver.ReceiverOffset;

public record Record<T>(String key,
                        T message,
                        ReceiverOffset acknowledgment) {
}
