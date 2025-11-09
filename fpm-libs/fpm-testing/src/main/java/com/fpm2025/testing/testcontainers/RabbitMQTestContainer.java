package com.fpm2025.testing.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public abstract class RabbitMQTestContainer {

    private static final String RABBITMQ_IMAGE = "rabbitmq:3.13-management-alpine";

    protected static final RabbitMQContainer rabbitMQContainer;

    static {
        rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse(RABBITMQ_IMAGE))
                .withReuse(true);

        rabbitMQContainer.start();

        log.info("RabbitMQ container started: {}:{}",
                rabbitMQContainer.getHost(),
                rabbitMQContainer.getAmqpPort());
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    public static RabbitMQContainer getContainer() {
        return rabbitMQContainer;
    }
}
