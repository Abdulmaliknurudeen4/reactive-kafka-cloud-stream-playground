package com.microservices.demo.cloudstreamkafkaplayground.sec11.dto;

public record Email(String email) implements ContactMethod {
    @Override
    public void contact() {
        System.out.println("Contacting via " + email);
    }
}
