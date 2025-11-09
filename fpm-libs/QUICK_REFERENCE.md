# FPM Libraries - Quick Reference Guide

## 🚀 Installation

```bash
cd fpm-libs
./build.sh
```

Or manually:
```bash
mvn clean install
```

---

## 📦 Dependencies

```xml
<!-- Add to your service's pom.xml -->
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 🔧 Common Imports

### Response Wrappers
```java
import com.fpm2025.core.dto.response.BaseResponse;
import com.fpm2025.core.dto.response.PageResponse;
import com.fpm2025.core.dto.response.ErrorResponse;
```

### Exceptions
```java
import com.fpm2025.core.exception.ResourceNotFoundException;
import com.fpm2025.core.exception.DuplicateResourceException;
import com.fpm2025.core.exception.ValidationException;
import com.fpm2025.core.exception.UnauthorizedException;
import com.fpm2025.core.exception.ForbiddenException;
```

### Mappers
```java
import com.fpm2025.core.dto.mapper.BaseMapper;
import com.fpm2025.core.dto.mapper.AbstractMapper;
import com.fpm2025.core.dto.mapper.MapperFactory;
```

### Security
```java
import com.fpm2025.core.security.JwtService;
import com.fpm2025.core.security.JwtAuthenticationFilter;
```

### Domain
```java
import com.fpm2025.domain.enums.*;
import com.fpm2025.domain.constants.DomainConstants;
import com.fpm2025.domain.constants.ErrorMessages;
```

### Events
```java
import com.fpm2025.messaging.event.model.DomainEvent;
import com.fpm2025.messaging.event.publisher.RabbitEventPublisher;
import com.fpm2025.messaging.event.listener.EventListener;
```

### Testing
```java
import com.fpm2025.testing.testcontainers.PostgresTestContainer;
import com.fpm2025.testing.util.TestDataFactory;
import com.fpm2025.testing.util.AssertionUtil;
```

---

## 📝 Code Snippets

### REST Controller
```java
@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ResourceDTO>> get(@PathVariable Long id) {
        ResourceDTO resource = service.getById(id);
        return ResponseEntity.ok(BaseResponse.success(resource));
    }
}
```

### Service with Mapper
```java
@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository repository;
    private final MapperFactory mapperFactory;

    public ResourceDTO getById(Long id) {
        Entity entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource", id));

        BaseMapper<Entity, ResourceDTO> mapper = mapperFactory.getMapper("resource");
        return mapper.toDTO(entity);
    }
}
```

### Custom Mapper
```java
@Component
public class ResourceMapper extends AbstractMapper<Entity, DTO> {

    @Override
    public DTO toDTO(Entity entity) {
        return DTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    @Override
    public Entity toEntity(DTO dto) {
        Entity entity = new Entity();
        entity.setName(dto.getName());
        return entity;
    }
}
```

### JWT Authentication
```java
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public String login(String email, String password) {
        // Authenticate...
        return jwtService.generateToken(userId, email);
    }
}
```

### Publish Event
```java
@Service
@RequiredArgsConstructor
public class ResourceService {
    private final RabbitEventPublisher eventPublisher;

    public void create(CreateRequest request) {
        // Create resource...

        ResourceCreatedEvent event = new ResourceCreatedEvent(id, userId);
        eventPublisher.publish(event, "exchange", "routing.key");
    }
}
```

### Listen to Event
```java
@Component
public class ResourceListener {

    @RabbitListener(queues = "queue.name")
    public void handle(ResourceCreatedEvent event) {
        // Process event...
    }
}
```

### Integration Test
```java
@SpringBootTest
class ServiceTest extends PostgresTestContainer {

    @Test
    void shouldWork() {
        String name = TestDataFactory.randomString(20);
        BigDecimal amount = TestDataFactory.randomAmount();

        // Test logic...

        AssertionUtil.assertValidId(result.getId());
        AssertionUtil.assertBigDecimalEquals(amount, result.getAmount());
    }
}
```

---

## ⚙️ Configuration Templates

### application.yml
```yaml
fpm:
  core:
    jwt:
      secret: ${JWT_SECRET}
      expiration: 86400000
  service:
    name: My Service
    version: 1.0.0

spring:
  rabbitmq:
    host: localhost
    port: 5672
  data:
    redis:
      host: localhost
      port: 6379
```

---

## 🔍 Error Handling

All exceptions are automatically handled by `GlobalExceptionHandler`:

```java
throw new ResourceNotFoundException("Resource", id);
// Returns: 404 with { "status": 404, "error": "NOT_FOUND", "message": "..." }

throw new DuplicateResourceException("Resource", "name", value);
// Returns: 409 with conflict message

throw new ValidationException("Invalid data");
// Returns: 400 with validation error

throw new UnauthorizedException();
// Returns: 401 with unauthorized message

throw new ForbiddenException();
// Returns: 403 with forbidden message
```

---

## 📊 Constants

### Validation Patterns
```java
DomainConstants.Wallet.NAME_PATTERN
DomainConstants.Wallet.MIN_BALANCE
DomainConstants.Transaction.MIN_AMOUNT
DomainConstants.Category.MAX_DEPTH
```

### Cache Keys
```java
String key = String.format(DomainConstants.Cache.KEY_WALLET, walletId);
Long ttl = DomainConstants.Cache.TTL_MEDIUM;
```

### Event Constants
```java
String exchange = DomainConstants.Event.EXCHANGE_WALLET;
String routingKey = DomainConstants.Event.ROUTING_KEY_WALLET_CREATED;
```

### Error Messages
```java
throw new ResourceNotFoundException(ErrorMessages.Wallet.NOT_FOUND_WITH_ID, id);
throw new ValidationException(ErrorMessages.Transaction.INVALID_AMOUNT);
```

---

## 🧪 Testing Utilities

### Test Data Generation
```java
String name = TestDataFactory.randomString(20);
String email = TestDataFactory.randomEmail();
Long id = TestDataFactory.randomLong();
BigDecimal amount = TestDataFactory.randomAmount();
LocalDateTime date = TestDataFactory.randomDateTime();
```

### Custom Assertions
```java
AssertionUtil.assertValidId(id);
AssertionUtil.assertBigDecimalEquals(expected, actual);
AssertionUtil.assertDateTimeClose(actual, 5);
AssertionUtil.assertPositive(amount);
AssertionUtil.assertNotBlank(name);
```

---

## 📞 Support

Questions? Check:
1. README.md - Overview and features
2. USAGE_EXAMPLES.md - Detailed implementation guides
3. CHANGELOG.md - Version history
4. This file - Quick reference

---

## 🔗 Module Dependencies

```
Your Service
    ├── fpm-core (required)
    ├── fpm-domain (optional, but recommended)
    ├── fpm-grpc (if using gRPC)
    ├── fpm-messaging (if using events)
    └── fpm-testing (test scope only)
```

Minimal setup:
```xml
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

Full setup:
```xml
<!-- All libraries -->
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-core</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-domain</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-grpc</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-messaging</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-testing</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

---

Happy coding! 🚀
