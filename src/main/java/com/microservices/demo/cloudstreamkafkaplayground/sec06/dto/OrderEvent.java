package com.microservices.demo.cloudstreamkafkaplayground.sec06.dto;

public record OrderEvent(int customerId,
                         int productId,
                         OrderType orderType) {
}
