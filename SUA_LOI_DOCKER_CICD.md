# 🆘 Troubleshooting Guide - Docker & CI/CD Issues

## Quick Issue Finder

| Problem | Solution Link |
|---------|---------------|
| Build fails | [Build Failures](#build-failures) |
| Docker issues | [Docker Problems](#docker-problems) |
| Container won't run | [Container Runtime](#container-runtime-issues) |
| Port conflicts | [Port Issues](#port-conflicts) |
| CI/CD not triggering | [Workflow Triggers](#workflow-triggers-not-working) |
| Docker push fails | [Registry Issues](#docker-registry-issues) |
| Tests failing | [Test Failures](#test-failures) |
| Out of disk space | [Resource Issues](#resource-issues) |
| Slow builds | [Performance Issues](#performance-issues) |

---

## Build Failures

### Error: "BUILD FAILURE" in Maven

**Symptoms:**
```
[ERROR] COMPILATION ERROR
[ERROR] SOURCE ERRORS
[ERROR] BUILD FAILED
```

**Solutions:**

1. **Check specific error message**
```bash
# Look for error between [ERROR] markers
# Common errors:
# - "Compilation error" → Java syntax issue
# - "Cannot find symbol" → Missing dependency
# - "OutOfMemory" → Increase memory
```

2. **Increase Java memory**
```powershell
# Option A: Set environment variable (Windows)
$env:MAVEN_OPTS = "-Xmx1024m"
mvn clean install

# Option B: In Docker
docker build --build-arg MAVEN_OPTS="-Xmx1024m" -t fpm:dev .

# Option C: In Dockerfile
ENV MAVEN_OPTS="-Xmx1024m"
```

3. **Clean and rebuild**
```bash
cd fpm-libs

# Remove all build artifacts
mvn clean

# Rebuild from scratch
mvn install -DskipTests

# If still fails, check for syntax errors
mvn compile
```

4. **Check dependency issues**
```bash
# Show dependency tree
mvn dependency:tree

# Check for conflicts
mvn dependency:analyze

# Update dependencies
mvn dependency:update-all
```

5. **Verify Java version**
```bash
java -version
javac -version

# Should show Java 21
# If not, set JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
```

### Error: "Missing dependencies"

**Symptoms:**
```
[ERROR] Could not find artifact org.springframework:spring-core:jar:6.0.0
[ERROR] Failure to find ... in https://repo.maven.apache.org/maven2
```

**Solutions:**

1. **Check .m2 cache**
```bash
# Remove local cache
cd %userprofile%\.m2\repository
del /S /Q *  # Clear repo

# Maven will re-download on next build
mvn clean install
```

2. **Force dependency resolution**
```bash
mvn dependency:resolve
mvn dependency:go-offline  # Download all deps
```

3. **Check internet connection**
```bash
ping maven.org
# If fails, check network/proxy settings

# For corporate proxy:
# ~/.m2/settings.xml - configure proxy
```

4. **Check pom.xml** 
```bash
# Verify pom.xml syntax
mvn help:effective-pom | head -50

# Check parent POM location
# <parent>
#   <groupId>org.springframework.boot</groupId>
#   <artifactId>spring-boot-starter-parent</artifactId>
#   <version>3.3.5</version>
#   <relativePath /> ← Should find from Maven Central
# </parent>
```

---

## Docker Problems

### Error: "Docker daemon is not running"

**Symptoms:**
```
Cannot connect to Docker daemon
Error response from daemon: request returned HTTP status 409
```

**Solutions (Windows):**

1. **Start Docker Desktop**
```powershell
# Option A: Manually start Docker Desktop
# - Click Docker Desktop icon on desktop or start menu
# - Wait for "Docker Engine is running" notification

# Option B: Via PowerShell
Start-Service docker  # Requires admin

# Option C: Check if running
docker --version
docker run hello-world
```

2. **Verify Docker installation**
```powershell
docker --version
docker images
docker ps

# All should work without errors
```

### Error: "Image build failed"

**Symptoms:**
```
ERROR: failed to solve with frontend dockerfile.v0:
failed to build LLB: <error details>
```

**Solutions:**

1. **Build with verbose output**
```bash
# See each step
docker build --progress=plain -t fpm:dev .

# Look for the step where it fails
```

2. **Interactive debugging**
```bash
# Modify Dockerfile temporarily to debug
FROM eclipse-temurin:21-jdk-alpine
RUN apk add --no-cache maven
WORKDIR /build
COPY . .
RUN mvn --version  # Debug step
RUN mvn dependency:resolve  # Check deps

# Build and debug
docker build -t fpm:debug . -f Dockerfile.debug
```

3. **Common build issues**

| Error | Cause | Fix |
|-------|-------|-----|
| "No space left" | Disk full | `docker system prune -a --volumes` |
| "DNS error" | Network issue | Check internet, Docker settings |
| "Cannot find command" | Missing in image | Add `RUN apk add --no-cache <package>` |
| "Memory error" | OOM | Increase Docker memory or MAVEN_OPTS |

4. **Check Docker resources**
```powershell
# Docker Desktop settings:
# Settings → Resources
# - CPUs: Set to 4+ (or half of your system CPUs)
# - Memory: Set to 4GB+
# - Disk: Set to 30GB+

# Verify available resources
docker system df
docker stats
```

### Error: "Cannot access shared resources"

**Symptoms:**
```
Drive has not been shared: D:\
Mounts denied: ...
```

**Solutions (Docker Desktop):**
1. Settings → Resources → File Sharing
2. Add drive D:\ to the list
3. Click "Apply & Restart"
4. Retry build

---

## Container Runtime Issues

### Error: Container exits immediately

**Symptoms:**
```
docker run hello-world
# Container runs and exits (no error)

docker ps  # Nothing listed
```

**Solutions:**

1. **Check container logs**
```bash
# List all containers (including stopped)
docker ps -a

# View why it exited
docker logs container-id

# View with full details
docker logs -f container-id
```

2. **Keep container running**
```bash
# Add sleep command
docker run -it fpm:latest sleep 3600

# Or interactive shell
docker run -it fpm:latest /bin/sh
# Don't exit until you type "exit"
```

3. **Check process configuration**
```bash
# Dockerfile CMD is:
CMD ["mvn", "--version"]
# ↑ This exits after running

# Change to keep running:
CMD ["sleep", "infinity"]
# Or: CMD ["/bin/sh"]
```

### Error: "Permission denied" in container

**Symptoms:**
```
Permission denied: /app
touch: cannot create: Read-only file system
```

**Solutions:**

1. **Check volume mount options**
```bash
# This may cause permission issues:
docker run -it fpm:latest /bin/sh
# /app is copied (not mounted)

# Better: mount with proper permissions
docker run -it \
  -v /path/to/source:/app \
  fpm:latest /bin/sh
```

2. **Use non-root user in Dockerfile**
```dockerfile
# Current (root):
USER root
RUN chmod 777 /app

# Better (non-root):
RUN addgroup -g 1000 app && \
    adduser -D -u 1000 -G app app
USER app

# But if you need to write:
RUN touch /app/file && \
    chown app:app /app/file
```

### Error: "Cannot connect to service"

**Symptoms:**
```
# From inside container
$ curl mysql:3306
curl: (6) Could not resolve host: mysql
```

**Solutions:**

1. **Check Docker network**
```bash
# Services must be on same network
docker network ls
docker inspect fpm-network  # Check for services

# If using compose:
docker-compose ps
# All services should show same network
```

2. **Use proper hostnames**
```bash
# From Windows: localhost:3306
# From container: mysql:3306 (service name)

# Verify in /etc/hosts (docker internal)
docker exec mysql cat /etc/hosts
```

3. **Enable inter-container communication**
```bash
# In docker-compose.yml:
# All services automatically on network

# Manual containers:
docker network create fpm-network
docker run --network fpm-network --name mysql IMAGE
docker run --network fpm-network --name app IMAGE
```

---

## Port Conflicts

### Error: "Address already in use" / "Port 3306 already allocated"

**Symptoms:**
```
Error response from daemon: driver failed programming external connectivity 
on endpoint fpm-mysql (...): Bind for 0.0.0.0:3306 failed
```

**Solutions (Windows):**

1. **Find what's using the port**
```powershell
# Check which process uses port
netstat -ano | findstr "3306"
# Output: TCP    127.0.0.1:3306    ... 12345

# 12345 = PID (Process ID)

# Find process name
tasklist | findstr "12345"

# Or directly:
Get-Process -Id 12345
```

2. **Kill the process**
```powershell
# Kill by PID
taskkill /PID 12345 /F

# Or kill by name (careful - might kill wrong process)
taskkill /IM mysqld.exe /F
```

3. **Change port in docker-compose.yml**
```yaml
# Original (conflicts):
services:
  mysql:
    ports:
      - "3306:3306"  # Error!

# Solution:
services:
  mysql:
    ports:
      - "3307:3306"  # Use 3307 on Windows
              ↑       ↑
          Host Port  Container Port
```

4. **Use alternative ports**
```yaml
# Map different ports
services:
  mysql:
    ports:
      - "3307:3306"      # MySQL on 3307
  rabbitmq:
    ports:
      - "5673:5672"      # AMQP on 5673
      - "15673:15672"    # Management on 15673
  redis:
    ports:
      - "6380:6379"      # Redis on 6380
```

5. **Verify port is free**
```powershell
# Check if port is available
$port = 3306
$tcpConnection = Test-NetConnection -ComputerName "127.0.0.1" -Port $port -ErrorAction SilentlyContinue
if ($null -eq $tcpConnection.TcpTestSucceeded) {
    Write-Host "Port $port is available"
} else {
    Write-Host "Port $port is in use"
}
```

---

## Workflow Triggers Not Working

### GitHub Actions workflow not executing

**Symptoms:**
```
Push code → No workflow runs
Create PR → No workflow runs
```

**Check these:**

1. **Verify repository is on GitHub**
```bash
# Is it actually on GitHub?
git remote -v
# Should show: origin https://github.com/YOUR-REPO.git

# If not:
git remote add origin https://github.com/YOUR-REPO.git
git push -u origin main
```

2. **Verify workflows exist**
```bash
# Are .github/workflows/*.yml files pushed?
git log -- .github/workflows/

# If no commits, need to push:
git add .github/
git commit -m "Add GitHub Actions workflows"
git push origin main
```

3. **Verify branch/tag name**
```yaml
# build-and-test.yml triggers on:
on:
  push:
    branches:
      - main        # ← MUST push to "main"
      - develop     # ← OR push to "develop"

# WILL NOT trigger:
# - git push to feature/my-feature
# - git push to experimental
```

**Solution:**
```bash
# Check current branch
git branch

# Push to correct branch
git push origin main    # Will trigger!
git push origin develop # Will trigger!
git push origin feature/new  # Will NOT trigger
```

4. **Check workflow syntax**
```bash
# Invalid YAML causes silent failure
# Validate in editor or:
# https://yamllint.com/

# Common issues:
# - Incorrect indentation (YAML is whitespace-sensitive)
# - Missing colons
# - Windows line endings (CRLF instead of LF)
```

5. **View workflow status on GitHub**
```
1. Go to GitHub.com → Your-Repo
2. Click "Actions" tab
3. See "No runs" or recent runs?

If no runs:
- Commits show as red X instead of green ✓
- Check commit details → "Checks" tab
- See if workflow configuration error
```

---

## Docker Registry Issues

### Error: "authentication required" / "unauthorized"

**Symptoms:**
```
denied: User: <user> cannot pull
Unauthorized: authentication required
```

**Solutions:**

1. **Login to ghcr.io (GitHub Container Registry)**
```bash
# Create Personal Access Token (PAT):
# GitHub.com → Settings → Developer settings → Personal access tokens
# - Scopes: write:packages, read:packages, delete:packages
# - Copy token

# Login locally
docker login ghcr.io -u YOUR-USERNAME -p YOUR-PAT
# Input: Username and token

# Verify login
cat ~/.docker/config.json | findstr ghcr

# Or for compose:
cat ~/.docker/config.json
# Should contain ghcr.io credentials
```

2. **Check secrets in GitHub Actions**
```
Go to: GitHub.com → Your-Repo → Settings → Secrets → Actions

Check:
- GITHUB_TOKEN exists (should be auto-created by GitHub)
- For custom registry: Add your token as secret

In workflow:
  - name: 'Login'
    uses: docker/login-action@v3
    with:
      registry: ghcr.io
      username: ${{ github.actor }}
      password: ${{ secrets.GITHUB_TOKEN }}
```

3. **Push image with correct name**
```bash
# Image name must match registry format:
# ghcr.io/GITHUB-USERNAME/IMAGE-NAME:TAG

# Build with correct name:
docker build -t ghcr.io/john-doe/fpm-libraries:latest .

# Push:
docker push ghcr.io/john-doe/fpm-libraries:latest

# Verify pushed:
# GitHub.com → Your-Profile → Packages → fpm-libraries
```

4. **Check registry access**
```bash
# Test authentication
docker login ghcr.io

# Try pulling a public image
docker pull ghcr.io/<username>/<repo>:latest

# If it works, you're authenticated
```

---

## Test Failures

### Maven tests fail in build

**Symptoms:**
```
[ERROR] FAILURE
Tests run: 10, Failures: 1, Errors: 0
[ERROR] BUILD FAILURE
```

**Solutions:**

1. **View detailed test failure**
```bash
# Maven output shows test name and error
# Look for:
#   - Test class name
#   - Test method that failed
#   - Assertion error message

cd fpm-libs
mvn test  # Shows detailed failure
```

2. **Run specific test**
```bash
# Test single class
mvn test -Dtest=MyTestClass

# Test single method
mvn test -Dtest=MyTestClass#testMethod

# Run with output
mvn test -X  # Debug mode

# Show print statements
mvn test -Dtest=MyTest -X -e
```

3. **Skip tests if not ready**
```bash
# For local development
mvn clean install -DskipTests

# For CI/CD (if needed to fix later)
# - Edit workflow to skip tests temporarily
# - Then commit fix for tests
```

4. **Check test resources**
```bash
# Tests need database/services?
# Ensure they're running:
docker-compose up -d mysql rabbitmq

# Then run tests
mvn test

# Or tests should be unit tests (no dependencies)
```

5. **Integration tests vs Unit tests**
```bash
# Run only unit tests (fast)
mvn test -Dgroups="!integration"

# Run only integration tests
mvn test -Dgroups="integration"

# Or skip integration tests
mvn test -DskipITs

# Check pom.xml for test configuration
```

---

## Resource Issues

### Error: "No space left on device"

**Symptoms:**
```
Error: No space left on device
BuildKit failed to build image
```

**Solutions (Windows):**

1. **Check disk space**
```powershell
# Show disk usage
Get-Volume

# Or:
dir C:
# See "Free Space"

# Need at least 10GB free for Docker
```

2. **Clean Docker resources**
```bash
# View Docker usage
docker system df

# Remove dangling images
docker image prune

# Remove unused volumes
docker volume prune

# Remove all unused (containers, images, networks)
docker system prune -a --volumes

# Force remove big items
docker system prune -a -f --volumes
```

3. **Clear Maven cache**
```powershell
# Remove ~/.m2 folder
rmdir %userprofile%\.m2 /S /Q

# Or specific:
rmdir %userprofile%\.m2\repository /S /Q
```

4. **Increase Docker disk limit**
```
Docker Desktop → Settings → Resources → Disk image size
- Default: 64GB
- Increase to 100GB+ if needed
- Click "Apply & Restart"
```

5. **Check large files**
```bash
# Find largest files
docker image ls --human-readable --all

# Find largest volumes
docker volume ls

# Remove specific image
docker rmi IMAGE_ID
```

### Error: "Out of memory" (OutOfMemoryError)

**Symptoms:**
```
OutOfMemoryError: Java heap space
Exception in thread "main" java.lang.OutOfMemoryError
```

**Solutions:**

1. **Increase Java memory**
```bash
# Via environment variable
export JAVA_OPTS="-Xmx2048m -Xms512m"
mvn clean install

# Via Maven options
mvn -Xmx2048m -Xms512m clean install

# In Docker
docker build --build-arg MAVEN_OPTS="-Xmx2048m" -t fpm:dev .

# In Dockerfile
ENV MAVEN_OPTS="-Xmx2048m"
ENV JAVA_OPTS="-Xmx2048m -Xms512m"
```

2. **Increase Docker memory**
```
Docker Desktop → Settings → Resources
- Memory: Increase from 2GB to 4GB+
- Click "Apply & Restart"
```

3. **Reduce parallel builds**
```bash
# Instead of parallel:
mvn -T 1 clean install  # 1 thread per core (serial)

# Parallel can use more memory
```

---

## Performance Issues

### Build is very slow

**Symptoms:**
```
Build takes 10+ minutes (should be 3-5 minutes)
```

**Solutions:**

1. **Enable caching**
```bash
# Maven caching (in GitHub Actions)
# actions/setup-java@v4:
#   cache: maven

# Docker layer caching
docker build ${BUILDKIT_INLINE_CACHE:+--build-arg BUILDKIT_INLINE_CACHE=1} \
  -t fpm:dev .

# Docker compose caching
docker-compose build --no-cache  # Disables
docker-compose build             # Uses cache
```

2. **Reduce build scope**
```bash
# Skip tests during build
mvn clean install -DskipTests

# Skip javadoc
mvn clean install -DskipTests -Dmaven.javadoc.skip=true

# Parallelize Maven
mvn -T 1C clean install    # 1 thread per core
```

3. **Optimize Dockerfile**
```dockerfile
# Bad (rebuilds every time):
COPY . /build
RUN mvn install

# Good (leverages cache):
COPY pom.xml /build/
RUN mvn dependency:resolve
COPY src /build/src
RUN mvn compile
```

4. **Check network bottleneck**
```bash
# Test DNS resolution
nslookup maven.org  # or ping

# Test download speed
# Try downloading random file
curl https://repo.maven.apache.org/maven2/org/springframework/spring-core/6.0.0/spring-core-6.0.0.jar -O

# If slow, it's network issue
```

5. **Monitor resources during build**
```bash
# Watch CPU/Memory usage
docker stats

# If low CPU/Memory usage:
# - Increase Docker resources
# - Increase JAVA_OPTS parallel threads
```

---

## Cannot Push to GitHub

### Error: "fatal: 'https://github.com/user/repo.git' does not appear to be a 'git' repository"

**Solutions:**

1. **Verify remote is set correctly**
```bash
git remote -v
# Should show:
# origin https://github.com/YOUR-USERNAME/YOUR-REPO.git

# If not:
git remote remove origin
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPO.git
git branch -u origin/main main
```

2. **Test authentication**
```bash
# Clone a test repo to verify auth works
cd /tmp
git clone https://github.com/YOUR-USERNAME/ANY-REPO.git

# If it works, authentication is fine
cd /path/to/fpm-project
git push origin main
```

---

## Getting Help

### Enable Debug Logging

**For GitHub Actions:**
```
Settings → Secrets → New repository secret
- Name: ACTIONS_STEP_DEBUG
- Value: true

Re-run workflow → More verbose logs
```

**For Docker:**
```bash
# Enable experimental features
# Docker Desktop → Settings → Docker Engine → {"debug": true}

# Or verbose build
docker build --progress=plain -t fpm:dev .
```

**For Maven:**
```bash
# Debug output
mvn -X clean install

# Show effective POM
mvn help:effective-pom

# Log to file
mvn clean install > build.log 2>&1
type build.log
```

### Useful Debugging Commands

```bash
# Check logs
docker logs -f container-name
docker-compose logs -f service-name

# Inspect configuration
docker image inspect fpm:latest
docker container inspect container-name
docker-compose config

# Run interactive debug
docker run -it --rm fpm:latest /bin/sh
docker exec -it container-name /bin/sh

# Check system health
docker system df
docker volume ls
docker network ls
```

---

## When All Else Fails

1. **Clean everything and restart**
```bash
docker system prune -a --volumes --force
docker volume prune --force

# Restart Docker Desktop
```

2. **Rebuild from cache**
```bash
rm -rf fpm-libs/target/
mvn clean install -U  # Force update
```

3. **Check logs carefully**
```bash
# Save output to file
mvn clean install 2>&1 | tee build.log

# Search for error
grep -i "error" build.log
```

4. **Ask for help with logs**
```bash
# When reporting issue, include:
- Full output of failing command
- docker version
- docker-compose version
- java -version
- mvn --version
- OS information
```

---

**Still stuck? Check:**
- Project README files
- GitHub Issues (your repo + upstream projects)
- Stack Overflow (tag: docker, maven, github-actions, java)
- Official documentation

---

💡 **Pro tip:** Often the issue is simple - check the error message first!
