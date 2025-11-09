# FPM Microservice Library Architecture

A comprehensive, reusable library collection for building Spring Boot microservices with best practices baked in.

## 📦 Modules Overview

### 1. **fpm-core** - Core Utilities & Infrastructure
Common utilities, security, exception handling, and configuration that every service needs.

**Features:**
- ✅ Standardized response wrappers (BaseResponse, PageResponse, ErrorResponse)
- ✅ Generic mapper pattern with factory & registry
- ✅ Global exception handling
- ✅ JWT authentication & authorization
- ✅ Redis configuration
- ✅ Swagger/OpenAPI configuration
- ✅ Utility classes (DateTime, Validation, Pageable)
- ✅ Configurable properties

### 2. **fpm-domain** - Domain Models & Constants
Business domain models, enums, and constants shared across services.

**Features:**
- ✅ Domain enums (CategoryType, WalletType, TransactionType, CurrencyCode)
- ✅ Domain constants (Wallet, Transaction, Category, Cache, Event)
- ✅ Standardized error messages
- ✅ Validation patterns

### 3. **fpm-grpc** - gRPC Communication
gRPC client configurations, interceptors, and proto definitions.

**Features:**
- ✅ Proto definitions (wallet, transaction, category, common)
- ✅ gRPC interceptors (error handling, logging)
- ✅ Client configuration with health checks
- ✅ Auto-generated stubs

### 4. **fpm-messaging** - Event-Driven Architecture
RabbitMQ configuration and event handling patterns.

**Features:**
- ✅ DomainEvent base class
- ✅ EventPublisher interface & implementation
- ✅ EventListener registry pattern
- ✅ RabbitMQ configuration with JSON serialization
- ✅ Async event publishing

### 5. **fpm-testing** - Testing Utilities
Comprehensive testing utilities and Testcontainers setup.

**Features:**
- ✅ PostgreSQL Testcontainer
- ✅ RabbitMQ Testcontainer
- ✅ Test data factory
- ✅ Mock data builder pattern
- ✅ Custom assertions

---

## 🚀 Quick Start

### 1. Build & Install Libraries

```bash
cd fpm-libs
mvn clean install
```

This will install all libraries to your local Maven repository.

### 2. Add Dependencies to Your Service

In your service's `pom.xml`:

```xml
<dependencies>
    <!-- Core utilities, security, exception handling -->
    <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-core</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!-- Domain models, enums, constants -->
    <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-domain</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!-- gRPC clients & interceptors -->
    <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-grpc</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!-- Event handling & RabbitMQ -->
    <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-messaging</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!-- Testing utilities (test scope) -->
    <dependency>
        <groupId>com.fpm2025</groupId>
        <artifactId>fpm-testing</artifactId>
        <version>1.0.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 📚 Usage Examples

### Using BaseResponse

```java
@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<WalletResponse>> getWallet(@PathVariable Long id) {
        WalletResponse wallet = walletService.getById(id);
        return ResponseEntity.ok(BaseResponse.success(wallet, "Wallet retrieved successfully"));
    }
}
```

### Using Mapper Pattern

```java
@Component
public class WalletMapper extends AbstractMapper<WalletEntity, WalletResponse> {

    @Override
    public WalletResponse toDTO(WalletEntity entity) {
        return WalletResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .balance(entity.getBalance())
                .build();
    }

    @Override
    public WalletEntity toEntity(WalletResponse dto) {
        WalletEntity entity = new WalletEntity();
        entity.setName(dto.getName());
        entity.setBalance(dto.getBalance());
        return entity;
    }
}

// Register in your configuration
@Configuration
public class MapperConfig {

    @Bean
    public CommandLineRunner registerMappers(MapperFactory factory, WalletMapper walletMapper) {
        return args -> {
            factory.registerMapper("wallet", walletMapper);
            factory.registerMapper(WalletEntity.class, WalletResponse.class, walletMapper);
        };
    }
}

// Use in service
@Service
@RequiredArgsConstructor
public class WalletService {
    private final MapperFactory mapperFactory;

    public WalletResponse getWallet(Long id) {
        WalletEntity entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Wallet", id));

        BaseMapper<WalletEntity, WalletResponse> mapper =
            mapperFactory.getMapper("wallet");

        return mapper.toDTO(entity);
    }
}
```

### Using JWT Service

```java
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        User user = authenticate(request);

        // Generate tokens
        String accessToken = jwtService.generateToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
```

### Publishing Domain Events

```java
// Define your event
public class WalletCreatedEvent extends DomainEvent {
    private String walletName;
    private BigDecimal initialBalance;

    public WalletCreatedEvent(Long walletId, Long userId, String walletName, BigDecimal balance) {
        super(walletId, userId, "wallet-service");
        this.walletName = walletName;
        this.initialBalance = balance;
    }

    @Override
    public String getEventName() {
        return "wallet.created";
    }
}

// Publish in service
@Service
@RequiredArgsConstructor
public class WalletService {
    private final RabbitEventPublisher eventPublisher;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request, Long userId) {
        // Create wallet
        WalletEntity wallet = new WalletEntity();
        wallet = repository.save(wallet);

        // Publish event
        WalletCreatedEvent event = new WalletCreatedEvent(
            wallet.getId(), userId, wallet.getName(), wallet.getBalance()
        );
        eventPublisher.publish(event, "wallet.exchange", "wallet.created");

        return mapper.toDTO(wallet);
    }
}
```

### Listening to Events

```java
@Component
@Slf4j
public class WalletEventListener {

    @RabbitListener(queues = "transaction.wallet.created")
    public void handleWalletCreated(WalletCreatedEvent event) {
        log.info("Handling wallet created event: {}", event.getEventId());
        // Handle the event
    }
}
```

### Using Domain Constants

```java
@Service
public class WalletValidator {

    public void validateBalance(BigDecimal balance) {
        if (balance.compareTo(DomainConstants.Wallet.MIN_BALANCE) < 0) {
            throw new ValidationException("Balance cannot be negative");
        }
        if (balance.compareTo(DomainConstants.Wallet.MAX_BALANCE) > 0) {
            throw new ValidationException("Balance exceeds maximum allowed");
        }
    }
}
```

### Using Test Utilities

```java
@SpringBootTest
class WalletServiceTest extends PostgresTestContainer {

    @Autowired
    private WalletService walletService;

    @Test
    void shouldCreateWallet() {
        // Use test data factory
        String walletName = TestDataFactory.randomString(20);
        BigDecimal balance = TestDataFactory.randomAmount();

        CreateWalletRequest request = CreateWalletRequest.builder()
                .name(walletName)
                .initialBalance(balance)
                .build();

        WalletResponse response = walletService.createWallet(request, 1L);

        // Use custom assertions
        AssertionUtil.assertValidId(response.getId());
        AssertionUtil.assertBigDecimalEquals(balance, response.getBalance());
        AssertionUtil.assertDateTimeClose(response.getCreatedAt(), 5);
    }
}
```

---

## ⚙️ Configuration

### application.yml

```yaml
# FPM Core Configuration
fpm:
  core:
    jwt:
      secret: ${JWT_SECRET:your-256-bit-secret-key}
      expiration: 86400000          # 24 hours
      refresh-expiration: 604800000  # 7 days
    page:
      defaultSize: 20
      maxSize: 100

  service:
    name: wallet-service
    version: 1.0.0
    description: Wallet Management Service

# gRPC Configuration
grpc:
  client:
    wallet:
      host: localhost
      port: 9091
    transaction:
      host: localhost
      port: 9092
    category:
      host: localhost
      port: 9093

# RabbitMQ Configuration
spring:
  rabbitmq:
    host: ${RABBITMQ_HOST:localhost}
    port: ${RABBITMQ_PORT:5672}
    username: ${RABBITMQ_USERNAME:guest}
    password: ${RABBITMQ_PASSWORD:guest}

fpm:
  rabbitmq:
    exchange:
      wallet: wallet.exchange
      transaction: transaction.exchange
      category: category.exchange
```

---

## 🏗️ Architecture Principles

### 1. **Separation of Concerns**
- Each module has a single, well-defined responsibility
- Project-specific code stays in services
- Shared infrastructure lives in libraries

### 2. **Dependency Inversion**
- Services depend on library abstractions
- Libraries don't depend on services
- Easy to swap implementations

### 3. **Convention Over Configuration**
- Sensible defaults provided
- Override only what you need
- Consistent patterns across services

### 4. **Reusability**
- Write once, use everywhere
- Standardized responses and error handling
- Common utilities and helpers

### 5. **Testability**
- Built-in testing utilities
- Testcontainers for integration tests
- Mock data builders

---

## 🔄 Versioning Strategy

Libraries use semantic versioning (MAJOR.MINOR.PATCH):

- **MAJOR**: Breaking changes
- **MINOR**: New features, backward compatible
- **PATCH**: Bug fixes

Current version: **1.0.0**

---

## 📦 Publishing to Artifact Repository

### Local Repository (for development)
```bash
mvn clean install
```

### Remote Repository (Nexus/Artifactory)

Configure in `pom.xml`:
```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <url>https://nexus.yourcompany.com/repository/maven-releases/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-snapshots</id>
        <url>https://nexus.yourcompany.com/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

Deploy:
```bash
mvn clean deploy
```

---

## 🎯 Benefits

✅ **Code Reusability** - Share common logic across all services
✅ **Maintainability** - Single source of truth for infrastructure code
✅ **Consistency** - Standardized patterns and responses
✅ **Scalability** - Easy to extend for new services
✅ **Productivity** - Focus on business logic, not boilerplate
✅ **Testing** - Built-in testing utilities and patterns
✅ **Future-Proof** - Template for all future projects

---

## 📝 License

Apache 2.0

---

## 👥 Contributing

1. Create feature branch
2. Make changes
3. Update version if needed
4. Submit pull request
5. Tag release after merge

---

## 🆘 Support

For questions or issues, contact: support@fpm2025.com
