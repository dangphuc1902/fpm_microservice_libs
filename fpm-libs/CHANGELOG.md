# Changelog

All notable changes to the FPM Libraries will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-11-09

### Added

#### fpm-core
- BaseResponse, PageResponse, ErrorResponse DTOs
- Generic BaseMapper interface and AbstractMapper
- MapperFactory and MapperRegistry for mapper management
- GlobalExceptionHandler with comprehensive error handling
- Custom exceptions (ResourceNotFoundException, DuplicateResourceException, etc.)
- JwtService for token generation and validation
- JwtAuthenticationFilter for JWT-based authentication
- SecurityConfig with Spring Security setup
- RedisConfig with JSON serialization
- SwaggerConfig with Bearer token support
- Utility classes (DateTimeUtil, ValidationUtil, PageableUtil)
- FpmCoreProperties for centralized configuration

#### fpm-domain
- CategoryType enum (EXPENSE, INCOME)
- WalletType enum (CASH, BANK, CREDIT_CARD, etc.)
- TransactionType enum (INCOME, EXPENSE, TRANSFER)
- CurrencyCode enum (VND, USD, EUR, GBP, JPY)
- DomainConstants with validation patterns and limits
- ErrorMessages with standardized error messages
- Cache key constants and TTL values
- Event exchange and routing key constants

#### fpm-grpc
- Proto definitions (common, wallet, transaction, category)
- GrpcErrorInterceptor for error handling
- GrpcLoggingInterceptor for request/response logging
- GrpcClientConfig with health checks and keep-alive

#### fpm-messaging
- DomainEvent base class for event-driven architecture
- EventMetadata for event tracking
- EventPublisher interface
- RabbitEventPublisher implementation
- EventListener interface with priority support
- EventListenerRegistry for listener management
- RabbitMQEventConfig with JSON message converter

#### fpm-testing
- PostgresTestContainer for integration tests
- RabbitMQTestContainer for messaging tests
- TestDataFactory for generating test data
- AssertionUtil with custom assertions
- MockDataBuilder for builder pattern

### Documentation
- Comprehensive README with usage examples
- USAGE_EXAMPLES with detailed implementation guides
- Build script for easy library installation
- Architecture principles and best practices

### Technical Details
- Spring Boot 3.3.5
- Java 21
- gRPC 1.62.2
- Protobuf 3.25.3
- Testcontainers 1.19.7
- RabbitMQ AMQP support
- Redis support
- JWT authentication

### Features
- Code reusability across microservices
- Standardized response formats
- Centralized exception handling
- Event-driven architecture support
- gRPC communication patterns
- Comprehensive testing utilities
- Production-ready configurations

---

## [Unreleased]

### Planned for 1.1.0
- Distributed tracing integration (Zipkin/Jaeger)
- Metrics and monitoring (Prometheus/Grafana)
- Circuit breaker patterns (Resilience4j)
- API rate limiting
- Request/Response logging interceptors
- Audit logging utilities
- Multi-tenancy support

### Planned for 1.2.0
- OAuth2 integration
- Social authentication providers
- Advanced caching strategies
- Search and filtering utilities
- File upload/download utilities
- Email/SMS notification templates
- Scheduled task utilities

---

## Version History

| Version | Release Date | Status      |
|---------|-------------|-------------|
| 1.0.0   | 2025-11-09  | Released    |
| 1.1.0   | TBD         | In Planning |
| 1.2.0   | TBD         | In Planning |
