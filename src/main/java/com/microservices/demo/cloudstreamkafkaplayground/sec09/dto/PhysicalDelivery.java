package com.microservices.demo.cloudstreamkafkaplayground.sec09.dto;

public record PhysicalDelivery(int productId,
                               String street,
                               String city,
                               String country) {
}
