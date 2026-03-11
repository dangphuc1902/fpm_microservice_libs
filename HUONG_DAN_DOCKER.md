# 🐳 Hướng Dẫn Docker Chi Tiết cho FPM Libraries

## Mục Lục
1. [Tổng Quan](#tổng-quan)
2. [Chuẩn Bị Môi Trường](#chuẩn-bị-môi-trường)
3. [Build Docker Image](#build-docker-image)
4. [Chạy Docker Container](#chạy-docker-container)
5. [Docker Compose](#docker-compose)
6. [Xóa và Cleanup](#xóa-và-cleanup)
7. [Troubleshooting](#troubleshooting)

---

## Tổng Quan

### Docker là gì?
Docker là công cụ containerization giúp:
- ✅ Đóng gói ứng dụng toàn bộ dependencies
- ✅ Chạy giống nhau trên mọi máy (local, CI/CD, production)
- ✅ Isolated environment, không ảnh hưởng hệ thống
- ✅ Dễ phân phối và scale

### Cấu trúc Project
```
fpm-project/
├── Dockerfile                 # Config build Docker image
├── docker-compose.yml         # Config multi-container setup
├── .dockerignore              # Files không cần copy vào container
├── fpm-libs/
│   ├── pom.xml               # Maven config
│   ├── fpm-core/
│   ├── fpm-common/
│   └── ...modules files
└── docker/
    ├── mysql/                # MySQL init scripts
    └── rabbitmq/             # RabbitMQ config
```

### File Dockerfile giải thích
```dockerfile
# Stage 1: BUILD - Compile project
FROM eclipse-temurin:21-jdk-alpine AS builder
# Eclipse Temurin = OpenJDK 21 trên Alpine Linux (nhỏ gọn)

# Install Maven và Git
RUN apk add --no-cache maven git

# Copy source code
COPY . /build

# Chạy build
RUN mvn clean install -DskipTests

# Stage 2: RUNTIME - Chỉ lấy artifacts cần thiết
FROM eclipse-temurin:21-jdk-alpine

# Tạo non-root user cho security
RUN addgroup -g 1000 app && adduser -D -u 1000 -G app app

# Copy kết quả build từ Stage 1
COPY --from=builder /build /app

USER app
CMD ["mvn", "--version"]
```

**Lợi ích Multi-stage:**
- 📉 Image nhỏ hơn (không chứa build tools)
- 🔒 An toàn hơn (chạy non-root user)
- ⚡ Build nhanh hơn (caching)

---

## Chuẩn Bị Môi Trường

### 1. Cài Đặt Docker Desktop

**Windows:**
```powershell
# Download từ: https://www.docker.com/products/docker-desktop
# Hoặc cài từ PowerShell (nếu có admin)
winget install Docker.DockerDesktop
```

**Verify cài đặt:**
```powershell
docker --version
docker run hello-world
```

### 2. Cấu Hình Docker Desktop
1. Mở **Docker Desktop** settings
2. Go to **Resources**
   - CPUs: Set to 4+ (hoặc nửa số CPU máy)
   - Memory: Set to 4GB+ (tùy khả năng)
   - Disk: Set to 30GB+ (để build artifacts)

### 3. Clone/Navigate đến Project
```powershell
cd d:\WorkSpace\App_Dev\FPM_Project\libs
ls  # Verify Dockerfile exists
```

---

## Build Docker Image

### Bước 1: Build Image Đơn Giản
```powershell
# Build với tag "latest"
docker build -t fpm-libraries:latest .

# Build với tag "dev" (nên dùng khi đang develop)
docker build -t fpm-libraries:dev .

# Build với tag cụ thể version
docker build -t fpm-libraries:1.0.0 .
```

**Giải thích từng phần:**
- `docker build` = Lệnh build image
- `-t fpm-libraries:latest` = Tag image (name:version)
- `.` = Build context (directory hiện tại)

### Bước 2: Monitor Build Process
```powershell
# Build with verbose output
docker build -t fpm-libraries:dev . --progress=plain

# Output example:
# ✓ FROM eclipse-temurin:21-jdk-alpine
# ✓ RUN apk add --no-cache maven git
# ✓ COPY . /build
# ... (chạy Maven compile)
# ✓ Build complete!
```

### Bước 3: Verify Image được Tạo
```powershell
# List all images
docker images

# Output example:
# REPOSITORY           TAG      IMAGE ID       SIZE
# fpm-libraries        latest   abc123def456   650MB
# fpm-libraries        dev      xyz789uvw123   650MB
# eclipse-temurin      21       ...            400MB

# Get detail info
docker image inspect fpm-libraries:latest
```

### ⚠️ Xử Lý Lỗi Build

**Lỗi: "No space left on device"**
```powershell
# Clean unused containers/images
docker system prune -a --volumes
# Sau đó retry build
```

**Lỗi: "Maven memory error"**
```powershell
# Build với environment variable
docker build --build-arg MAVEN_OPTS="-Xmx1024m" -t fpm-libraries:dev .
```

**Lỗi: Network timeout**
```powershell
# Rebuild without cache (forces download)
docker build --no-cache -t fpm-libraries:dev .
```

---

## Chạy Docker Container

### Bước 1: Run Container Đơn Giản
```powershell
# Run container từ image
docker run -it --rm fpm-libraries:latest

# -i = interactive (nhân input)
# -t = terminal (hiển thị output)
# --rm = auto delete container khi stop
# fpm-libraries:latest = image name:tag
```

### Bước 2: Run Container với Shell Access
```powershell
# Vào shell của container
docker run -it --rm fpm-libraries:latest /bin/sh
docker run -it --rm fpm-libraries:1.0.0 /bin/sh

# Bây giờ bạn ở trong container:
# $ ls /app
# $ cd /app/fpm-libs
# $ find . -name "*.jar"
# $ mvn --version

# Exit container
# $ exit
```

### Bước 3: Run Container với Volume Mount
```powershell
# Mount source code vào container (development mode)
docker run -it --rm `
  -v D:\WorkSpace\App_Dev\FPM_Project\libs:/app `
  fpm-libraries:latest /bin/sh

# -v host_path:container_path = Mount folder
# Thay đổi code trên Windows → Changes ở trong container
```

### Bước 4: Run Container với Environment Variables
```powershell
# Cấp memory cho Java
docker run -it --rm `
  -e JAVA_OPTS="-Xmx1024m -Xms512m" `
  fpm-libraries:latest /bin/sh

# Cấp multiple env vars
docker run -it --rm `
  -e JAVA_OPTS="-Xmx1024m" `
  -e MAVEN_OPTS="-Xmx1024m" `
  -e ENV=development `
  fpm-libraries:latest /bin/sh
```

### Bước 5: View Build Artifacts
```powershell
# List JAR files từ container
docker run --rm fpm-libraries:latest `
  find /app/fpm-libs -name "*.jar" -type f

# Output example:
# /app/fpm-libs/fpm-core/target/fpm-core-1.0.0-SNAPSHOT.jar
# /app/fpm-libs/fpm-common/target/fpm-common-1.0.0-SNAPSHOT.jar
# ...
```

### Bước 6: Copy Artifacts từ Container
```powershell
# Tạo temporary container
docker create --name temp-container fpm-libraries:latest

# Copy artifacts
docker cp temp-container:/app/fpm-libs ./fpm-libs-artifacts

# Cleanup
docker rm temp-container

# Verify
ls ./fpm-libs-artifacts/
```

### Background Container

```powershell
# Run container ở background (daemon mode)
docker run -d --name fpm-build `
  -v D:\WorkSpace\App_Dev\FPM_Project\libs:/app `
  fpm-libraries:dev

# Check logs
docker logs -f fpm-build

# Stop container
docker stop fpm-build

# Remove container
docker rm fpm-build
```

---

## Docker Compose

### Tại sao Docker Compose?
Khi project cần:
- MySQL + RabbitMQ + Redis + Build container
- Networking giữa containers
- Persistent volumes
- Environment config

### Chạy Docker Compose

```powershell
# Start all services
docker-compose up -d

# -d = detached mode (background)

# Verify services running
docker-compose ps

# Output:
# NAME              STATUS              PORTS
# fpm-builder       Up 2 minutes        
# fpm-mysql         Up 2 minutes        0.0.0.0:3306->3306/tcp
# fpm-rabbitmq      Up 2 minutes        0.0.0.0:5672->5672/tcp
```

### View Logs

```powershell
# All services logs
docker-compose logs -f

# Specific service logs
docker-compose logs -f mysql
docker-compose logs -f rabbitmq
docker-compose logs -f fpm-builder

# Last N lines
docker-compose logs --tail=100 fpm-builder
```

### Stop Services

```powershell
# Stop all services
docker-compose stop

# Stop specific service
docker-compose stop mysql

# Remove all containers
docker-compose down

# Remove everything (containers + volumes + networks)
docker-compose down -v
```

### Access Services từ Compose

```powershell
# MySQL (host: mysql, port: 3306, trong container network nó là "mysql")
# Từ Windows: localhost:3306
# Từ container: mysql:3306

# RabbitMQ (host: rabbitmq, ports: 5672, 15672-management)
# Từ Windows: localhost:15672 (web management)

# Redis (port: 6379)
# Từ Windows: localhost:6379
```

### Modify docker-compose.yml

```powershell
# Nếu thay đổi docker-compose.yml
docker-compose up -d --force-recreate

# Rebuild image sau thay đổi
docker-compose up -d --build
```

---

## Xóa và Cleanup

### Cleanup Lựa Chọn

```powershell
# 1. Remove dangling containers (stopped)
docker container prune

# 2. Remove dangling images
docker image prune

# 3. Remove unused volumes
docker volume prune

# 4. Remove everything (containers, images, volumes, networks)
docker system prune -a --volumes
```

### Remove Specific Image

```powershell
# List images
docker images

# Remove image
docker rmi fpm-libraries:dev

# Force remove
docker rmi -f fpm-libraries:dev

# Remove multiple images
docker rmi fpm-libraries:latest fpm-libraries:dev
```

### Clear Maven Cache

```powershell
# Maven cache ở trong container volume m2-cache
# Nếu muốn rebuild từ đầu
docker volume rm fpm-project_m2-cache

# Hoặc trong docker-compose down
docker-compose down -v  # -v xóa luôn volumes
```

---

## Troubleshooting

### ❌ "Docker daemon is not running"
**Solution:**
```powershell
# Start Docker Desktop manually
# Hoặc từ PowerShell:
Start-Service docker
```

### ❌ "Image build failed with Maven error"
**Solution:**
```powershell
# 1. Clean Docker build cache
docker build --no-cache -t fpm-libraries:dev .

# 2. Increase Java memory
docker build --build-arg MAVEN_OPTS="-Xmx1024m" -t fpm-libraries:dev .

# 3. Check Maven logs
docker run -it --rm fpm-libraries:dev /bin/sh
# $ cd /app/fpm-libs && mvn help:active-profiles
```

### ❌ "Port already in use" (docker-compose)
**Solution:**
```powershell
# Find what's using port
netstat -ano | findstr ":3306"

# Change port trong docker-compose.yml:
# ports:
#   - "3307:3306"  # Thay 3306 thành 3307

docker-compose up -d
```

### ❌ "Out of disk space"
**Solution:**
```powershell
# Check disk usage
docker system df

# Clean everything
docker system prune -a --volumes

# Increase Docker disk limit:
# 1. Docker Desktop → Settings → Resources → Disk image size
```

### ❌ "Build very slow"
**Solution:**
```powershell
# 1. Increase Docker resources (4+ CPUs, 4GB+ RAM)
# 2. Check network:
docker run --rm --net=host alpine ping -c 1 8.8.8.8

# 3. Build with progress
docker build --progress=plain -t fpm-libraries:dev .

# 4. Use cache
docker build -t fpm-libraries:dev .  # (không use --no-cache)
```

### ✅ Debug trong Container

```powershell
# Interactive shell debugging
docker run -it --rm `
  -v D:\WorkSpace\App_Dev\FPM_Project\libs:/app `
  fpm-libraries:dev /bin/sh

# Trong shell:
$ cd /app/fpm-libs
$ mvn clean compile  # test specific step
$ ls target/  # check output
$ mvn dependency:tree  # verify dependencies
$ java -version  # check Java
```

---

## Cheat Sheet

```powershell
# ========== IMAGE OPERATIONS ==========
docker build -t fpm-libraries:dev .           # Build image
docker images                                  # List images
docker image inspect fpm-libraries:dev        # Image details
docker rmi fpm-libraries:dev                  # Remove image

# ========== CONTAINER OPERATIONS ==========
docker run -it --rm fpm-libraries:dev         # Run interactive
docker run -d --name my-container fpm:dev    # Run background
docker ps -a                                  # List containers
docker logs -f container-name                 # View logs
docker stop container-name                    # Stop container
docker rm container-name                      # Remove container
docker exec -it container-name /bin/sh        # Access container

# ========== VOLUME OPERATIONS ==========
docker volume ls                              # List volumes
docker volume inspect volume-name             # Volume details
docker volume rm volume-name                  # Remove volume

# ========== COMPOSE OPERATIONS ==========
docker-compose up -d                          # Start services
docker-compose ps                             # List services
docker-compose logs -f service-name           # View logs
docker-compose stop                           # Stop services
docker-compose down -v                        # Stop + remove + volumes

# ========== CLEANUP ==========
docker system prune -a --volumes              # Clean all
docker container prune                        # Clean containers
docker image prune                            # Clean images
docker volume prune                           # Clean volumes
```

---

## Next Steps

✅ Hoàn thành Docker setup  
👉 Tiếp theo: Đọc `HUONG_DAN_CICD.md` để hiểu CI/CD workflows
