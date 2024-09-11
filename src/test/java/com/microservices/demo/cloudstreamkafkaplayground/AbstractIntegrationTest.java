package com.microservices.demo.cloudstreamkafkaplayground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = {
        "logging.level.root=ERROR",
        "logging.level.com.microservices.demo.cloudstreamkafkaplayground=INFO",
        "spring.cloud.stream.kafka.binder.configuration.auto.offset.reset=earliest"
})
@EmbeddedKafka(
        partitions = 1,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers",
        kraft = true)
@DirtiesContext
public abstract class AbstractIntegrationTest {
    @Autowired
    private EmbeddedKafkaBroker broker;


}
