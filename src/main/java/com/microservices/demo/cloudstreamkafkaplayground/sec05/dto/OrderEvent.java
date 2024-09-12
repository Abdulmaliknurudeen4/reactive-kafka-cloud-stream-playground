package com.microservices.demo.cloudstreamkafkaplayground.sec05.dto;

public record OrderEvent(int customerId,
                         int productId,
                         OrderType orderType) {
}
