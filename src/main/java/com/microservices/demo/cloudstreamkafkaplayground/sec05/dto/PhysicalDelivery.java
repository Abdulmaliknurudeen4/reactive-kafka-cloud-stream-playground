package com.microservices.demo.cloudstreamkafkaplayground.sec05.dto;

public record PhysicalDelivery(int productId,
                               String street,
                               String city,
                               String country) {
}
