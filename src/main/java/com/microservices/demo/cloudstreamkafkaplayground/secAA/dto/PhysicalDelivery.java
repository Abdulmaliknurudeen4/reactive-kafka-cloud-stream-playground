package com.microservices.demo.cloudstreamkafkaplayground.secAA.dto;

public record PhysicalDelivery(int productId,
                               String street,
                               String city,
                               String country) {
}
