# FPM Libraries - Usage Examples

Complete examples of how to use FPM libraries in your microservices.

## 📋 Table of Contents
1. [Service Setup](#service-setup)
2. [REST API with BaseResponse](#rest-api-with-baseresponse)
3. [Mapper Pattern](#mapper-pattern)
4. [Exception Handling](#exception-handling)
5. [JWT Authentication](#jwt-authentication)
6. [Event Publishing & Listening](#event-publishing--listening)
7. [gRPC Communication](#grpc-communication)
8. [Caching with Redis](#caching-with-redis)
9. [Testing](#testing)

---

## 1. Service Setup

### Step 1: Add Dependencies

**pom.xml**
```xml
<dependencies>
    <!-- FPM Libraries -->
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
</dependencies>
```

### Step 2: Application Configuration

**application.yml**
```yaml
server:
  port: 8080

spring:
  application:
    name: wallet-service
  datasource:
    url: jdbc:postgresql://localhost:5432/walletdb
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  data:
    redis:
      host: localhost
      port: 6379

fpm:
  core:
    jwt:
      secret: ${JWT_SECRET}
      expiration: 86400000
  service:
    name: Wallet Service
    version: 1.0.0
    description: Wallet Management Microservice
  rabbitmq:
    exchange:
      wallet: wallet.exchange
```

---

## 2. REST API with BaseResponse

### Controller Example

```java
package com.fpm2025.wallet.controller;

import com.fpm2025.core.dto.response.BaseResponse;
import com.fpm2025.core.dto.response.PageResponse;
import com.fpm2025.wallet.dto.request.CreateWalletRequest;
import com.fpm2025.wallet.dto.response.WalletResponse;
import com.fpm2025.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Tag(name = "Wallet", description = "Wallet management APIs")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    @Operation(summary = "Create new wallet")
    public ResponseEntity<BaseResponse<WalletResponse>> createWallet(
            @Valid @RequestBody CreateWalletRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        WalletResponse wallet = walletService.createWallet(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.created(wallet, "Wallet created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get wallet by ID")
    public ResponseEntity<BaseResponse<WalletResponse>> getWallet(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        WalletResponse wallet = walletService.getWalletById(id, userId);

        return ResponseEntity.ok(
                BaseResponse.success(wallet, "Wallet retrieved successfully")
        );
    }

    @GetMapping
    @Operation(summary = "List all wallets")
    public ResponseEntity<BaseResponse<PageResponse<WalletResponse>>> listWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        PageResponse<WalletResponse> wallets = walletService.listWallets(userId, page, size);

        return ResponseEntity.ok(BaseResponse.success(wallets));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update wallet")
    public ResponseEntity<BaseResponse<WalletResponse>> updateWallet(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWalletRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        WalletResponse wallet = walletService.updateWallet(id, request, userId);

        return ResponseEntity.ok(
                BaseResponse.success(wallet, "Wallet updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete wallet")
    public ResponseEntity<BaseResponse<Void>> deleteWallet(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        walletService.deleteWallet(id, userId);

        return ResponseEntity.ok(
                BaseResponse.success(null, "Wallet deleted successfully")
        );
    }

    private Long extractUserId(UserDetails userDetails) {
        // Extract from custom UserDetails implementation
        return ((CustomUserDetails) userDetails).getUserId();
    }
}
```

---

## 3. Mapper Pattern

### Create Mapper

```java
package com.fpm2025.wallet.mapper;

import com.fpm2025.core.dto.mapper.AbstractMapper;
import com.fpm2025.wallet.entity.WalletEntity;
import com.fpm2025.wallet.dto.response.WalletResponse;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper extends AbstractMapper<WalletEntity, WalletResponse> {

    @Override
    public WalletResponse toDTO(WalletEntity entity) {
        if (entity == null) return null;

        return WalletResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .walletType(entity.getWalletType())
                .balance(entity.getBalance())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public WalletEntity toEntity(WalletResponse dto) {
        if (dto == null) return null;

        WalletEntity entity = new WalletEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setWalletType(dto.getWalletType());
        entity.setBalance(dto.getBalance());
        entity.setCurrency(dto.getCurrency());
        return entity;
    }
}
```

### Register Mapper

```java
package com.fpm2025.wallet.config;

import com.fpm2025.core.dto.mapper.MapperFactory;
import com.fpm2025.wallet.entity.WalletEntity;
import com.fpm2025.wallet.dto.response.WalletResponse;
import com.fpm2025.wallet.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final MapperFactory mapperFactory;
    private final WalletMapper walletMapper;

    @Bean
    public CommandLineRunner registerMappers() {
        return args -> {
            // Register with string key
            mapperFactory.registerMapper("wallet", walletMapper);

            // Register with class types
            mapperFactory.registerMapper(
                WalletEntity.class,
                WalletResponse.class,
                walletMapper
            );
        };
    }
}
```

### Use Mapper in Service

```java
package com.fpm2025.wallet.service;

import com.fpm2025.core.dto.mapper.BaseMapper;
import com.fpm2025.core.dto.mapper.MapperFactory;
import com.fpm2025.core.exception.ResourceNotFoundException;
import com.fpm2025.wallet.entity.WalletEntity;
import com.fpm2025.wallet.dto.response.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final MapperFactory mapperFactory;

    public WalletResponse getWalletById(Long id, Long userId) {
        WalletEntity entity = walletRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet", id));

        // Get mapper and convert
        BaseMapper<WalletEntity, WalletResponse> mapper =
                mapperFactory.getMapper("wallet");

        return mapper.toDTO(entity);
    }

    public List<WalletResponse> listUserWallets(Long userId) {
        List<WalletEntity> entities = walletRepository.findAllByUserId(userId);

        BaseMapper<WalletEntity, WalletResponse> mapper =
                mapperFactory.getMapper("wallet");

        return mapper.toDTOList(entities);
    }
}
```

---

## 4. Exception Handling

The GlobalExceptionHandler from fpm-core automatically handles exceptions:

```java
package com.fpm2025.wallet.service;

import com.fpm2025.core.exception.ResourceNotFoundException;
import com.fpm2025.core.exception.DuplicateResourceException;
import com.fpm2025.core.exception.ValidationException;
import com.fpm2025.domain.constants.ErrorMessages;

@Service
public class WalletService {

    public WalletResponse getWallet(Long id, Long userId) {
        return walletRepository.findByIdAndUserId(id, userId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                    ErrorMessages.Wallet.NOT_FOUND_WITH_ID, id
                ));
    }

    public WalletResponse createWallet(CreateWalletRequest request, Long userId) {
        // Check duplicate
        if (walletRepository.existsByNameAndUserId(request.getName(), userId)) {
            throw new DuplicateResourceException(
                ErrorMessages.Wallet.DUPLICATE_NAME
            );
        }

        // Validate
        if (request.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                ErrorMessages.Wallet.INVALID_BALANCE
            );
        }

        // Create wallet...
    }
}
```

---

## 5. JWT Authentication

### Create UserDetailsService

```java
package com.fpm2025.wallet.security;

import com.fpm2025.core.exception.ResourceNotFoundException;
import com.fpm2025.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
```

### Auth Service

```java
package com.fpm2025.wallet.service;

import com.fpm2025.core.security.JwtService;
import com.fpm2025.wallet.dto.request.LoginRequest;
import com.fpm2025.wallet.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest request) {
        // Authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate tokens
        String accessToken = jwtService.generateToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(86400L)
                .build();
    }
}
```

---

## 6. Event Publishing & Listening

### Define Event

```java
package com.fpm2025.wallet.event;

import com.fpm2025.messaging.event.model.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCreatedEvent extends DomainEvent {

    private String walletName;
    private String walletType;
    private BigDecimal initialBalance;
    private String currency;

    public WalletCreatedEvent(Long walletId, Long userId, String walletName,
                              String walletType, BigDecimal initialBalance, String currency) {
        super(walletId, userId, "wallet-service");
        this.walletName = walletName;
        this.walletType = walletType;
        this.initialBalance = initialBalance;
        this.currency = currency;
    }

    @Override
    public String getEventName() {
        return "wallet.created";
    }
}
```

### Publish Event

```java
package com.fpm2025.wallet.service;

import com.fpm2025.domain.constants.DomainConstants;
import com.fpm2025.messaging.event.publisher.RabbitEventPublisher;
import com.fpm2025.wallet.event.WalletCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final RabbitEventPublisher eventPublisher;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request, Long userId) {
        // Create wallet
        WalletEntity wallet = new WalletEntity();
        wallet.setName(request.getName());
        wallet.setWalletType(request.getWalletType());
        wallet.setBalance(request.getInitialBalance());
        wallet.setUserId(userId);

        wallet = walletRepository.save(wallet);

        // Publish event
        WalletCreatedEvent event = new WalletCreatedEvent(
                wallet.getId(),
                userId,
                wallet.getName(),
                wallet.getWalletType().getValue(),
                wallet.getBalance(),
                wallet.getCurrency().getCode()
        );

        eventPublisher.publish(
                event,
                DomainConstants.Event.EXCHANGE_WALLET,
                DomainConstants.Event.ROUTING_KEY_WALLET_CREATED
        );

        return mapper.toDTO(wallet);
    }
}
```

### Listen to Event

```java
package com.fpm2025.transaction.listener;

import com.fpm2025.wallet.event.WalletCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletEventListener {

    private final TransactionService transactionService;

    @RabbitListener(queues = "transaction.wallet.created")
    public void handleWalletCreated(WalletCreatedEvent event) {
        log.info("Received wallet created event: walletId={}, userId={}",
                event.getAggregateId(), event.getUserId());

        try {
            // Handle the event
            transactionService.initializeWalletTracking(
                    event.getAggregateId(),
                    event.getUserId()
            );

            log.info("Successfully processed wallet created event");
        } catch (Exception e) {
            log.error("Error processing wallet created event", e);
            throw e;
        }
    }
}
```

### Configure Queue

```java
package com.fpm2025.transaction.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue walletCreatedQueue() {
        return new Queue("transaction.wallet.created", true);
    }

    @Bean
    public Binding walletCreatedBinding(Queue walletCreatedQueue, TopicExchange walletExchange) {
        return BindingBuilder
                .bind(walletCreatedQueue)
                .to(walletExchange)
                .with("wallet.created");
    }
}
```

---

## 7. Testing

### Integration Test with Testcontainers

```java
package com.fpm2025.wallet.service;

import com.fpm2025.testing.testcontainers.PostgresTestContainer;
import com.fpm2025.testing.util.TestDataFactory;
import com.fpm2025.testing.util.AssertionUtil;
import com.fpm2025.wallet.dto.request.CreateWalletRequest;
import com.fpm2025.wallet.dto.response.WalletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletServiceIntegrationTest extends PostgresTestContainer {

    @Autowired
    private WalletService walletService;

    @Test
    void shouldCreateWallet() {
        // Given
        String walletName = TestDataFactory.randomString(20);
        BigDecimal balance = TestDataFactory.randomAmount();

        CreateWalletRequest request = CreateWalletRequest.builder()
                .name(walletName)
                .walletType(WalletType.CASH)
                .initialBalance(balance)
                .currency(CurrencyCode.VND)
                .build();

        // When
        WalletResponse response = walletService.createWallet(request, 1L);

        // Then
        AssertionUtil.assertValidId(response.getId());
        assertThat(response.getName()).isEqualTo(walletName);
        AssertionUtil.assertBigDecimalEquals(balance, response.getBalance());
        AssertionUtil.assertDateTimeClose(response.getCreatedAt(), 5);
    }
}
```

---

For more examples, see the README.md file.
