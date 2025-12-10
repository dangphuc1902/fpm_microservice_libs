package com.fpm2025.messaging.kafka.config;

import com.fpm2025.messaging.kafka.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka topic configuration
 * Auto-creates topics if they don't exist
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaTopicConfig {

    private static final int DEFAULT_PARTITIONS = 3;
    private static final short DEFAULT_REPLICATION_FACTOR = 1;

    // User topics
    @Bean
    public NewTopic userCreatedTopic() {
        return createTopic(KafkaTopics.USER_CREATED);
    }

    @Bean
    public NewTopic userUpdatedTopic() {
        return createTopic(KafkaTopics.USER_UPDATED);
    }

    @Bean
    public NewTopic userDeletedTopic() {
        return createTopic(KafkaTopics.USER_DELETED);
    }

    // Wallet topics
    @Bean
    public NewTopic walletCreatedTopic() {
        return createTopic(KafkaTopics.WALLET_CREATED);
    }

    @Bean
    public NewTopic walletUpdatedTopic() {
        return createTopic(KafkaTopics.WALLET_UPDATED);
    }

    @Bean
    public NewTopic balanceChangedTopic() {
        return createTopic(KafkaTopics.BALANCE_CHANGED);
    }

    // Transaction topics
    @Bean
    public NewTopic transactionCreatedTopic() {
        return createTopic(KafkaTopics.TRANSACTION_CREATED);
    }

    @Bean
    public NewTopic transactionUpdatedTopic() {
        return createTopic(KafkaTopics.TRANSACTION_UPDATED);
    }

    @Bean
    public NewTopic transactionDeletedTopic() {
        return createTopic(KafkaTopics.TRANSACTION_DELETED);
    }

    // Category topics
    @Bean
    public NewTopic categoryAssignedTopic() {
        return createTopic(KafkaTopics.CATEGORY_ASSIGNED);
    }

    @Bean
    public NewTopic budgetExceededTopic() {
        return createTopic(KafkaTopics.BUDGET_EXCEEDED);
    }

    // Notification topics
    @Bean
    public NewTopic notificationParsedTopic() {
        return createTopic(KafkaTopics.NOTIFICATION_PARSED);
    }

    @Bean
    public NewTopic pushNotificationTopic() {
        return createTopic(KafkaTopics.PUSH_NOTIFICATION);
    }

    // OCR topics
    @Bean
    public NewTopic ocrCompletedTopic() {
        return createTopic(KafkaTopics.OCR_COMPLETED);
    }

    // Sharing topics
    @Bean
    public NewTopic familyCreatedTopic() {
        return createTopic(KafkaTopics.FAMILY_CREATED);
    }

    @Bean
    public NewTopic memberAddedTopic() {
        return createTopic(KafkaTopics.MEMBER_ADDED);
    }

    /**
     * Create topic with default settings
     * @param topicName Topic name
     * @return NewTopic
     */
    private NewTopic createTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICATION_FACTOR)
                .config("retention.ms", "604800000") // 7 days
                .config("cleanup.policy", "delete")
                .build();
    }

    /**
     * Create topic with custom partitions
     * @param topicName Topic name
     * @param partitions Number of partitions
     * @return NewTopic
     */
    private NewTopic createTopic(String topicName, int partitions) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(DEFAULT_REPLICATION_FACTOR)
                .config("retention.ms", "604800000")
                .build();
    }

    /**
     * Create compacted topic (for changelog/state)
     * @param topicName Topic name
     * @return NewTopic
     */
    private NewTopic createCompactedTopic(String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICATION_FACTOR)
                .config("cleanup.policy", "compact")
                .config("min.compaction.lag.ms", "60000") // 1 minute
                .build();
    }
}