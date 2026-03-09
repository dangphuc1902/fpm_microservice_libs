# 🐳 Docker & CI/CD Setup Guide for FPM Libraries

## Table of Contents
1. [Docker Local Setup](#docker-local-setup)
2. [GitHub Actions CI/CD](#github-actions-cicd)
3. [Manual Commands](#manual-commands)
4. [Troubleshooting](#troubleshooting)

---

## 🐳 Docker Local Setup

### Prerequisites
- Docker Desktop installed (v24.0+)
- Docker Compose (optional, for multi-container setup)
- Git installed

### Build Docker Image Locally

```bash
# Navigate to project root
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# Build the image
docker build -t fpm-libraries:latest .

# View built image
docker images | grep fpm-libraries
```

### Run Container

```bash
# Run interactive container
docker run -it --rm fpm-libraries:latest /bin/sh

# Run with volume mount (for development)
docker run -it --rm \
  -v D:\WorkSpace\App_Dev\FPM_Project\libs:/app \
  fpm-libraries:latest /bin/sh

# Run with specific Java options
docker run -it --rm \
  -e JAVA_OPTS="-Xmx1024m -Xms512m" \
  fpm-libraries:latest /bin/sh

# List container artifacts
docker run --rm fpm-libraries:latest find /app/fpm-libs -name "*.jar" -type f
```

### Verify Build Artifacts

```bash
# Copy artifacts from container to host
docker create --name temp-container fpm-libraries:latest
docker cp temp-container:/app/fpm-libs ./fpm-libs-output
docker rm temp-container

# Check current directory for output
ls -R fpm-libs-output/
```

### Docker Compose (Optional - for Local Development)

Create `docker-compose.local.yml`:

```yaml
version: '3.8'

services:
  fpm-build:
    build:
      context: .
      dockerfile: Dockerfile
    image: fpm-libraries:dev
    container_name: fpm-build-dev
    volumes:
      - .:/app
      - ~/.m2:/home/app/.m2
    environment:
      JAVA_OPTS: "-Xmx1024m -Xms512m"
    command: mvn clean install

  postgres:
    image: postgres:15-alpine
    container_name: fpm-postgres
    environment:
      POSTGRES_DB: fpm_dev
      POSTGRES_USER: fpm_user
      POSTGRES_PASSWORD: fpm_pass
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: fpm-rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest

volumes:
  postgres_data:
```

Run with Docker Compose:

```bash
docker-compose -f docker-compose.local.yml up --build
```

---

## 🚀 GitHub Actions CI/CD

### Workflow Overview

The CI/CD pipeline consists of 2 workflows:

1. **build-and-test.yml** - Runs on push/PR to main/develop
   - Build Maven project
   - Run tests
   - Build Docker image
   - Code quality checks

2. **release.yml** - Runs on version tags (v*)
   - Create GitHub Release
   - Build and push Docker image to registry

### Setup Steps

#### Step 1: Push Code to GitHub

```bash
# Initialize repo if not already
git init
git add .
git commit -m "Initial commit: FPM Libraries with Docker and CI/CD"

# Add remote (replace with your repo URL)
git remote add origin https://github.com/YOUR_USERNAME/fpm-libraries.git

# Push to GitHub
git branch -M main
git push -u origin main
```

#### Step 2: Enable GitHub Actions

1. Go to **Settings** → **Actions** → **General**
2. Enable **Actions** and set default permissions
3. Allow all actions and reusable workflows

#### Step 3: Configure Secrets (Optional)

If using private registry (e.g., Azure Container Registry):

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Add these secrets:
   ```
   DOCKER_USERNAME = your_docker_username
   DOCKER_PASSWORD = your_docker_token
   REGISTRY_URL = docker.io (or your registry)
   ```

#### Step 4: Create Release Tags

```bash
# Create and push a release tag
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0

# This automatically triggers the release workflow
```

### Viewing Workflow Runs

1. Go to **Actions** tab in your GitHub repo
2. See all workflow runs with their status
3. Click on a run to see detailed logs

### Monitoring

- **Branch Protection**: Require CI checks to pass before merging
  - Settings → Branches → Add rule for `main` branch
  - Require status checks to pass: `build`, `docker-build`, `code-quality`

---

## 📝 Manual Commands

### Local Development

```bash
# Navigate to project
cd fpm-libs

# Build without tests (fast)
mvn clean install -DskipTests

# Build with tests
mvn clean install

# Run specific tests
mvn test

# Build specific module
mvn clean install -pl fpm-core

# Check for dependency updates
mvn versions:display-dependency-updates

# Update dependencies
mvn versions:update-properties
```

### Docker Build Options

```bash
# Build with build arguments
docker build \
  --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') \
  --build-arg VCS_REF=$(git rev-parse --short HEAD) \
  -t fpm-libraries:1.0.0 .

# Build for specific platform
docker buildx build --platform linux/amd64,linux/arm64 -t fpm-libraries:latest .

# Build and load to Docker
docker buildx build --load -t fpm-libraries:latest .
```

### Tag and Push to Registry

```bash
# Tag image
docker tag fpm-libraries:latest ghcr.io/username/fpm-libraries:latest
docker tag fpm-libraries:latest ghcr.io/username/fpm-libraries:1.0.0

# Log in to registry
docker login ghcr.io

# Push images
docker push ghcr.io/username/fpm-libraries:latest
docker push ghcr.io/username/fpm-libraries:1.0.0
```

---

## 🔧 Troubleshooting

### Docker Build Issues

**Issue:** Build fails with "Maven not found"
```bash
# Verify Docker is running
docker --version

# Rebuild with verbose output
docker build --progress=plain -t fpm-libraries:latest .
```

**Issue:** Out of memory during build
```bash
# Increase Docker memory limit
# Docker Desktop → Settings → Resources → Memory (set to 4GB+)

# Or build without tests
docker build --build-arg SKIP_TESTS=true -t fpm-libraries:latest .
```

**Issue:** Cache issues
```bash
# Clear Docker cache and rebuild
docker build --no-cache -t fpm-libraries:latest .
```

### GitHub Actions Issues

**Issue:** Workflow fails with "permission denied"
```bash
# Ensure GITHUB_TOKEN has correct permissions
# Check Settings → Actions → General → Workflow permissions
# Select "Read and write permissions"
```

**Issue:** Docker image push fails
```bash
# Check registry credentials in Secrets
# Verify GITHUB_TOKEN is not expired
# Ensure Actions permissions include packages:write
```

**Issue:** Tests timeout in CI
```bash
# Increase timeout in workflow file
# timeout-minutes: 30 (in job definition)

# Or skip tests for faster feedback
# Run with -DskipTests flag
```

### Maven Issues

**Issue:** Dependency resolution fails
```bash
# Clear Maven cache
rm -r ~/.m2/repository

# Rebuild
mvn clean install
```

**Issue:** Java version mismatch
```bash
# Check Java version
java -version

# Should be Java 21+
# Update JAVA_HOME if needed
```

---

## 📦 Released Artifacts

After successful CI/CD pipeline, artifacts are available at:

### Local Maven Repository
```
~/.m2/repository/com/fpm2025/
├── fpm-common/1.0.0-SNAPSHOT/
├── fpm-core/1.0.0-SNAPSHOT/
├── fpm-domain/1.0.0-SNAPSHOT/
└── fpm-grpc/1.0.0-SNAPSHOT/
```

### Docker Registry (GitHub Container Registry)
```
ghcr.io/username/fpm-libraries:latest
ghcr.io/username/fpm-libraries:v1.0.0
ghcr.io/username/fpm-libraries:main-<sha>
```

### GitHub Releases
View at: https://github.com/username/fpm-libraries/releases

---

## 🔐 Security Best Practices

1. **Use OIDC for authentication** (configured in workflow)
2. **Scan images for vulnerabilities**
   ```bash
   docker scan fpm-libraries:latest
   ```

3. **Use semantic versioning** for releases
4. **Review and approve** PRs before merge
5. **Keep dependencies updated**
   ```bash
   mvn dependency:purge-local-repository
   ```

6. **Use secrets** for sensitive data (never commit)

---

## 📚 Additional Resources

- [Docker Documentation](https://docs.docker.com)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Documentation](https://maven.apache.org)
- [Spring Boot Guidelines](https://spring.io/guides)
- [Container Security Best Practices](https://cheatsheetseries.owasp.org/cheatsheets/Docker_Security_Cheat_Sheet.html)

