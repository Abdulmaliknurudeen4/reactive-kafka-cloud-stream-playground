package com.microservices.demo.cloudstreamkafkaplayground.secAA.dto;

public record OrderEvent(int customerId,
                         int productId,
                         OrderType orderType) {
}
