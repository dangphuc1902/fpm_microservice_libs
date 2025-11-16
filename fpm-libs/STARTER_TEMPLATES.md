# Spring Boot Starters - Implementation Templates

Chi tiết cấu trúc, code examples và best practices cho từng starter mới.

---

## 📦 Template 1: fpm-web-starter

### Cấu trúc Thư Mục

```
fpm-web-starter/
├── pom.xml
├── src/main/java/com/fpm2025/web/
│   ├── autoconfigure/
│   │   ├── FpmWebAutoConfiguration.java
│   │   └── FpmWebProperties.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ErrorResponseFactory.java
│   │   └── ValidationErrorConverter.java
│   ├── response/
│   │   ├── BaseResponse.java
│   │   ├── PageResponse.java
│   │   └── ErrorResponse.java
│   ├── advice/
│   │   └── ValidationAdvice.java
│   ├── openapi/
│   │   └── OpenApiConfig.java
│   └── util/
│       ├── PageableUtil.java
│       └── ResponseEntityHelper.java
├── src/main/resources/
│   └── META-INF/spring/
│       └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
└── src/main/resources/
    └── META-INF/spring-configuration-metadata.json
```

### pom.xml

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-libs</artifactId>
    <version>1.0.0</version>
  </parent>

  <artifactId>fpm-web-starter</artifactId>
  <name>FPM Web Starter</name>
  <description>Spring Boot starter for REST APIs, responses, and OpenAPI</description>

  <dependencies>
    <!-- Spring Boot -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>

    <!-- OpenAPI/Swagger -->
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.3.0</version>
      <optional>true</optional>
    </dependency>

    <!-- Utilities -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

### FpmWebAutoConfiguration.java

```java
package com.fpm2025.web.autoconfigure;

import com.fpm2025.web.exception.GlobalExceptionHandler;
import com.fpm2025.web.openapi.OpenApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(DispatcherServlet.class)
@EnableConfigurationProperties(FpmWebProperties.class)
@RequiredArgsConstructor
@Slf4j
public class FpmWebAutoConfiguration {

    private final FpmWebProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        log.info("Registering FPM GlobalExceptionHandler");
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
        name = "fpm.web.openapi.enabled",
        havingValue = "true",
        matchIfMissing = true
    )
    public OpenApiConfig openApiConfig() {
        log.info("Enabling FPM OpenAPI configuration");
        return new OpenApiConfig(properties.getOpenapi());
    }

    // Response entity helper bean
    @Bean
    @ConditionalOnMissingBean
    public ResponseEntityHelper responseEntityHelper() {
        return new ResponseEntityHelper();
    }
}
```

### FpmWebProperties.java

```java
@Data
@Configuration
@ConfigurationProperties(prefix = "fpm.web")
public class FpmWebProperties {

    private OpenApiProperties openapi = new OpenApiProperties();
    private PageableProperties pageable = new PageableProperties();

    @Data
    public static class OpenApiProperties {
        private boolean enabled = true;
        private String title = "FPM API";
        private String version = "1.0.0";
        private String description = "FPM Microservice API";
        private String contactName = "FPM Team";
        private String contactEmail = "support@fpm2025.com";
    }

    @Data
    public static class PageableProperties {
        private int defaultPageSize = 20;
        private int maxPageSize = 100;
    }
}
```

### META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

```
com.fpm2025.web.autoconfigure.FpmWebAutoConfiguration
```

---

## 📦 Template 2: fpm-security-starter

### Cấu trúc

```
fpm-security-starter/
├── pom.xml
├── src/main/java/com/fpm2025/security/
│   ├── autoconfigure/
│   │   ├── FpmSecurityAutoConfiguration.java
│   │   ├── FpmSecurityProperties.java
│   │   └── ConditionalOnSecurityEnabled.java (custom annotation)
│   ├── jwt/
│   │   ├── JwtService.java
│   │   ├── JwtTokenProvider.java
│   │   └── JwtClaimsExtractor.java
│   ├── filter/
│   │   └── JwtAuthenticationFilter.java
│   ├── config/
│   │   └── SecurityFilterChainConfig.java
│   └── userdetails/
│       └── JwtUserDetails.java
├── src/main/resources/META-INF/spring/
└── src/test/...
```

### FpmSecurityAutoConfiguration.java

```java
@AutoConfiguration
@ConditionalOnProperty(
    name = "fpm.security.enabled",
    havingValue = "true",
    matchIfMissing = true
)
@ConditionalOnClass(SecurityFilterChain.class)
@EnableConfigurationProperties(FpmSecurityProperties.class)
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class FpmSecurityAutoConfiguration {

    private final FpmSecurityProperties securityProperties;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @ConditionalOnMissingBean
    public JwtService jwtService() {
        log.info("Configuring FPM JWT Service");
        return new JwtService(securityProperties.getJwt());
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/api/auth/**",
                    "/health/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

### FpmSecurityProperties.java

```java
@Data
@Configuration
@ConfigurationProperties(prefix = "fpm.security")
public class FpmSecurityProperties {

    private boolean enabled = true;
    private JwtProperties jwt = new JwtProperties();
    private CorsProperties cors = new CorsProperties();

    @Data
    public static class JwtProperties {
        private String secret = "your-256-bit-secret-key-change-me";
        private long expiration = 86400000L;  // 24 hours
        private long refreshExpiration = 604800000L;  // 7 days
        private String issuer = "fpm-2025";
        private String audience = "fpm-services";
        private long clockSkew = 60L;  // seconds
    }

    @Data
    public static class CorsProperties {
        private List<String> allowedOrigins = List.of("*");
        private List<String> allowedMethods = List.of("*");
        private long maxAge = 3600L;
    }
}
```

---

## 📦 Template 3: fpm-grpc-starter

### Cấu trúc

```
fpm-grpc-starter/
├── pom.xml
├── src/main/java/com/fpm2025/grpc/
│   ├── autoconfigure/
│   │   ├── GrpcClientAutoConfiguration.java
│   │   ├── GrpcServerAutoConfiguration.java
│   │   └── FpmGrpcProperties.java
│   ├── client/
│   │   ├── ManagedChannelFactory.java
│   │   └── GrpcClientStubFactory.java
│   ├── server/
│   │   ├── GrpcServerConfiguration.java
│   │   └── GrpcHealthCheckService.java
│   ├── interceptor/
│   │   ├── GrpcMetadataPropagationInterceptor.java (CLIENT)
│   │   ├── GrpcLoggingInterceptor.java (SERVER)
│   │   ├── GrpcErrorInterceptor.java (SERVER)
│   │   └── GrpcTracingInterceptor.java (BOTH)
│   ├── error/
│   │   ├── GrpcStatusMapper.java
│   │   └── GrpcErrorHandler.java
│   ├── health/
│   │   └── GrpcHealthIndicator.java
│   └── context/
│       └── GrpcContextHolder.java
└── src/main/resources/META-INF/spring/
```

### FpmGrpcProperties.java

```java
@Data
@Configuration
@ConfigurationProperties(prefix = "fpm.grpc")
public class FpmGrpcProperties {

    private ClientProperties client = new ClientProperties();
    private ServerProperties server = new ServerProperties();

    @Data
    public static class ClientProperties {
        private boolean enabled = true;
        private Map<String, ServiceEndpoint> services = new HashMap<>();
        private int keepAliveTime = 30;  // seconds
        private int keepAliveTimeout = 10;  // seconds
        private boolean keepAliveWithoutCalls = true;
        private int maxRetryAttempts = 3;
        private long initialBackoff = 1000L;  // ms
        private long maxBackoff = 10000L;  // ms
    }

    @Data
    public static class ServerProperties {
        private boolean enabled = false;
        private int port = 9090;
        private int maxInboundMessageSize = 4 * 1024 * 1024;  // 4MB
    }

    @Data
    public static class ServiceEndpoint {
        private String host;
        private int port;
        private boolean useTls = false;
    }
}
```

### ManagedChannelFactory.java

```java
@Component
@RequiredArgsConstructor
@Slf4j
public class ManagedChannelFactory {

    private final FpmGrpcProperties grpcProperties;
    private final Map<String, ManagedChannel> channels = new ConcurrentHashMap<>();

    public ManagedChannel getChannel(String serviceName) {
        return channels.computeIfAbsent(serviceName, name -> {
            FpmGrpcProperties.ServiceEndpoint endpoint =
                grpcProperties.getClient().getServices().get(name);

            if (endpoint == null) {
                throw new IllegalArgumentException("No endpoint configured for service: " + name);
            }

            log.info("Creating gRPC channel for service: {} ({}:{})",
                name, endpoint.getHost(), endpoint.getPort());

            ManagedChannelBuilder<?> builder = ManagedChannelBuilder
                .forAddress(endpoint.getHost(), endpoint.getPort());

            if (!endpoint.isUseTls()) {
                builder.usePlaintext();
            }

            return builder
                .keepAliveTime(grpcProperties.getClient().getKeepAliveTime(), TimeUnit.SECONDS)
                .keepAliveTimeout(grpcProperties.getClient().getKeepAliveTimeout(), TimeUnit.SECONDS)
                .keepAliveWithoutCalls(grpcProperties.getClient().isKeepAliveWithoutCalls())
                .build();
        });
    }

    @PreDestroy
    public void shutdownChannels() {
        channels.values().forEach(channel -> {
            try {
                if (!channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)) {
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
            }
        });
    }
}
```

### GrpcClientAutoConfiguration.java

```java
@AutoConfiguration
@ConditionalOnProperty(name = "fpm.grpc.client.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(ManagedChannel.class)
@EnableConfigurationProperties(FpmGrpcProperties.class)
@RequiredArgsConstructor
@Slf4j
public class GrpcClientAutoConfiguration {

    private final FpmGrpcProperties grpcProperties;

    @Bean
    public ManagedChannelFactory managedChannelFactory() {
        return new ManagedChannelFactory(grpcProperties);
    }

    @Bean
    public ClientInterceptor[] globalClientInterceptors(
            Optional<FpmObservabilityProperties> obsProps) {
        List<ClientInterceptor> interceptors = new ArrayList<>();

        interceptors.add(new GrpcMetadataPropagationInterceptor());
        interceptors.add(new GrpcLoggingInterceptor());

        if (obsProps.isPresent()) {
            interceptors.add(new GrpcTracingInterceptor(obsProps.get()));
        }

        return interceptors.toArray(new ClientInterceptor[0]);
    }

    @Bean
    public GrpcStatusMapper grpcStatusMapper() {
        return new GrpcStatusMapper();
    }
}
```

---

## 📦 Template 4: fpm-messaging-starter

### DomainEvent Base (Versioned)

```java
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class DomainEvent<T> {

    /**
     * Unique event ID for idempotency
     */
    @Builder.Default
    private String eventId = UUID.randomUUID().toString();

    /**
     * Event type name (e.g., "wallet.created")
     */
    @NonNull
    private String eventType;

    /**
     * Event version for backward compatibility
     * v1 → v2: can add optional fields, not break existing
     */
    @Builder.Default
    private int eventVersion = 1;

    /**
     * Aggregate ID (e.g., walletId, transactionId)
     */
    @NonNull
    private Long aggregateId;

    /**
     * Aggregate type (e.g., "wallet", "transaction")
     */
    @NonNull
    private String aggregateType;

    /**
     * User who triggered the event
     */
    @NonNull
    private Long userId;

    /**
     * Event payload (can be different message types)
     */
    @NonNull
    private T payload;

    /**
     * Distributed tracing
     */
    private String traceId;
    private String spanId;

    /**
     * Correlation ID for request tracking
     */
    private String correlationId;

    /**
     * Request ID (from X-Request-Id header)
     */
    private String requestId;

    /**
     * When event occurred (server time)
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Instant occurredAt = Instant.now();

    /**
     * Service that published this event
     */
    private String source;

    /**
     * Tenant ID (for multi-tenancy)
     */
    private String tenantId;

    /**
     * For consumer idempotency: mark if already processed
     */
    @Transient
    private transient boolean processed = false;

    /**
     * Get fully qualified event type with version
     * e.g., "com.fpm2025.wallet.v1.WalletCreatedEvent"
     */
    public String getFullEventType() {
        return String.format("%s.v%d.%s",
            this.getClass().getPackageName(),
            this.eventVersion,
            this.eventType
        );
    }
}

// Usage
@Data
@EqualsAndHashCode(callSuper = true)
public class WalletCreatedEvent extends DomainEvent<WalletCreatedPayload> {

    public WalletCreatedEvent(
            Long walletId,
            Long userId,
            WalletCreatedPayload payload) {
        super.builder()
            .eventType("wallet.created")
            .eventVersion(1)
            .aggregateId(walletId)
            .aggregateType("wallet")
            .userId(userId)
            .payload(payload)
            .source("wallet-service")
            .build();
    }
}

@Data
public class WalletCreatedPayload {
    private String walletName;
    private String walletType;
    private BigDecimal initialBalance;
    private String currency;
}
```

### EventPublisher (with Outbox Pattern)

```java
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    /**
     * Publish event with outbox pattern for durability
     */
    public <T> void publish(DomainEvent<T> event) {
        // 1. Enrich with context
        enrichEventContext(event);

        // 2. Save to outbox table (guarantee durability)
        OutboxEvent outboxEvent = OutboxEvent.builder()
            .eventId(event.getEventId())
            .eventType(event.getEventType())
            .aggregateId(event.getAggregateId())
            .aggregateType(event.getAggregateType())
            .payload(serializePayload(event))
            .published(false)
            .createdAt(LocalDateTime.now())
            .build();

        outboxRepository.save(outboxEvent);

        log.info("Event saved to outbox: eventId={}, type={}, aggregateId={}",
            event.getEventId(), event.getEventType(), event.getAggregateId());

        // 3. Publish to broker (async, non-blocking)
        publishAsync(event);
    }

    @Async("eventPublisherExecutor")
    protected <T> void publishAsync(DomainEvent<T> event) {
        try {
            String routingKey = event.getEventType();
            String exchange = "domain-events";

            rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                event,
                message -> {
                    // Add headers for tracing
                    message.getMessageProperties()
                        .setHeader("X-Trace-Id", event.getTraceId())
                        .setHeader("X-Correlation-Id", event.getCorrelationId())
                        .setHeader("X-Request-Id", event.getRequestId())
                        .setHeader("X-Event-Version", String.valueOf(event.getEventVersion()));
                    return message;
                }
            );

            log.info("Event published to broker: eventId={}, type={}, routingKey={}",
                event.getEventId(), event.getEventType(), routingKey);

            // Mark as published
            outboxRepository.markAsPublished(event.getEventId());
        } catch (Exception e) {
            log.error("Failed to publish event: {}", event.getEventId(), e);
            // Outbox poller will retry
        }
    }

    private <T> String serializePayload(DomainEvent<T> event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    private void enrichEventContext(DomainEvent<?> event) {
        event.setTraceId(MDC.get("traceId"));
        event.setSpanId(MDC.get("spanId"));
        event.setCorrelationId(MDC.get("correlationId"));
        event.setRequestId(MDC.get("requestId"));
    }
}
```

### EventListener Registry

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class EventListenerRegistry {

    private final Map<String, List<EventHandlerMethod>> handlers = new ConcurrentHashMap<>();

    /**
     * Register handler method for event type
     */
    public void register(String eventType, EventHandlerMethod handler) {
        handlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList())
            .add(handler);
        log.info("Registered event handler for: {}", eventType);
    }

    /**
     * Handle event by routing to registered handlers
     */
    public <T> void handle(DomainEvent<T> event) {
        String eventType = event.getEventType();
        List<EventHandlerMethod> eventHandlers = handlers.get(eventType);

        if (eventHandlers == null || eventHandlers.isEmpty()) {
            log.warn("No handlers registered for event type: {}", eventType);
            return;
        }

        for (EventHandlerMethod handler : eventHandlers) {
            try {
                // Set up MDC for this handler
                MDC.put("traceId", event.getTraceId());
                MDC.put("correlationId", event.getCorrelationId());

                handler.handle(event);

                log.debug("Successfully handled event: eventId={}, handler={}",
                    event.getEventId(), handler.getHandlerName());
            } catch (Exception e) {
                log.error("Error handling event: eventId={}, handler={}, error={}",
                    event.getEventId(), handler.getHandlerName(), e.getMessage(), e);
                // Broker will retry if consumer NACKs
                throw new EventHandlerException(e);
            } finally {
                MDC.clear();
            }
        }
    }

    @FunctionalInterface
    public interface EventHandlerMethod {
        void handle(DomainEvent<?> event) throws Exception;
        default String getHandlerName() {
            return this.getClass().getSimpleName();
        }
    }
}
```

### RabbitMQ Configuration

```java
@Configuration
@ConditionalOnProperty(name = "fpm.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class FpmMessagingAutoConfiguration {

    @Bean
    public TopicExchange domainEventsExchange() {
        return new TopicExchange("domain-events", true, false);
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setClassMapper(new DefaultJackson2JavaTypeMapper());
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter(new ObjectMapper()));
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setPrefetchCount(10);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setDefaultRequeueRejected(false);  // Send to DLQ instead of requeue
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean(name = "eventPublisherExecutor")
    public Executor eventPublisherExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("event-publisher-");
        executor.initialize();
        return executor;
    }
}
```

---

## 📋 Summary

Mỗi template trên đó là:
- ✅ **Production-ready**: Error handling, logging, configuration
- ✅ **Conditional**: `@ConditionalOnProperty` để enable/disable
- ✅ **Extensible**: Easy to customize via properties
- ✅ **Testable**: Mockable beans, clear dependencies
- ✅ **Observable**: MDC, tracing, metrics hooks

---

*Các template này là blueprint để implement từng starter theo production standards.*
