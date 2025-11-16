# FPM Libraries - Refactoring Plan to Production-Ready Architecture

## 🎯 Mục tiêu Refactoring

Chuyển đổi từ cấu trúc "monolithic libs" sang **Spring Boot Starters + Contracts** pattern, tối ưu hóa cho microservices enterprise-grade.

---

## 📊 So sánh Before vs After

### Before (Hiện tại)
```
fpm-libs/
├── fpm-core (monolithic - 21 files)
├── fpm-domain
├── fpm-grpc (mixed: stubs + custom code)
├── fpm-messaging
├── fpm-testing (integration test utils)
├── shared-utils (proto, nhưng tên chưa rõ)
└── pom.xml (parent, chưa có BOM)
```

**Vấn đề:**
- ❌ Khi dùng chỉ 1 tính năng (Redis), phải kéo tất cả dependencies của fpm-core
- ❌ Proto stubs ở fpm-grpc, nhưng .proto files ở shared-utils
- ❌ Không có centralized version management
- ❌ Thiếu observability/tracing
- ❌ Không có Spring Boot auto-configuration pattern

### After (Mục tiêu)
```
fpm-libs/
├── fpm-bom/ (Bill of Materials - central version mgmt)
├── fpm-domain/ (enums, constants, domain types)
├── fpm-proto/ (contracts: .proto files ONLY)
├── fpm-grpc-starter/ (auto-config, interceptors, factories)
├── fpm-web-starter/ (REST, GlobalExceptionHandler, Swagger)
├── fpm-security-starter/ (JWT, Spring Security, conditional)
├── fpm-redis-starter/ (Redis templates, auto-config)
├── fpm-messaging-starter/ (Event base, RabbitMQ/Kafka, publisher/subscriber)
├── fpm-observability-starter/ (Logging, Tracing, Metrics)
├── fpm-test-support/ (Testcontainers, Builders, Assertions - test scope)
├── fpm-config-server/ (Spring Cloud Config Server)
├── parent-pom.xml
└── README.md (updated)
```

**Lợi ích:**
- ✅ **Granular dependencies**: Service chỉ import cần thiết
- ✅ **Spring Boot Starters**: Auto-configuration + conditional loading
- ✅ **Centralized versioning**: BOM đảm bảo consistency
- ✅ **Proto versioning**: package v1, v2 ready
- ✅ **Observability built-in**: Tracing, logging, metrics
- ✅ **Better testability**: Mock/stub factories
- ✅ **Config management**: Tách repo config

---

## 🔄 Chi Tiết Refactoring từng Module

### Phase 1: Chuẩn Bị (foundation)

#### 1.1 Tạo fpm-bom

**Mục đích:** Centralized dependency & plugin versioning

```xml
<!-- fpm-bom/pom.xml -->
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-bom</artifactId>
  <version>1.0.0</version>
  <packaging>pom</packaging>

  <properties>
    <spring-boot.version>3.3.5</spring-boot.version>
    <grpc.version>1.62.2</grpc.version>
    <protobuf.version>3.25.3</protobuf.version>
    <protoc-plugin.version>0.6.1</protoc-plugin.version>
    <testcontainers.version>1.19.7</testcontainers.version>
    <micrometer.version>1.12.0</micrometer.version>
    <opentelemetry.version>1.32.0</opentelemetry.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <!-- Spring Boot -->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-bom</artifactId>
        <version>${spring-boot.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <!-- gRPC BOM -->
      <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-bom</artifactId>
        <version>${grpc.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <!-- Testcontainers BOM -->
      <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers-bom</artifactId>
        <version>${testcontainers.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <!-- Observability -->
      <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-bom</artifactId>
        <version>${micrometer.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-bom</artifactId>
        <version>${opentelemetry.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <!-- Internal Libraries -->
      <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-domain</artifactId>
        <version>${project.version}</version>
      </dependency>
      <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-proto</artifactId>
        <version>${project.version}</version>
      </dependency>
      <!-- ... etc -->
    </dependencies>
  </dependencyManagement>

  <pluginManagement>
    <plugins>
      <!-- Maven Compiler -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
        <configuration>
          <release>21</release>
          <enablePreview>false</enablePreview>
        </configuration>
      </plugin>

      <!-- Protobuf -->
      <plugin>
        <groupId>org.xolstice.maven.plugins</groupId>
        <artifactId>protobuf-maven-plugin</artifactId>
        <version>${protoc-plugin.version}</version>
      </plugin>

      <!-- Enforcer: Prevent version drift -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-enforcer-plugin</artifactId>
        <version>3.4.1</version>
        <executions>
          <execution>
            <id>enforce-versions</id>
            <goals>
              <goal>enforce</goal>
            </goals>
            <configuration>
              <rules>
                <requireMavenVersion>
                  <version>[3.8.1,)</version>
                </requireMavenVersion>
                <requireJavaVersion>
                  <version>[21,)</version>
                </requireJavaVersion>
                <dependencyConvergence/>
              </rules>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </pluginManagement>
</project>
```

#### 1.2 Update parent-pom.xml

```xml
<!-- fpm-libs/pom.xml -->
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.3.5</version>
</parent>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.fpm2025</groupId>
      <artifactId>fpm-bom</artifactId>
      <version>${project.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<modules>
  <module>fpm-bom</module>
  <module>fpm-domain</module>
  <module>fpm-proto</module>
  <module>fpm-grpc-starter</module>
  <module>fpm-web-starter</module>
  <module>fpm-security-starter</module>
  <module>fpm-redis-starter</module>
  <module>fpm-messaging-starter</module>
  <module>fpm-observability-starter</module>
  <module>fpm-test-support</module>
  <module>fpm-config-server</module>
</modules>
```

---

### Phase 2: Tách fpm-core thành Starters

#### 2.1 fpm-web-starter

**Chỉ trách nhiệm:** REST response wrapping, global exception handling, validation, Swagger

```java
// com.fpm2025.web.autoconfigure.FpmWebAutoConfiguration
@Configuration
@ConditionalOnWebApplication
public class FpmWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnProperty(name = "fpm.web.openapi.enabled", havingValue = "true", matchIfMissing = true)
    public OpenApiConfig openApiConfig() {
        return new OpenApiConfig();
    }

    // ... validators, interceptors
}
```

**Dependencies:**
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

#### 2.2 fpm-security-starter

**Chỉ trách nhiệm:** JWT, Spring Security filters, conditional loading

```java
@Configuration
@ConditionalOnProperty(name = "fpm.security.enabled", havingValue = "true", matchIfMissing = true)
public class FpmSecurityAutoConfiguration {

    @Bean
    public JwtService jwtService(FpmSecurityProperties props) { ... }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() { ... }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) { ... }
}
```

**Key: @ConditionalOnProperty** → có thể disable toàn bộ security dùng property `fpm.security.enabled=false` (tiện cho test)

#### 2.3 fpm-redis-starter

**Chỉ trách nhiệm:** Redis template, caching config

```java
@Configuration
@ConditionalOnProperty(name = "fpm.redis.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RedisTemplate.class)
public class FpmRedisAutoConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) { ... }

    @Bean
    public RedisHealthIndicator redisHealthIndicator() { ... }
}
```

---

### Phase 3: Refactor gRPC (Split Concerns)

#### 3.1 fpm-proto (NEW)

**Nội dung:** Chỉ `.proto` files, KHÔNG commit generated code

```
src/main/proto/
├── fpm/common/v1/
│   ├── common.proto        (Status, Error, Pagination, validation)
│   └── metadata.proto      (TraceId, CorrelationId)
├── fpm/transaction/v1/
│   └── transaction.proto   (TransactionService, models)
├── fpm/wallet/v1/
│   └── wallet.proto
├── fpm/category/v1/
│   └── category.proto
└── fpm/user_auth/v1/
    └── user_auth.proto

pom.xml:
  <packaging>jar</packaging>
  <plugin>protobuf-maven-plugin
    <goals>compile, compile-custom (grpc)</goals>
    <generateProtoGeneratedDirectory>target/generated-sources</generateProtoGeneratedDirectory>
  </plugin>
```

**Protobuf Best Practices:**
```protobuf
syntax = "proto3";

// Option 1: Package versioning
package fpm.transaction.v1;

option java_multiple_files = true;
option java_package = "com.fpm2025.grpc.v1.transaction";
option java_outer_classname = "TransactionProto";

// Use message names carefully: TransactionServiceV1
service TransactionServiceV1 {
  rpc CreateTransaction(CreateTransactionRequest) returns (CreateTransactionResponse);
  // ... methods
}

// Common pattern: command/request/response triad
message CreateTransactionRequest {
  string wallet_id = 1;
  string category_id = 2;
  double amount = 3;
  string transaction_type = 4;  // Reference fpm.common.v1.TransactionType
  string note = 5;
  string user_id = 6;
  // Reserved for future fields:
  // reserved 10 to 15;
}

message CreateTransactionResponse {
  fpm.common.v1.Status status = 1;
  Transaction transaction = 2;
}

// Enum DEFINED IN common.proto, shared
enum TransactionType {
  TRANSACTION_TYPE_UNSPECIFIED = 0;
  INCOME = 1;
  EXPENSE = 2;
  TRANSFER = 3;
}
```

#### 3.2 fpm-grpc-starter (NEW)

**Nội dung:** AutoConfiguration, interceptors, client factories

```java
// com.fpm2025.grpc.autoconfigure.GrpcClientAutoConfiguration
@Configuration
@ConditionalOnClass(ManagedChannel.class)
@EnableConfigurationProperties(FpmGrpcProperties.class)
public class GrpcClientAutoConfiguration {

    @Bean
    @Scope("prototype")
    public ManagedChannelFactory managedChannelFactory(FpmGrpcProperties props) {
        return new ManagedChannelFactory(props);
    }

    @Bean
    public GrpcClientInterceptor[] globalClientInterceptors(
            FpmObservabilityProperties obs) {
        return new GrpcClientInterceptor[]{
            new GrpcMetadataPropagationInterceptor(),
            new GrpcLoggingInterceptor(obs),
            new GrpcTracingInterceptor() // nếu có micrometer
        };
    }
}

// Usage:
@Component
public class TransactionGrpcClient {
    private final ManagedChannelFactory channelFactory;

    public void someMethod() {
        ManagedChannel channel = channelFactory.create("transaction-service");
        TransactionServiceV1Grpc.TransactionServiceV1BlockingStub stub =
            TransactionServiceV1Grpc.newBlockingStub(channel)
                .withInterceptors(globalInterceptors);
        // ...
    }
}
```

**Dependencies:**
```xml
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-proto</artifactId>
</dependency>
<dependency>
  <groupId>io.grpc</groupId>
  <artifactId>grpc-netty-shaded</artifactId>
</dependency>
<dependency>
  <groupId>io.grpc</groupId>
  <artifactId>grpc-protobuf</artifactId>
</dependency>
<dependency>
  <groupId>io.grpc</groupId>
  <artifactId>grpc-stub</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-observability-starter</artifactId>
</dependency>
```

---

### Phase 4: Messaging & Events

#### 4.1 fpm-messaging-starter

**Cải tổ:**
- `DomainEvent<T>` generic + versioning
- Event metadata (id, version, correlationId, traceId)
- Publisher/Subscriber registry
- Idempotency + exactly-once (outbox pattern optional)
- Correlation ID propagation

```java
// Event base với versioning
@Data
public abstract class DomainEvent<T> {
    private String eventId = UUID.randomUUID().toString();
    private String eventType;  // e.g., "WalletCreated"
    private int eventVersion = 1;  // tương thích v1, v2
    private Instant occurredAt = Instant.now();
    private Long aggregateId;
    private Long userId;
    private String correlationId;  // X-Correlation-Id header
    private String traceId;        // Distributed tracing
    private T payload;

    protected DomainEvent(String eventType, Long aggregateId, T payload) {
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.payload = payload;
    }
}

// Usage
public class WalletCreatedEvent extends DomainEvent<WalletCreatedPayload> {
    public WalletCreatedEvent(Long walletId, WalletCreatedPayload payload) {
        super("wallet.created", walletId, payload);
        this.eventVersion = 1;  // v1 compatible
    }
}

// Publisher
@Service
public class EventPublisher {
    @Transactional
    public <T> void publish(DomainEvent<T> event) {
        // 1. Save event to outbox (for durability)
        outboxRepository.save(event);

        // 2. Async: Rabbit publishes outbox poller
        rabbitTemplate.convertAndSend("domain-events", event);
    }
}

// Subscriber
@Component
public class WalletEventListeners {
    @RabbitListener(queues = "wallet.events")
    @Transactional(readOnly = true)
    public void onWalletCreated(WalletCreatedEvent event) {
        // Mark as processed (idempotency)
        if (isAlreadyProcessed(event.getEventId())) return;

        MDC.put("correlationId", event.getCorrelationId());
        MDC.put("traceId", event.getTraceId());

        try {
            // Process
        } finally {
            MDC.clear();
        }
    }
}
```

---

### Phase 5: Observability

#### 5.1 fpm-observability-starter (NEW)

**Nội dung:**
- Structured logging (JSON, MDC)
- Distributed tracing (OpenTelemetry)
- Metrics (Micrometer)
- Correlation ID + Trace ID propagation

```java
@Configuration
@EnableMetrics
public class FpmObservabilityAutoConfiguration {

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();  // X-Correlation-Id, X-Trace-Id
    }

    @Bean
    public JsonLogbackConfiguration jsonLogging() {
        return new JsonLogbackConfiguration();  // ECS-formatted JSON logs
    }

    @Bean
    @ConditionalOnProperty(name = "fpm.observability.tracing.enabled", havingValue = "true")
    public OpenTelemetryConfig openTelemetryConfig() { ... }

    @Bean
    public MeterRegistry meterRegistry() { ... }
}
```

---

### Phase 6: Testing

#### 6.1 fpm-test-support

```xml
<packaging>jar</packaging>
<scope>test</scope>  <!-- Hoặc phát hành test-jar classifier -->

<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>test-jar</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

---

### Phase 7: Config Server

#### 7.1 fpm-config-server

```
fpm-config-server/src/main/resources/application.yml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/fpm2025/fpm-config-repo
          search-paths:
            - '{application}'
            - 'shared'
          default-label: main
```

#### 7.2 fpm-config-repo (SEPARATE REPOSITORY)

```
fpm-config-repo/
├── application.yml (default)
├── shared.yml (common properties)
├── api-gateway/
│   ├── application-dev.yml
│   ├── application-staging.yml
│   └── application-prod.yml
├── transaction-service/
│   ├── application-dev.yml
│   └── ...
├── wallet-service/
├── user-auth-service/
└── reporting-service/
```

**Quan trọng:** Secrets dùng HashiCorp Vault hoặc AWS Secrets Manager, KHÔNG commit vào repo.

---

## 📋 Dependency Matrix

### Minimal (Chỉ REST API)
```xml
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-web-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-domain</artifactId>
</dependency>
```

### With Security + Redis
```xml
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-web-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-security-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-redis-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-observability-starter</artifactId>
</dependency>
```

### Full Stack (Microservice)
```xml
<!-- All starters + proto for gRPC + messaging -->
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-web-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-security-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-redis-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-proto</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-grpc-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-messaging-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-observability-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-test-support</artifactId>
  <scope>test</scope>
</dependency>
```

---

## ⏱️ Timeline Thực hiện

| Phase | Task                                    | Effort | Duration |
|-------|-----------------------------------------|--------|----------|
| 1     | Create fpm-bom                          | 4h     | 1 day    |
| 1     | Update parent-pom                       | 2h     |          |
| 2     | Extract fpm-web-starter                 | 6h     | 2 days   |
| 2     | Extract fpm-security-starter            | 4h     |          |
| 2     | Extract fpm-redis-starter               | 3h     |          |
| 3     | Create fpm-proto with versioned proto   | 8h     | 2-3 days |
| 3     | Create fpm-grpc-starter                 | 8h     |          |
| 4     | Refactor fpm-messaging-starter          | 6h     | 2 days   |
| 5     | Create fpm-observability-starter        | 10h    | 3 days   |
| 6     | Create fpm-test-support (test-jar)      | 4h     | 1 day    |
| 7     | Setup fpm-config-server + fpm-config-repo | 6h | 2 days   |
| 8     | Update documentation + examples         | 8h     | 2 days   |
| 9     | Testing + validation                    | 10h    | 3 days   |
| 10    | Maven release + publish                 | 4h     | 1 day    |

**Total: ~83 hours, ~17-18 days (2-3 sprints)**

---

## ✅ Validation Checklist

- [ ] Mọi starter có `AutoConfiguration.imports`
- [ ] Mọi `@Configuration` có `@ConditionalOnProperty` để enable/disable
- [ ] Proto files versioned: `fpm.v1`, `fpm.v2`
- [ ] Không commit generated sources (ignore trong `.gitignore`)
- [ ] BOM cấp version cho ALL dependencies
- [ ] Maven enforcer enforce Java 21, Maven 3.8+
- [ ] Test coverage cho autoconfiguration
- [ ] Docs: README + QUICK_START cho từng starter
- [ ] CI/CD: build, test, publish to Nexus/GitHub Packages
- [ ] Backward compatibility: proto fields reserved, messages versionable

---

## 🚀 Next Steps

1. **Chuẩn bị**: Review plan này, approve changes
2. **Branch**: Tạo branch `refactor/production-starters`
3. **Execute**: Tuần theo Phase
4. **Test**: Integration tests cho từng starter combo
5. **Release**: v1.1.0 với cấu trúc mới
6. **Migration**: Update services để dùng new starters (semver)

---

*Document này là roadmap chi tiết để biến FPM libs thành **production-ready, enterprise-grade Spring Boot starters collection**.*
