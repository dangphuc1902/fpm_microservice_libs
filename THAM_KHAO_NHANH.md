# ⚡ Quick Reference - Docker & CI/CD Commands

## 🐳 Docker - Essential Commands

### Build Images
```bash
# Build with tag
docker build -t fpm-libraries:latest .

# Build with different Dockerfile
docker build -f Dockerfile.prod -t fpm-libraries:prod .

# Build with build arguments
docker build --build-arg MAVEN_OPTS="-Xmx2048m" -t fpm:dev .

# Build without cache (clean rebuild)
docker build --no-cache -t fpm:dev .

# Build with progress output
docker build --progress=plain -t fpm:dev .
```

### Run Containers
```bash
# Basic interactive container
docker run -it --rm fpm-libraries:latest

# With shell
docker run -it --rm fpm-libraries:latest /bin/sh

# With volume mount
docker run -it --rm -v D:\WorkSpace\App_Dev\FPM_Project\libs:/app fpm:latest

# With environment variables
docker run -it --rm -e JAVA_OPTS="-Xmx1024m" fpm:latest

# Background container
docker run -d --name fpm-build fpm:latest

# Multiple ports and volumes
docker run -d \
  -p 8080:8080 \
  -p 8081:8081 \
  -v /host/path:/container/path \
  -e VAR=value \
  fpm:latest
```

### Inspect & Debug
```bash
# List images
docker images
docker images | grep fpm

# Get image details
docker image inspect fpm:latest

# List containers
docker ps -a
docker ps -a grep fpm

# View container logs
docker logs container-id
docker logs -f container-id          # Follow logs
docker logs --tail=100 container-id # Last 100 lines

# Enter running container
docker exec -it container-id /bin/sh

# View resource usage
docker stats
docker stats container-name
```

### Copy & Extract
```bash
# Create container (without running)
docker create --name temp fpm:latest

# Copy from container
docker cp temp:/app/fpm-libs/target ./artifacts

# Remove container
docker rm temp

# Complete: Copy + remove
docker create --name temp fpm:latest && \
docker cp temp:/app/fpm-libs ./output && \
docker rm temp
```

### Cleanup
```bash
# Remove container
docker rm container-id

# Remove image
docker rmi image-id
docker rmi fpm:latest

# Stop all containers
docker stop $(docker ps -aq)

# Remove all stopped containers
docker container prune

# Remove unused images
docker image prune

# Remove everything (dangerous!)
docker system prune -a --volumes
```

---

## 🐋 Docker Compose - Essential Commands

### Lifecycle
```bash
# Start services
docker-compose up             # Foreground
docker-compose up -d          # Background

# Stop services
docker-compose stop           # Graceful stop
docker-compose kill           # Force stop

# Remove services & containers
docker-compose down           # Stop + remove
docker-compose down -v        # + remove volumes

# Restart services
docker-compose restart
docker-compose restart mysql  # Specific service
```

### Status & Logs
```bash
# List services
docker-compose ps

# View logs
docker-compose logs           # All services
docker-compose logs -f        # Follow
docker-compose logs mysql     # Specific service
docker-compose logs --tail=50 mysql

# Service details
docker-compose config         # Show merged config
```

### Rebuild & Update
```bash
# Rebuild images
docker-compose build

# Rebuild specific service
docker-compose build mysql

# Up with rebuild
docker-compose up -d --build

# Force recreate containers
docker-compose up -d --force-recreate
```

### Access Services
```bash
# Execute command in service
docker-compose exec mysql mysql -u root -proot

# Interactive shell
docker-compose exec mysql /bin/sh

# View service logs
docker-compose logs fpm-builder
```

---

## 🔨 Maven - Essential Commands

### Build Operations
```bash
# Full build with tests
mvn clean install

# Build skip tests
mvn clean install -DskipTests

# Quiet mode (less output)
mvn clean install -q

# Offline mode (no internet)
mvn clean install -o

# Increase memory
mvn -Xmx1024m clean install

# Specific phase
mvn clean
mvn compile
mvn package
mvn test
mvn verify
mvn deploy
```

### Dependencies
```bash
# Show dependency tree
mvn dependency:tree

# Check for updates
mvn dependency:check-updates

# Resolve dependencies
mvn dependency:resolve

# Purge cache
mvn dependency:purge-local-repository
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=MyTestClass

# Run specific test method
mvn test -Dtest=MyTestClass#testMethod

# Skip tests
mvn install -DskipTests

# Skip test compilation
mvn install -DskipTests=true
```

### Get Info
```bash
# Maven version
mvn --version

# Active profiles
mvn help:active-profiles

# Show effective POM
mvn help:effective-pom

# System properties
mvn help:system
```

---

## 📦 Git - Essential Commands

### Branches
```bash
# Create branch
git checkout -b feature/new-auth

# Switch branch
git checkout main
git switch develop              # Newer syntax

# List branches
git branch -a                   # All (local + remote)
git branch -v                   # With last commit

# Delete branch
git branch -d feature/old
git push origin --delete feature/old
```

### Commits
```bash
# Stage changes
git add .
git add src/                    # Specific directory

# Commit
git commit -m "Message"
git commit -am "Message"        # Stage + commit

# View history
git log                         # Full log
git log --oneline -10          # Last 10
git log --oneline --graph --all # With branches
```

### Tags & Release
```bash
# Create annotated tag (recommended)
git tag -a v1.0.0 -m "Release message"

# List tags
git tag -l

# Show tag details
git show v1.0.0

# Push tags
git push origin v1.0.0          # Single tag
git push origin --tags          # All tags

# Delete tag
git tag -d v1.0.0               # Local
git push origin --delete v1.0.0 # Remote
```

### Remote
```bash
# View remotes
git remote -v

# Add remote
git remote add origin https://github.com/user/repo.git

# Push
git push origin main
git push origin feature/new
git push -u origin feature/new  # Set upstream

# Pull
git pull                        # Current branch
git pull origin main            # Specific branch

# Fetch
git fetch                       # Get updates
git fetch --all                 # All remotes
```

---

## 🚀 GitHub Actions - Essential Concepts

### Workflow Triggers
```yaml
on:
  push:
    branches: [main, develop]        # Push trigger
  pull_request:
    branches: [main]                 # PR trigger
  schedule:
    - cron: '0 0 * * 0'             # Weekly
  workflow_dispatch:                 # Manual trigger
```

### Job Syntax
```yaml
jobs:
  build:
    runs-on: ubuntu-latest           # Runner OS
    timeout-minutes: 30              # Timeout
    needs: [job1, job2]              # Dependencies
    
    steps:
      - uses: actions/checkout@v4    # Use action
      - name: 'Run command'
        run: echo "Hello"             # Shell command
      - name: 'Conditional'
        if: success()                 # Only if previous success
        run: echo "Success"
```

### Common Actions
```yaml
# Checkout code
- uses: actions/checkout@v4

# Setup Java
- uses: actions/setup-java@v4
  with:
    java-version: '21'
    distribution: 'temurin'
    cache: maven

# Setup Docker
- uses: docker/setup-buildx-action@v3

# Build and push Docker
- uses: docker/build-push-action@v5

# Create release
- uses: actions/create-release@v1
```

### Variables & Secrets
```yaml
# Use variable
${{ env.VARIABLE_NAME }}

# Use secret
${{ secrets.SECRET_NAME }}

# GitHub context
${{ github.event_name }}
${{ github.ref }}
${{ github.repository }}
${{ github.sha }}
${{ github.actor }}

# Outputs
${{ steps.step-id.outputs.output-name }}
```

---

## 🔥 PowerShell Scripts (Windows)

### Quick Build
```powershell
# Navigate and build
cd d:\WorkSpace\App_Dev\FPM_Project\libs
mvn clean install

# Verify
echo "Build status: $?"
```

### Docker One-Liner
```powershell
# Build and run
docker build -t fpm:latest . && docker run -it --rm fpm:latest /bin/sh

# Build, create temp, copy artifacts, cleanup
docker build -t fpm:latest . && `
docker create --name temp fpm:latest && `
docker cp temp:/app/fpm-libs ./artifacts && `
docker rm temp && `
echo "✓ Artifacts copied"
```

### Cleanup All
```powershell
# Stop all containers
docker stop $(docker ps -aq)

# Remove all containers and images
docker rm $(docker ps -aq)
docker rmi $(docker images -q)

# Clean Docker system
docker system prune -a --volumes --force
```

### Check Ports
```powershell
# Find what's using port 3306
netstat -ano | findstr ":3306"

# Kill process (get PID from above)
taskkill /PID <PID> /F

# List all listening ports
netstat -ano | findstr "LISTEN"
```

---

## 🎯 Common Workflows

### Daily Development
```bash
# 1. Create feature branch
git checkout -b feature/TASK-123

# 2. Make changes and commit
git add .
git commit -m "Implement feature TASK-123"

# 3. Test locally
cd fpm-libs && mvn clean install

# 4. Push to GitHub
git push origin feature/TASK-123

# 5. Create PR (via GitHub UI)

# 6. Monitor CI/CD (GitHub Actions tab)
# - Build workflow runs
# - Tests execute
# - Results show on PR

# 7. Merge after approval
# (via GitHub UI)
```

### Creating Release
```bash
# 1. Ensure on main branch
git checkout main
git pull origin main

# 2. Create tag
git tag -a v1.0.0 -m "Release version 1.0.0"

# 3. Push tag
git push origin v1.0.0

# 4. Monitor workflow
# - release.yml triggers
# - Docker image gets tagged v1.0.0
# - GitHub Release page created
```

### Hotfix
```bash
# 1. Create hotfix branch from main
git checkout -b hotfix/critical-bug main

# 2. Fix and test
git commit -am "Fix critical bug"
cd fpm-libs && mvn test

# 3. Merge to main
git checkout main
git merge hotfix/critical-bug
git push origin main

# 4. Create release patch
git tag -a v1.0.1 -m "Hotfix v1.0.1"
git push origin v1.0.1

# 5. Also merge to develop
git checkout develop
git merge main
git push origin develop
```

---

## ❌ Troubleshooting One-Liners

### Docker Issues
```bash
# Free disk space
docker system prune -a --volumes --force

# Reset Docker (dangerous!)
docker system prune -a --volumes --force
docker volume prune --force

# View image layers
docker history fpm:latest

# Inspect image config
docker image inspect fpm:latest | grep -A 10 '"Config"'
```

### Maven Issues
```bash
# Clean everything
cd fpm-libs
mvn clean

# Force download all deps
mvn clean install -U

# Check for dependency conflicts
mvn dependency:tree

# Offline mode (use cache)
mvn install -o
```

### Git Issues
```bash
# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# See what will be pushed
git log origin/main..main

# Sync fork with upstream
git fetch upstream
git merge upstream/main
```

### Container Port Issues
```powershell
# Find processes using port
netstat -ano | findstr ":8080"

# Kill by PID
taskkill /PID 1234 /F

# Change docker-compose port
# ports:
#   - "8081:8080"  # Map 8081 → 8080

docker-compose up -d
```

---

## 📊 Monitoring Commands

### Check Everything
```bash
# Docker status
docker version
docker info
docker system df

# Container status
docker ps -a
docker stats

# Image status
docker images
docker image inspect fpm:latest

# Compose status
docker-compose ps
docker-compose logs --tail=20
```

### View Workflow Status
```bash
# GitHub CLI (if installed)
gh workflow list
gh run list
gh run view <RUN-ID>

# Via web
# GitHub.com → Your-Repo → Actions
```

---

## 🚀 Performance Tuning

### Maven
```bash
# Parallel builds
mvn -T 1C clean install  # 1 thread per core

# Skip expensive operations
mvn compile -DskipTests -Dskip.jacoco=true

# Profile build
mvn clean install -Dprofile
```

### Docker
```bash
# Multi-stage optimization
docker build --target builder           # Only build stage

# Layer caching
docker build --progress=plain --no-cache=false   # Show cache usage

# Resource limits
docker run --memory=1024m --cpus=2 fpm:latest
```

### Compose
```bash
# Selective service startup
docker-compose up mysql        # Only MySQL
docker-compose up -d --no-deps fpm-builder
```

---

## 💡 Pro Tips

1. **Cache Maven locally**
   ```bash
   docker run -v ~/.m2:/root/.m2 fpm:latest mvn ...
   ```

2. **Use compose for full env**
   ```bash
   docker-compose up -d  # Start all services once
   docker-compose logs -f
   ```

3. **Automate with shell scripts**
   ```bash
   # build.sh
   #!/bin/bash
   docker build -t fpm:$(date +%s) . && \
   docker run --rm -v $PWD:/app fpm:latest mvn test
   ```

4. **Monitor workflows locally**
   ```bash
   # Poll GitHub Actions
   while true; do git log --oneline -1; sleep 30; done
   ```

5. **Reuse Docker cache**
   ```bash
   docker build --cache-from fpm:latest -t fpm:new .
   ```

---

## 📚 File locations in this project

```
d:\WorkSpace\App_Dev\FPM_Project\libs\
├── .github/workflows/
│   ├── build-and-test.yml       # CI/CD workflow
│   └── release.yml              # Release workflow
├── Dockerfile                    # Image specification
├── docker-compose.yml            # Services config
├── .dockerignore                 # Files to skip
├── HUONG_DAN_DOCKER.md          # Docker guide
├── HUONG_DAN_CICD.md            # CI/CD guide
├── HUONG_DAN_TONG_HOP.md        # Complete guide
├── THAM_KHAO_NHANH.md           # This file (quick reference)
└── fpm-libs/
    └── pom.xml                  # Maven config
```

---

**Use these commands daily! Copy-paste as needed. 🚀**
