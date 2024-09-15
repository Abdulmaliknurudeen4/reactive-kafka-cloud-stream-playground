package com.microservices.demo.cloudstreamkafkaplayground.sec11.dto;

public record Phone(int number) implements ContactMethod {
    @Override
    public void contact() {
        System.out.println("Contacting via phone " + number);
    }
}
