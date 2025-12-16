# FPM Microservice Libs

Shared base classes, patterns, and configurations for FPM-2025 microservices.

## Features

- ✅ Base entity classes (BaseEntity, AuditableEntity, SoftDeleteEntity)
- ✅ Base repository interfaces with JPA Specifications
- ✅ Base service classes with common CRUD operations
- ✅ JPA Auditing configuration
- ✅ Jackson configuration for JSON
- ✅ Correlation ID filter for distributed tracing
- ✅ Logging aspects (method entry/exit, exceptions)
- ✅ Performance monitoring aspect
- ✅ Pageable utilities

## Installation

Add to your microservice `pom.xml`:

```xml
<dependency>
    <groupId>com.fpm2025</groupId>
    <artifactId>fpm-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>