# FPM Libraries Refactoring - Implementation Checklist

Use this checklist to track progress through all 5 phases of refactoring.

---

## Phase 1: Foundation (Week 1)

### ✅ Create fpm-bom Module

- [ ] Create directory: `fpm-bom/`
- [ ] Create `pom.xml` with:
  - [ ] `<packaging>pom</packaging>`
  - [ ] `<dependencyManagement>` with all BOMs:
    - [ ] Spring Boot BOM
    - [ ] gRPC BOM
    - [ ] Testcontainers BOM
    - [ ] Micrometer BOM
    - [ ] OpenTelemetry BOM
  - [ ] Internal library dependencies (fpm-domain, fpm-proto, etc.)
  - [ ] `<pluginManagement>` with:
    - [ ] maven-compiler (Java 21)
    - [ ] protobuf-maven-plugin
    - [ ] maven-enforcer-plugin (version convergence)
- [ ] Add to parent pom: `<module>fpm-bom</module>`
- [ ] Test: `mvn clean verify -pl fpm-bom`

### ✅ Update Parent pom.xml

- [ ] Add fpm-bom to `<dependencyManagement>` with `<type>pom</type>` and `<scope>import</scope>`
- [ ] Update `<modules>` to include all new modules
- [ ] Remove direct version declarations (use BOM)
- [ ] Verify: `mvn dependency:tree | grep "version"` shows no version override warnings

### ✅ Maven Enforcer Configuration

- [ ] Add maven-enforcer-plugin to parent pluginManagement
- [ ] Create enforcer rule for:
  - [ ] Java version ≥ 21
  - [ ] Maven version ≥ 3.8.1
  - [ ] `<dependencyConvergence/>` (fail on conflicts)
- [ ] Test: `mvn enforcer:enforce` should pass

### ✅ AutoConfiguration Pattern Setup

- [ ] Create directory: `src/main/resources/META-INF/spring/`
- [ ] Create file: `org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- [ ] Add to `.gitignore`:
  ```
  target/
  generated-sources/
  *.class
  .DS_Store
  ```

---

## Phase 2: Core Starters (Week 2-3)

### ✅ fpm-web-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `FpmWebAutoConfiguration.java`
  - [ ] `FpmWebProperties.java`
  - [ ] Move from fpm-core:
    - [ ] `BaseResponse.java`
    - [ ] `PageResponse.java`
    - [ ] `ErrorResponse.java`
    - [ ] `GlobalExceptionHandler.java`
    - [ ] `OpenApiConfig.java`
- [ ] Create `pom.xml` with dependencies:
  - [ ] spring-boot-starter-web
  - [ ] spring-boot-starter-validation
  - [ ] springdoc-openapi-starter-webmvc-ui
- [ ] Create `AutoConfiguration.imports`
- [ ] Create `spring-configuration-metadata.json`
- [ ] Test: `mvn clean verify -pl fpm-web-starter`
- [ ] Test autoconfiguration: Create test-app, verify beans initialized

### ✅ fpm-security-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `FpmSecurityAutoConfiguration.java`
  - [ ] `FpmSecurityProperties.java`
  - [ ] Move from fpm-core:
    - [ ] `JwtService.java`
    - [ ] `JwtAuthenticationFilter.java`
    - [ ] `SecurityConfig.java`
- [ ] Add `@ConditionalOnProperty(name = "fpm.security.enabled")`
- [ ] Create `pom.xml`
- [ ] Create `AutoConfiguration.imports` and metadata
- [ ] Test: Verify beans only created when property enabled
- [ ] Test: Verify JWT token generation/validation

### ✅ fpm-redis-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `FpmRedisAutoConfiguration.java`
  - [ ] `FpmRedisProperties.java`
  - [ ] Move from fpm-core:
    - [ ] `RedisConfig.java`
- [ ] Add conditional: `@ConditionalOnClass(RedisTemplate.class)`
- [ ] Create `pom.xml`
- [ ] Create `AutoConfiguration.imports` and metadata
- [ ] Test: Verify connects to Redis (testcontainers)

### ✅ Deprecate fpm-core

- [ ] Add deprecation notice to `fpm-core/README.md`
- [ ] Update `fpm-core/pom.xml` to depend on new starters (for backward compat)
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
  ```
- [ ] Mark `fpm-core` as transitional (deprecation warning in logs)

### ✅ Validation

- [ ] Build all starters: `mvn clean verify -pl fpm-web-starter,fpm-security-starter,fpm-redis-starter`
- [ ] Test combinations:
  - [ ] fpm-web-starter alone
  - [ ] fpm-web-starter + fpm-security-starter
  - [ ] fpm-web-starter + fpm-security-starter + fpm-redis-starter
- [ ] Verify no duplicate beans
- [ ] Check JAR sizes (should be < 100KB each without transitives)

---

## Phase 3: Proto & gRPC (Week 3-4)

### ✅ Rename shared-utils → fpm-proto

- [ ] Rename directory: `shared-utils/` → `fpm-proto/`
- [ ] Update parent pom: `<module>fpm-proto</module>`
- [ ] Clean .proto structure:
  ```
  src/main/proto/
  ├── fpm/common/v1/
  │   ├── common.proto
  │   └── metadata.proto
  ├── fpm/transaction/v1/
  │   └── transaction.proto
  ├── fpm/wallet/v1/
  │   └── wallet.proto
  ├── fpm/category/v1/
  │   └── category.proto
  └── fpm/user_auth/v1/
      └── user_auth.proto
  ```

### ✅ Proto Versioning

- [ ] Update all `.proto` files:
  - [ ] Add `package fpm.xxx.v1;`
  - [ ] Add `option java_package = "com.fpm2025.grpc.v1.xxx";`
  - [ ] Add `option java_multiple_files = true;`
  - [ ] Name services: `service TransactionServiceV1`
- [ ] Define common.proto with shared types:
  - [ ] `Status` message
  - [ ] `Error` message
  - [ ] `PaginationRequest/Response`
  - [ ] Common enums (TransactionType, etc.)
- [ ] Add field reservation for future versions:
  ```protobuf
  message Transaction {
    string id = 1;
    double amount = 2;
    reserved 10 to 20;  // For v2
  }
  ```

### ✅ fpm-proto pom.xml

- [ ] Configure protobuf-maven-plugin:
  ```xml
  <plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <configuration>
      <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}</protocArtifact>
      <pluginId>grpc-java</pluginId>
      <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
    </configuration>
  </plugin>
  ```
- [ ] Add `.gitignore` entry: `/target/generated-sources/`
- [ ] Ensure generated code NOT committed

### ✅ fpm-grpc-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `GrpcClientAutoConfiguration.java`
  - [ ] `GrpcServerAutoConfiguration.java`
  - [ ] `FpmGrpcProperties.java`
  - [ ] `ManagedChannelFactory.java`
  - [ ] `GrpcClientStubFactory.java`
  - [ ] Interceptors:
    - [ ] `GrpcMetadataPropagationInterceptor.java` (CLIENT)
    - [ ] `GrpcLoggingInterceptor.java` (SERVER)
    - [ ] `GrpcErrorInterceptor.java` (SERVER)
    - [ ] `GrpcTracingInterceptor.java` (BOTH)
  - [ ] Error handling:
    - [ ] `GrpcStatusMapper.java`
    - [ ] `GrpcErrorHandler.java`
- [ ] Create `pom.xml` with dependencies:
  - [ ] fpm-proto
  - [ ] io.grpc:grpc-netty-shaded
  - [ ] io.grpc:grpc-protobuf
  - [ ] io.grpc:grpc-stub
- [ ] Create `AutoConfiguration.imports` and metadata
- [ ] Add properties support:
  ```yaml
  fpm:
    grpc:
      client:
        enabled: true
        services:
          transaction-service:
            host: localhost
            port: 9091
  ```

### ✅ Test Proto Backward Compatibility

- [ ] Create v1 and v2 test protos
- [ ] Verify v1 client can read v2 messages (with new optional fields)
- [ ] Verify v2 client can read v1 messages
- [ ] Test: `mvn clean verify -pl fpm-proto`
- [ ] Build grpc stubs: `mvn compile -pl fpm-proto`
- [ ] Verify stubs generated to `target/generated-sources` (NOT in src/)

### ✅ fpm-grpc-starter Tests

- [ ] Unit tests for auto-configuration
- [ ] Integration tests with mock gRPC server
- [ ] Test interceptor chain
- [ ] Test error mapping
- [ ] Test channel factory

---

## Phase 4: Messaging & Infrastructure (Week 4-5)

### ✅ fpm-messaging-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `FpmMessagingAutoConfiguration.java`
  - [ ] `FpmMessagingProperties.java`
  - [ ] Event base classes:
    - [ ] `DomainEvent<T>.java` (versioned)
    - [ ] `EventMetadata.java`
  - [ ] Publisher:
    - [ ] `EventPublisher.java`
    - [ ] Outbox pattern implementation
  - [ ] Listener registry:
    - [ ] `EventListenerRegistry.java`
    - [ ] `@EventListener` annotation support
- [ ] Create `pom.xml` with dependencies:
  - [ ] spring-boot-starter-amqp
  - [ ] Jackson with datetime support
- [ ] Add properties:
  ```yaml
  fpm:
    messaging:
      enabled: true
      rabbitmq:
        exchanges:
          domain-events: domain-events
        queues:
          wallet-events: wallet.events
  ```
- [ ] Test: Publish and consume events with versioning
- [ ] Test: Idempotency (consume same event twice)
- [ ] Test: Correlation ID propagation

### ✅ fpm-observability-starter

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `FpmObservabilityAutoConfiguration.java`
  - [ ] `FpmObservabilityProperties.java`
  - [ ] `CorrelationIdFilter.java` (extract/propagate X-Correlation-Id)
  - [ ] `TraceIdFilter.java` (X-Trace-Id)
  - [ ] Logging configuration (JSON/ECS format)
  - [ ] Micrometer setup
  - [ ] Optional OpenTelemetry config
- [ ] Create `pom.xml`:
  - [ ] io.micrometer:micrometer-core
  - [ ] Optional: OpenTelemetry dependencies
  - [ ] Jackson (JSON logging)
- [ ] Logback configuration file: `logback-spring.xml` for JSON output
- [ ] Test: Verify JSON logs
- [ ] Test: MDC propagation
- [ ] Test: Metrics collection

### ✅ fpm-test-support

- [ ] Create module structure
- [ ] Implement classes:
  - [ ] `PostgresTestContainer.java`
  - [ ] `RabbitMQTestContainer.java`
  - [ ] `TestDataFactory.java`
  - [ ] `AssertionUtil.java`
  - [ ] `MockDataBuilder.java`
- [ ] Create `pom.xml` with `<scope>test</scope>`
- [ ] Configure test-jar publication:
  ```xml
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <executions>
      <execution>
        <goals>
          <goal>test-jar</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
  ```
- [ ] Test: Testcontainers work in CI environment

### ✅ Config Server

- [ ] Create `fpm-config-server/` module
- [ ] Create `pom.xml` with spring-cloud-config-server
- [ ] Create `application.yml`:
  ```yaml
  spring:
    cloud:
      config:
        server:
          git:
            uri: https://github.com/fpm2025/fpm-config-repo
            search-paths: '{application},shared'
  ```
- [ ] Create separate `fpm-config-repo` repository:
  - [ ] `application.yml` (default)
  - [ ] `shared.yml` (common properties)
  - [ ] Per-service configs: `transaction-service.yml`, `wallet-service.yml`, etc.
  - [ ] Per-environment: `application-{dev,staging,prod}.yml`
- [ ] Test: Config server retrieves and serves configs
- [ ] Test: Clients connect and get properties

### ✅ Integration Testing

- [ ] Test all starter combinations in example app:
  - [ ] Web-only
  - [ ] Web + Security
  - [ ] Web + Security + Redis
  - [ ] Web + Security + gRPC + Messaging + Observability (full stack)
- [ ] Build example app for each combo
- [ ] Run integration tests
- [ ] Verify no bean conflicts
- [ ] Check memory/startup time for each combo

### ✅ Validation

- [ ] Build all new starters: `mvn clean verify`
- [ ] Fix any version conflicts caught by enforcer
- [ ] Check dependency sizes:
  - [ ] fpm-web-starter: < 100KB
  - [ ] fpm-security-starter: < 100KB
  - [ ] fpm-grpc-starter: < 200KB (includes stubs)
- [ ] JAR inspection: `jar tf fpm-web-starter-1.1.0.jar | head -20`

---

## Phase 5: Release & Migration (Week 5-6)

### ✅ Update Documentation

- [ ] Create/update README for each starter
- [ ] Create MIGRATION_GUIDE.md with examples
- [ ] Update QUICK_REFERENCE.md
- [ ] Create fpm-bom/README.md
- [ ] Update main README.md with new architecture
- [ ] Add code examples to USAGE_EXAMPLES.md

### ✅ CI/CD Pipeline

- [ ] Update `.github/workflows/build.yml`:
  - [ ] Build all modules
  - [ ] Run tests
  - [ ] Enforce version convergence
  - [ ] Check for generated sources in git
- [ ] Setup Maven release plugin:
  ```xml
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-release-plugin</artifactId>
    <configuration>
      <autoVersionSubmodules>true</autoVersionSubmodules>
      <tagNameFormat>v@{project.version}</tagNameFormat>
    </configuration>
  </plugin>
  ```
- [ ] Setup publishing to Nexus/GitHub Packages
- [ ] Test: `mvn clean deploy` (dry-run first)

### ✅ Migrate Example Services

Pick 2-3 services to migrate first:

- [ ] **Service 1: Simple REST API**
  - [ ] Replace `fpm-core` with `fpm-web-starter + fpm-security-starter`
  - [ ] Update `pom.xml`
  - [ ] Remove deprecated `fpm-core` code
  - [ ] Test: `mvn clean package`
  - [ ] Test: Spring boot run
  - [ ] Verify no warnings

- [ ] **Service 2: With gRPC Client**
  - [ ] Add `fpm-proto + fpm-grpc-starter`
  - [ ] Update gRPC client code to use `ManagedChannelFactory`
  - [ ] Test: Build and deploy (dry-run)

- [ ] **Service 3: Full Stack**
  - [ ] Add all starters
  - [ ] Test: Full integration

### ✅ Version Release

- [ ] Update CHANGELOG.md with all changes
- [ ] Create release branch: `git checkout -b release/v1.1.0`
- [ ] Update versions: `mvn versions:set -DnewVersion=1.1.0`
- [ ] Commit: `git commit -am "Release v1.1.0"`
- [ ] Tag: `git tag -a v1.1.0 -m "Release v1.1.0"`
- [ ] Push: `git push --tags`
- [ ] Publish to Maven repository
- [ ] Create GitHub Release with notes

### ✅ Team Training

- [ ] Schedule 1-hour training session
- [ ] Cover:
  - [ ] New starter structure
  - [ ] AutoConfiguration pattern
  - [ ] Properties-based configuration
  - [ ] Migration steps
  - [ ] Q&A
- [ ] Create team documentation
- [ ] Share examples in team channel

### ✅ Post-Release Validation

- [ ] Monitor v1.1.0 adoption
- [ ] Collect feedback from teams
- [ ] Create GitHub issues for any bugs/improvements
- [ ] Plan v1.2.0 enhancements based on feedback

---

## 🚨 Critical Success Factors

- [ ] **Backward Compatibility:** v1.0.0 services still work with v1.1.0 libs
- [ ] **No Generated Code Commits:** Proto stubs only in target/
- [ ] **Version Convergence:** Maven enforcer prevents conflicts
- [ ] **Test Coverage:** ≥80% for all new code
- [ ] **Documentation:** Every starter has clear README
- [ ] **Team Alignment:** Everyone understands new architecture

---

## 📋 Sign-Off

- [ ] Architecture Lead: ___________________ Date: ____
- [ ] Tech Lead: ___________________ Date: ____
- [ ] DevOps Lead: ___________________ Date: ____
- [ ] QA Lead: ___________________ Date: ____

---

## 📌 Notes & Blockers

Use this section to track issues discovered during implementation:

```
Issue #1: ___________________
Status: [ ] Open [ ] In Progress [ ] Resolved
Notes: ___________________

Issue #2: ___________________
Status: [ ] Open [ ] In Progress [ ] Resolved
Notes: ___________________
```

---

**Start Date:** ________________
**Completion Date:** ________________
**Total Effort:** ___ hours

---

*This checklist is the source of truth for implementation. Update regularly.*
