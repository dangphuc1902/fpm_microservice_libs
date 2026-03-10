# 🏗️ FPM Libraries - Complete Architecture Overview

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     GitHub Repository                            │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  Source Code (fpm-libs modules)                          │   │
│  │  - fpm-core, fpm-domain, fpm-grpc, etc.                  │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                       │
│                      Git Push (tag)                               │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
        ┌───────────────────────────────────────────┐
        │    GitHub Actions CI/CD Pipeline         │
        │                                           │
        │  1. build-and-test.yml                   │
        │     ├─ Build Maven                       │
        │     ├─ Run Tests                         │
        │     └─ Build Docker Image                │
        │                                           │
        │  2. release.yml (on version tag)          │
        │     ├─ Create GitHub Release             │
        │     ├─ Build Docker Image                │
        │     └─ Push to Registry                  │
        └───────────────────────────────────────────┘
                            │
                            ▼
        ┌───────────────────────────────────────────┐
        │  Docker Build Process                    │
        │                                           │
        │  Stage 1: Build                          │
        │  ├─ Java 21 JDK                          │
        │  ├─ Maven Build                          │
        │  └─ Compile & Package                    │
        │                                           │
        │  Stage 2: Runtime                        │
        │  ├─ Java 21 JRE (lightweight)            │
        │  ├─ Copy Artifacts                       │
        │  └─ Non-root User                        │
        └───────────────────────────────────────────┘
                            │
                            ▼
        ┌───────────────────────────────────────────┐
        │  Container Registry                      │
        │  (GitHub Container Registry)             │
        │  ghcr.io/username/fpm-libraries:v1.0.0   │
        └───────────────────────────────────────────┘
                            │
                            ▼
        ┌───────────────────────────────────────────┐
        │  Local/Production Deployment             │
        │  docker pull & docker run                │
        └───────────────────────────────────────────┘
```

---

## Local Development Environment (Docker Compose)

```
┌──────────────────────────────────────────────────────────┐
│                   Docker Network (fpm-network)            │
│                                                           │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │ PostgreSQL  │  │   RabbitMQ   │  │    Redis     │    │
│  │             │  │              │  │              │    │
│  │  Port 5432  │  │  Ports 5672  │  │  Port 6379   │    │
│  │  Databases: │  │  UI: 15672   │  │  Password:   │    │
│  │  - fpm_dev  │  │  User: guest │  │  redis_pwd   │    │
│  │  - wallet   │  │              │  │              │    │
│  │  - trans.   │  │              │  │              │    │
│  │  - category │  │              │  │              │    │
│  └─────────────┘  └──────────────┘  └──────────────┘    │
│       ▲                ▲                  ▲               │
│       │                │                  │               │
│  ┌────┴────────────────┴──────────────────┴─────┐        │
│  │                                               │        │
│  │        FPM Builder Container                 │        │
│  │                                               │        │
│  │  ├─ Java 21 JDK                              │        │
│  │  ├─ Maven 3.9.13                             │        │
│  │  ├─ Build fpm-libs modules                   │        │
│  │  └─ Generate artifacts                       │        │
│  │                                               │        │
│  │  Volumes:                                    │        │
│  │  - /app → local project                      │        │
│  │  - ~/.m2 → Maven cache                       │        │
│  │                                               │        │
│  └───────────────────────────────────────────────┘        │
│                                                           │
│  Optional Services:                                      │
│  ┌──────────────────┐  ┌─────────────────────┐          │
│  │   Zookeeper      │  │     Kafka           │          │
│  │   Port: 2181     │  │  Ports: 9092, 29092 │          │
│  └──────────────────┘  └─────────────────────┘          │
│                                                           │
│  ┌──────────────────────────────┐                        │
│  │   pgAdmin (Admin UI)          │                        │
│  │   http://localhost:5050       │                        │
│  │   Email: admin@example.com    │                        │
│  └──────────────────────────────┘                        │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
fpm-libs/
│
├── 📁 fpm-bom/
│   └── pom.xml (Bill of Materials - Version Management)
│       ├─ PostgreSQL 42.7.2 (CVE Fixed ✓)
│       └─ Protobuf 3.25.5 (CVE Fixed ✓)
│
├── 📁 fpm-common/
│   ├── src/main/java/...
│   └── pom.xml
│
├── 📁 fpm-core/
│   ├── src/main/java/... (21 Java files)
│   └── pom.xml
│
├── 📁 fpm-domain/
│   ├── src/main/java/... (6 Java files)
│   └── pom.xml
│
├── 📁 fpm-grpc/
│   ├── src/main/java/... (3 Java files)
│   └── pom.xml
│
├── 📁 fpm-messaging/
│   ├── src/main/java/... (7 Java files)
│   └── pom.xml
│
├── 📁 fpm-proto/
│   ├── src/main/proto/...
│   └── pom.xml
│
├── 📁 fpm-security/
│   ├── src/main/java/...
│   └── pom.xml
│
├── 📁 fpm-testing/
│   ├── src/main/java/... (5 Java files)
│   └── pom.xml
│
├── 📁 .github/
│   └── 📁 workflows/
│       ├── build-and-test.yml     (CI/CD on push/PR)
│       └── release.yml            (Release on tag)
│
├── 📁 docker/
│   ├── 📁 postgres/
│   │   └── init.sql               (Database initialization)
│   └── 📁 rabbitmq/
│       └── rabbitmq.conf          (Message broker config)
│
├── Dockerfile                      (Multi-stage build)
├── .dockerignore                   (Optimize context)
├── docker-compose.yml              (Complete dev environment)
├── docker-setup.ps1                (Windows automation)
├── docker-setup.sh                 (Linux/Mac automation)
│
├── .env.development                (40+ environment variables)
│
├── 📄 DOCKER_CICD_SETUP.md         (220+ lines, complete guide)
├── 📄 DOCKER_QUICK_START.md        (80+ lines, quick reference)
├── 📄 DOCKER_CI_CD_COMPLETE.md     (300+ lines, summary)
├── 📄 SETUP_CHECKLIST.md           (400+ lines, validation)
├── 📄 IMPLEMENTATION_COMPLETE.md   (This summary)
│
├── pom.xml (Parent)
└── [other files...]
```

---

## CI/CD Pipeline Workflow

### On Push/PR to main/develop

```
┌─────────────────┐
│  Push to main   │
└────────┬────────┘
         │
         ▼
┌──────────────────────────────────────────┐
│ build-and-test.yml Workflow Triggered    │
└────┬───────────────────────────────────┬─┘
     │                                   │
     ▼                                   ▼
┌──────────────────┐          ┌─────────────────────┐
│ Build Job        │          │ Docker Build Job    │
│                  │          │                     │
│ 1. Checkout code │────────▶ │ 1. Build image      │
│ 2. Setup Java 21 │          │ 2. Tag image        │
│ 3. Run Maven     │          │ 3. Push to registry │
│ 4. Run tests     │          │    (if on main)     │
│ 5. Generate      │          │                     │
│    test report   │          └─────────────────────┘
└──────────────────┘                   │
         │                             │
         └─────────────┬───────────────┘
                       │
                       ▼
        ┌──────────────────────────┐
        │ Code Quality Job         │
        │                          │
        │ 1. Check vulnerabilities │
        │ 2. Check dependency      │
        │    updates               │
        │ 3. Run linters (optional)│
        └──────────────────────────┘
                       │
                       ▼
        ┌──────────────────────────┐
        │ Workflow Complete ✓      │
        │ Build Success/Failed     │
        └──────────────────────────┘
```

### On Version Tag (v*)

```
┌──────────────────┐
│  git tag v1.0.0  │
│  git push tag    │
└────────┬─────────┘
         │
         ▼
┌──────────────────────────────────────────┐
│ release.yml Workflow Triggered           │
└────┬───────────────────────────────────┬─┘
     │                                   │
     ▼                                   ▼
┌──────────────────┐          ┌─────────────────────┐
│ Release Job      │          │ Docker Release Job  │
│                  │          │                     │
│ 1. Checkout code │          │ 1. Build Docker     │
│ 2. Setup Java 21 │          │ 2. Tag with version │
│ 3. Build project │          │ 3. Push to registry │
│ 4. Create GitHub │──────┐   │                     │
│    Release       │      │   │                     │
│ 5. Attach        │      │   │                     │
│    artifacts     │      │   │                     │
└──────────────────┘      │   └─────────────────────┘
                          │            │
                          │            ▼
                          │   ┌─────────────────────┐
                          │   │ Docker Registry      │
                          │   │ (GitHub Container)  │
                          │   │                     │
                          │   │ ghcr.io/.../        │
                          │   │ fpm-libraries:v1.0.0│
                          │   └─────────────────────┘
                          │
                          ▼
                  ┌──────────────────────┐
                  │ Release Complete ✓   │
                  │ Available on GitHub  │
                  └──────────────────────┘
```

---

## Build Artifact Output

```
After successful build:

Local Maven Repository:
~/.m2/repository/com/fpm2025/
├── fpm-bom-1.0.0-SNAPSHOT.jar
├── fpm-common-1.0.0-SNAPSHOT.jar
├── fpm-core-1.0.0-SNAPSHOT.jar
├── fpm-domain-1.0.0-SNAPSHOT.jar
├── fpm-grpc-1.0.0-SNAPSHOT.jar
├── fpm-messaging-1.0.0-SNAPSHOT.jar
├── fpm-proto-1.0.0-SNAPSHOT.jar
├── fpm-security-1.0.0-SNAPSHOT.jar
└── fpm-testing-1.0.0-SNAPSHOT.jar

Docker Registry (on release):
ghcr.io/username/fpm-libraries:latest
ghcr.io/username/fpm-libraries:v1.0.0
ghcr.io/username/fpm-libraries:main-<commit-sha>

GitHub Release:
Release: v1.0.0
├─ Release notes
├─ Artifacts attached
└─ Docker image references
```

---

## Technology Stack

```
Frontend/Build Tools:
├─ Java 21 LTS
├─ Maven 3.9.13
├─ Spring Boot 3.3.5
├─ Protocol Buffers 4.28.2
└─ gRPC 1.68.1

Infrastructure:
├─ Docker (Multi-stage builds)
├─ Docker Compose
├─ PostgreSQL 16
├─ RabbitMQ 3.13
├─ Redis 7
├─ Kafka 7.5 (optional)
└─ Zookeeper 7.5 (optional)

CI/CD:
├─ GitHub Actions
├─ GitHub Container Registry
└─ Maven Repository

Development:
├─ Eclipse Temurin JDK 21
├─ Alpine Linux (lightweight)
├─ PowerShell (Windows)
└─ Bash (Linux/Mac)
```

---

## Deployment Flow

```
┌─────────────────────────────────────────┐
│ Local Development                       │
│ docker-compose up -d                    │
│ Access: localhost:5432, 5672, 6379      │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│ Push to GitHub                          │
│ git push origin main                    │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│ CI/CD Pipeline (Automated)              │
│ Build → Test → Build Docker             │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│ Create Release                          │
│ git tag v1.0.0 && git push origin v1.0.0
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│ Release Workflow (Automated)            │
│ Release → Build → Push to Registry      │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│ Production Deployment                   │
│ docker pull ghcr.io/.../fpm:v1.0.0      │
│ docker run [docker run options]         │
└─────────────────────────────────────────┘
```

---

## Security Layers

```
┌──────────────────────────────────┐
│ Application Code                 │
│ (Non-root user in Docker)        │
└────────────┬─────────────────────┘
             │
┌────────────▼─────────────────────┐
│ Docker Image                     │
│ - Alpine Linux (minimal)         │
│ - No build tools in runtime      │
│ - Health checks                  │
│ - Non-root user (app:1000)       │
└────────────┬─────────────────────┘
             │
┌────────────▼─────────────────────┐
│ Container Orchestration          │
│ - Docker Compose networks        │
│ - Volume isolation               │
│ - Environment variable secrets   │
└────────────┬─────────────────────┘
             │
┌────────────▼─────────────────────┐
│ Registry Security                │
│ - OIDC authentication            │
│ - GitHub Actions secrets         │
│ - Container scanning             │
│ - Access control lists           │
└──────────────────────────────────┘
```

---

## Key Metrics

| Metric | Value |
|--------|-------|
| Docker Image Size | ~500-800 MB |
| Build Time | ~2-3 minutes |
| Startup Time | ~10-15 seconds |
| Container Count | 7 (3 optional) |
| Network Isolation | Docker bridge network |
| Volume Mounting | 7 named volumes |
| Environment Variables | 40+ configured |
| Documentation | 1,000+ lines |

---

**Architecture Documentation Complete ✅**

For detailed information, see:
- 📖 [DOCKER_CICD_SETUP.md](./DOCKER_CICD_SETUP.md)
- 🎯 [DOCKER_CI_CD_COMPLETE.md](./DOCKER_CI_CD_COMPLETE.md)
- ✅ [SETUP_CHECKLIST.md](./SETUP_CHECKLIST.md)

