# FPM Libraries - Recommendations Summary & Action Plan

## 🎯 Executive Summary

**Current State:** FPM libs v1.0.0 có cấu trúc tốt nhưng chưa tối ưu cho production enterprise use.

**Target State:** Production-ready Spring Boot Starters architecture theo best practices.

**Benefit:** 30-40% giảm dependency size per service, 100% backward compatible, easier to maintain.

---

## 📊 Key Recommendations

| # | Recommendation | Priority | Effort | Impact |
|---|---|---|---|---|
| 1 | Tách fpm-core thành focused starters | 🔴 High | 20h | High |
| 2 | Tạo fpm-bom cho centralized versioning | 🔴 High | 8h | High |
| 3 | Rename shared-utils → fpm-proto | 🔴 High | 4h | High |
| 4 | Proto versioning (v1, v2) | 🔴 High | 6h | High |
| 5 | fpm-grpc-starter pattern | 🔴 High | 16h | High |
| 6 | fpm-observability-starter | 🟡 Medium | 12h | Medium |
| 7 | Config repo separation | 🟡 Medium | 8h | Medium |
| 8 | Event versioning + outbox pattern | 🟡 Medium | 10h | Medium |
| 9 | CI/CD + Release pipeline | 🟡 Medium | 10h | Medium |
| 10 | Documentation update | 🟡 Medium | 8h | Medium |

**Total Effort:** ~82 hours (3 sprints)

---

## 🚀 Action Plan (Phased Approach)

### Phase 1: Foundation (Week 1)
- [ ] Create fpm-bom with all versions
- [ ] Update parent-pom to use BOM
- [ ] Add Maven enforcer for version drift
- [ ] Setup AutoConfiguration.imports pattern

**Deliverable:** v1.1.0-beta1 with centralized versioning

### Phase 2: Core Starters (Week 2-3)
- [ ] Extract fpm-web-starter (REST, exception, swagger)
- [ ] Extract fpm-security-starter (JWT, conditional)
- [ ] Extract fpm-redis-starter
- [ ] Extract fpm-messaging-starter (event versioning)
- [ ] Create fpm-observability-starter

**Deliverable:** v1.1.0-beta2 with granular starters

### Phase 3: Proto & gRPC (Week 3-4)
- [ ] Rename shared-utils → fpm-proto
- [ ] Reorganize .proto files với versioning (v1/)
- [ ] Create fpm-grpc-starter (auto-config, factories)
- [ ] Add interceptors (metadata propagation, tracing)
- [ ] Test proto backward compatibility

**Deliverable:** v1.1.0-beta3 with versioned contracts

### Phase 4: Infrastructure & Testing (Week 4-5)
- [ ] Create fpm-test-support (test-jar, testcontainers wrappers)
- [ ] Setup fpm-config-server
- [ ] Create fpm-config-repo (separate)
- [ ] Integration tests cho mỗi starter combo
- [ ] Validation: build, test, deploy

**Deliverable:** v1.1.0 stable release

### Phase 5: Migration & Documentation (Week 5-6)
- [ ] Update existing services to use new starters
- [ ] Document migration path (semver)
- [ ] Update all README, examples, quick-start
- [ ] Publish to Nexus/GitHub Packages
- [ ] Training for team

**Deliverable:** v1.1.0 production release + documentation

---

## 💡 Rationale for Each Recommendation

### 1️⃣ **Split fpm-core → Multiple Starters**

**Current Problem:**
```xml
<!-- Every service must import everything -->
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-core</artifactId>
  <!-- Pulls: JWT, Redis, Swagger, Security, etc. -->
  <!-- Even if only need BaseResponse! -->
</dependency>
```

**Solution:** Spring Boot Starters pattern
```xml
<!-- Service chỉ import cần thiết -->
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-web-starter</artifactId>  <!-- REST responses, 100KB -->
</dependency>
<dependency>
  <groupId>com.fpm2025</groupId>
  <artifactId>fpm-security-starter</artifactId>  <!-- JWT only if needed, 50KB -->
</dependency>
<!-- Total: 150KB vs 400KB before -->
```

**Benefits:**
- ✅ Smaller JAR sizes
- ✅ Fewer transitive dependencies
- ✅ Conditional auto-configuration
- ✅ Properties-based enable/disable

### 2️⃣ **fpm-bom for Centralized Versioning**

**Current Problem:** Version mismatch between gRPC, Protobuf, Spring Boot
```
grpc: 1.62.2 (lib)
protoc: 3.25.1 (dev's machine = mismatch!)
spring-boot: 3.3.5 (lib)
spring-boot: 3.2.0 (another dev)
```

**Solution:** Single source of truth
```xml
<!-- fpm-bom defines all versions -->
<properties>
  <grpc.version>1.62.2</grpc.version>
  <protobuf.version>3.25.3</protobuf.version>
  ...
</properties>

<!-- enforcer-maven-plugin prevents drift -->
<rule>
  <dependencyConvergence/>  <!-- fail if conflict -->
</rule>
```

**Benefits:**
- ✅ Consistency across all services
- ✅ One place to update versions
- ✅ Build failure on version conflicts
- ✅ Easier to track/rollback

### 3️⃣ **Proto Versioning (v1, v2)**

**Current Problem:** Can't evolve APIs without breaking clients
```protobuf
message Transaction {
  string id = 1;
  double amount = 2;
  // Need to add: string category_id?
  // → Breaking change!
}
```

**Solution:** Versioned packages
```protobuf
// fpm/transaction/v1/transaction.proto
package fpm.transaction.v1;
service TransactionServiceV1 { ... }

// fpm/transaction/v2/transaction.proto (future)
package fpm.transaction.v2;
service TransactionServiceV2 {
  // Support both v1 and v2 clients during transition
}

// Same service can support both versions
service TransactionService {
  rpc CreateTransactionV1(...) returns (...);
  rpc CreateTransactionV2(...) returns (...);  // new fields
}
```

**Benefits:**
- ✅ Backward compatibility
- ✅ Gradual migration
- ✅ Zero downtime upgrades
- ✅ Old and new clients coexist

### 4️⃣ **fpm-grpc-starter Pattern**

**Current Problem:** Proto stubs mixed with custom code; client setup repeated

**Solution:** Dedicated starter with factories
```java
// Auto-config creates channels per service
ManagedChannelFactory factory; // inject
ManagedChannel channel = factory.getChannel("transaction-service");

// Auto-included interceptors: logging, tracing, metadata
TransactionServiceV1Grpc.TransactionServiceV1BlockingStub stub =
    TransactionServiceV1Grpc.newBlockingStub(channel);
    // Interceptors already included!
```

**Benefits:**
- ✅ DRY: configure once in properties
- ✅ Standardized interceptors
- ✅ Built-in health checks
- ✅ Observable by default

### 5️⃣ **Observability Starter**

**Current Problem:** Logging, tracing, metrics scattered; MDC not propagated

**Solution:** Dedicated starter
```yaml
fpm:
  observability:
    logging:
      format: json  # ECS-compliant structured logs
      enabled: true
    tracing:
      enabled: true
      jaeger-endpoint: http://localhost:14268
    metrics:
      enabled: true
```

**Log example (JSON structured):**
```json
{
  "timestamp": "2025-11-16T10:30:45.123Z",
  "level": "INFO",
  "logger": "com.fpm2025.wallet.service",
  "message": "Wallet created",
  "trace_id": "abc123",
  "correlation_id": "req-456",
  "user_id": 789,
  "wallet_id": 999,
  "event": "wallet_created"
}
```

**Benefits:**
- ✅ Centralized log collection (ELK, Splunk)
- ✅ Easy to query and filter
- ✅ Distributed tracing (Jaeger)
- ✅ Prometheus metrics
- ✅ MDC propagated automatically

### 6️⃣ **Config Repo Separation**

**Current Problem:** Config files in source code → rebuild needed for config changes

**Solution:** Separate config repo + Spring Cloud Config Server
```
Source Repo: fpm-2025-backend (code only)
Config Repo: fpm-config-repo (configs + secrets)

Config Server pulls from config repo on startup
```

**Benefits:**
- ✅ Hot reload config (without rebuild)
- ✅ Separate secrets management (Vault)
- ✅ Different configs per environment
- ✅ Audit trail for config changes

### 7️⃣ **Event Versioning + Outbox Pattern**

**Current Problem:** Events lost if broker down; no idempotency; no versioning

**Solution:** Outbox pattern + event versioning
```java
@Transactional
public void publishEvent(DomainEvent<?> event) {
    // 1. Save event to database (guaranteed durability)
    outboxRepository.save(event);

    // 2. Async publish to broker
    // 3. Broker failure? Outbox poller retries
    // 4. Duplicate message? Consumer checks idempotency key
}

// Event versioning for evolution
public class WalletCreatedEvent extends DomainEvent<WalletCreatedPayload> {
    @Override
    public int getEventVersion() {
        return 1;  // Can upgrade to v2 later
    }
}
```

**Benefits:**
- ✅ Event durability (no loss)
- ✅ Exactly-once delivery semantics
- ✅ API versioning (add fields without breaking)
- ✅ Dead letter queue for failed handlers

---

## 📈 Expected Outcomes

### Performance
- 30-40% smaller JAR sizes per service
- 15-20% faster startup (less bean initialization)
- Reduced memory footprint

### Maintainability
- Single version source (BOM)
- Standardized patterns (starters)
- Easier debugging (structured logging)
- Clearer dependencies (each starter = one concern)

### Scalability
- Event versioning = API evolution
- Proto versioning = gRPC evolution
- Config repo = hot reload
- Observability = easier troubleshooting

### Developer Experience
- Clear documentation (one readme per starter)
- Copy-paste properties to enable features
- Minimal boilerplate
- IDE autocomplete for properties

---

## 🎓 Learning Resources

### For Implementation Team
1. **Spring Boot Starters:** https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter-poms
2. **gRPC versioning:** https://cloud.google.com/architecture/versioning-apis-defined-grpc-proto
3. **Event Sourcing/Outbox:** https://microservices.io/patterns/data/transactional-outbox.html
4. **OpenTelemetry:** https://opentelemetry.io/docs/

### Videos to Watch
- Spring Cloud Config: 10 mins
- gRPC Best Practices: 15 mins
- Protobuf versioning: 12 mins
- Event-driven architectures: 20 mins

---

## ✅ Go/No-Go Criteria

### Before Phase 1
- [ ] Team alignment on architecture
- [ ] Budget approval (82 hours)
- [ ] Dev environment setup (Maven 3.8+, Java 21)

### After Phase 2
- [ ] All starters have AutoConfiguration.imports
- [ ] Properties validation (spring-configuration-metadata.json)
- [ ] Test coverage ≥ 80%
- [ ] CI/CD pipeline working

### After Phase 3
- [ ] Proto backward compatibility tested
- [ ] Generated stubs NOT committed (gitignore updated)
- [ ] gRPC interceptors working
- [ ] Error mapping complete

### After Phase 4
- [ ] Integration tests passing (all starter combos)
- [ ] Config server routing correctly
- [ ] Testcontainers working in CI
- [ ] Performance baseline established

### After Phase 5
- [ ] 2-3 services migrated successfully
- [ ] Zero downtime upgrade validated
- [ ] Team trained on new architecture
- [ ] Documentation complete
- [ ] Release notes published

---

## 📞 Decision Points & Risks

### Decision 1: When to Start?
**Options:**
- A) Immediately (highest impact, but disruptive)
- B) After current sprint (balanced approach)
- C) Next quarter (low priority)

**Recommendation:** Option B (next sprint)

### Decision 2: Breaking Change or Backward Compatible?
**Approach:** **100% backward compatible**
- v1.0.0 continues to work
- v1.1.0 introduces new starters
- Services migrate gradually
- No forced upgrades

### Risk 1: Version Conflicts During Migration
**Mitigation:**
- Strict dependency convergence rules (Maven enforcer)
- Test all starter combinations
- Gradual rollout (1 service → team → all)

### Risk 2: Proto Versioning Complexity
**Mitigation:**
- Start with v1 only
- Plan v2 strategy BEFORE implementing v1
- Document with examples

### Risk 3: Config Repository Access
**Mitigation:**
- Same Git provider (GitHub)
- Same auth mechanism
- Network access policy review

---

## 🎯 Success Metrics

### Technical
- ✅ JAR size reduction: target 30-40%
- ✅ Dependency convergence: 100% (no conflicts)
- ✅ Test coverage: ≥80% for all starters
- ✅ Build time: ≤ 10min for all modules

### Business
- ✅ Time to build new service: < 1 day (vs 2-3 days)
- ✅ Onboarding time: < 2 hours (vs 4-6 hours)
- ✅ Bug fix velocity: +30% faster
- ✅ Production incidents: -40% (better observability)

### Adoption
- ✅ 100% of existing services upgraded
- ✅ All new services use starters
- ✅ Team confidence: ≥ 90% (survey)

---

## 📅 Timeline & Milestones

```
Week 1 (Foundation)
├─ Nov 18-22
├─ fpm-bom created
├─ Parent pom updated
└─ Milestone: v1.1.0-beta1

Week 2-3 (Starters)
├─ Nov 25 - Dec 6
├─ 5 starters extracted
├─ AutoConfigurations working
└─ Milestone: v1.1.0-beta2

Week 3-4 (Proto & gRPC)
├─ Dec 1-13
├─ fpm-proto versioned
├─ fpm-grpc-starter complete
└─ Milestone: v1.1.0-beta3

Week 4-5 (Infrastructure)
├─ Dec 8-20
├─ Config server working
├─ Test support complete
├─ Integration tests passing
└─ Milestone: v1.1.0-rc1

Week 5-6 (Release)
├─ Dec 15-27
├─ Services migrated
├─ Documentation complete
├─ Team trained
└─ Milestone: v1.1.0 GA

Production Deployment
├─ Early Jan 2026
├─ Gradual rollout
└─ Milestone: All services migrated
```

---

## 🔗 Related Documents

- **REFACTORING_PLAN.md** - Detailed implementation steps
- **STARTER_TEMPLATES.md** - Code templates for each starter
- **README.md** - Current architecture (for reference)
- **ARCHITECTURE_DIAGRAM.md** - Visual architecture

---

## 📝 Sign-off

This recommendations document outlines a comprehensive modernization plan for FPM libraries, targeting production-grade enterprise architecture.

**Next Action:**
1. Share this document with architecture review board
2. Schedule 30-min kick-off meeting
3. Get approval to proceed with Phase 1
4. Create Jira epic and sprint tickets

---

**Document Version:** 1.0
**Last Updated:** 2025-11-16
**Status:** Ready for Review & Approval

*This document is living; update as decisions evolve.*
