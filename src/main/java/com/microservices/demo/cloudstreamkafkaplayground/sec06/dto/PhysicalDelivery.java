package com.microservices.demo.cloudstreamkafkaplayground.sec06.dto;

public record PhysicalDelivery(int productId,
                               String street,
                               String city,
                               String country) {
}
