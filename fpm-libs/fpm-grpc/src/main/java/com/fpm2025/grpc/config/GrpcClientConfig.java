package com.fpm2025.grpc.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class GrpcClientConfig {

    @Value("${grpc.client.wallet.host:localhost}")
    private String walletHost;

    @Value("${grpc.client.wallet.port:9091}")
    private int walletPort;

    @Value("${grpc.client.transaction.host:localhost}")
    private String transactionHost;

    @Value("${grpc.client.transaction.port:9092}")
    private int transactionPort;

    @Value("${grpc.client.category.host:localhost}")
    private String categoryHost;

    @Value("${grpc.client.category.port:9093}")
    private int categoryPort;

    @Bean(name = "walletChannel")
    public ManagedChannel walletChannel() {
        log.info("Creating wallet gRPC channel: {}:{}", walletHost, walletPort);
        return ManagedChannelBuilder
                .forAddress(walletHost, walletPort)
                .usePlaintext()
                .keepAliveTime(30, TimeUnit.SECONDS)
                .keepAliveTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Bean(name = "transactionChannel")
    public ManagedChannel transactionChannel() {
        log.info("Creating transaction gRPC channel: {}:{}", transactionHost, transactionPort);
        return ManagedChannelBuilder
                .forAddress(transactionHost, transactionPort)
                .usePlaintext()
                .keepAliveTime(30, TimeUnit.SECONDS)
                .keepAliveTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Bean(name = "categoryChannel")
    public ManagedChannel categoryChannel() {
        log.info("Creating category gRPC channel: {}:{}", categoryHost, categoryPort);
        return ManagedChannelBuilder
                .forAddress(categoryHost, categoryPort)
                .usePlaintext()
                .keepAliveTime(30, TimeUnit.SECONDS)
                .keepAliveTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
