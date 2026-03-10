# 🚀 FPM Libraries - Docker & CI/CD Deployment Guide

**Phiên bản:** 1.0.0  
**Cập nhật:** March 2026  
**Trạng thái:** Production Ready ✅

---

## 📑 Mục Lục

1. [Yêu Cầu Hệ Thống](#yêu-cầu-hệ-thống)
2. [Quick Start - Bắt Đầu Nhanh](#quick-start-bắt-đầu-nhanh)
3. [Chi Tiết Từng Bước](#chi-tiết-từng-bước)
4. [Docker Build & Run](#docker-build--run)
5. [CI/CD Pipeline Setup](#cicd-pipeline-setup)
6. [Troubleshooting](#troubleshooting)

---

## ✅ Yêu Cầu Hệ Thống

### Bắt buộc
- **Docker Desktop**: v24.0+ ([Download](https://www.docker.com/products/docker-desktop))
- **Docker Compose**: v2.20+ (Bao gồm trong Docker Desktop)
- **Git**: v2.40+
- **Java**: JDK 21 (tùy chọn, Docker sẽ có sẵn)

### Kiểm tra cài đặt
```powershell
# Windows PowerShell
docker --version
docker-compose --version
git --version

# Output mong đợi:
# Docker version 24.0.0+
# Docker Compose version 2.20.0+
# git version 2.40.0+
```

---

## 🚀 Quick Start - Bắt Đầu Nhanh

### Cho người dùng Windows (PowerShell)

```powershell
# 1. Chuyển đến thư mục dự án
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# 2. Build Docker image
docker build -t fpm-libraries:1.0.0 -f Dockerfile .

# 3. Kiểm tra image vừa tạo
docker images | findstr fpm-libraries

# 4. Chạy container để test
docker run --rm -it fpm-libraries:1.0.0 java -version

# 5. Chạy docker-compose (tất cả services)
docker-compose up -d

# 6. Kiểm tra trạng thái
docker-compose ps

# 7. Xem logs của build
docker-compose logs -f fpm-builder

# 8. Dừng tất cả services
docker-compose down
```

### Cho người dùng Linux/Mac (Bash)

```bash
# 1. Chuyển đến thư mục dự án
cd /path/to/FPM_Project/libs

# 2. Build Docker image
docker build -t fpm-libraries:1.0.0 -f Dockerfile .

# 3. Kiểm tra image
docker images | grep fpm-libraries

# 4. Chạy container để test
docker run --rm -it fpm-libraries:1.0.0 java -version

# 5. Chạy docker-compose
docker-compose up -d

# 6. Xem logs
docker-compose logs -f fpm-builder

# 7. Dừng services
docker-compose down
```

---

## 📝 Chi Tiết Từng Bước

### Bước 1: Chuẩn Bị Dự Án

```powershell
# Đảm bảo Docker đang chạy
docker ps

# Chuyển đến thư mục dự án
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# Kiểm tra cấu trúc
ls -la | findstr "Dockerfile|docker-compose"
```

**Output mong đợi:**
```
Dockerfile                          # File cấu hình Docker
docker-compose.yml                  # File cấu hình docker-compose
```

### Bước 2: Build Maven Project (không cần Docker lúc này)

```powershell
# Tùy chọn: Build cục bộ trước để kiểm tra
cd fpm-libs
mvn clean install -DskipTests
cd ..
```

**Nếu gặp lỗi:**
- Đảm bảo Java 21 được cài đặt
- Chạy: `java -version`

### Bước 3: Build Docker Image

```powershell
# Build image
docker build -t fpm-libraries:1.0.0 -f Dockerfile .

# Hoặc build với tag latest (để test nhanh)
docker build -t fpm-libraries:latest -f Dockerfile .
```

**Giải thích:**
- `-t fpm-libraries:1.0.0` → Đặt tên và phiên bản image
- `-f Dockerfile` → Chỉ định file Dockerfile
- `.` → Context (thư mục hiện tại)

**Thời gian:** ~3-5 phút lần đầu (lần sau nhanh hơn do cache)

### Bước 4: Kiểm Tra Image

```powershell
# Liệt kê tất cả images
docker images

# Kiểm tra chi tiết về image
docker inspect fpm-libraries:1.0.0

# Kiểm tra dung lượng
docker images --format "table {{.Repository}}\t{{.Size}}" | findstr fpm
```

### Bước 5: Chạy Container

```powershell
# Chạy container đơn giản (kiểm tra Java)
docker run --rm fpm-libraries:1.0.0 java -version

# Chạy container interactively
docker run --rm -it fpm-libraries:1.0.0 /bin/sh

# Chạy container với volume (để develop)
docker run --rm -it -v d:\WorkSpace\App_Dev\FPM_Project\libs:/app fpm-libraries:1.0.0 /bin/sh

# Chạy container backend (nếu là service)
docker run -d --name fpm-service \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx512m" \
  fpm-libraries:1.0.0
```

---

## 🐳 Docker Build & Run

### Build Multi-Stage (Hiệu quả)

Dockerfile hiện tại sử dụng multi-stage build:

**Stage 1: Builder**
```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS builder
# - Cài Maven
# - Build Maven project
# - Tạo JAR/classes
```

**Stage 2: Runtime**
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
# - Copy artifacts từ Stage 1
# - Setup non-root user
# - Chạy ứng dụng
```

**Lợi ích:** Image runtime nhỏ hơn, không chứa build tools

### Tối ưu Build Time

```powershell
# Xóa cache nếu cần rebuild từ đầu
docker build --no-cache -t fpm-libraries:1.0.0 .

# Build với progress output chi tiết
docker build --progress=plain -t fpm-libraries:1.0.0 .

# Build nền (không chặn terminal)
docker build -t fpm-libraries:1.0.0 . | Out-Null
Write-Host "Build hoàn tất!"
```

### Docker Compose - Tất cả Services

```powershell
# 1. Khởi động tất cả services
docker-compose up -d

# 2. Kiểm tra status
docker-compose ps

# 3. Xem logs
docker-compose logs                    # Tất cả services
docker-compose logs fpm-builder        # Chỉ fpm-builder
docker-compose logs -f mysql           # Theo dõi real-time MySQL

# 4. Kiểm tra health của service
docker-compose exec fpm-builder ps aux

# 5. Chạy command trong container
docker-compose exec mysql mysql -u root -p

# 6. Rebuild service (nếu code thay đổi)
docker-compose up -d --build fpm-builder

# 7. Dừng all services
docker-compose down

# 8. Dừng và xóa volumes
docker-compose down -v

# 9. Clean up (xóa images, networks)
docker-compose down --rmi all
```

### Services có sẵn trong Docker Compose

| Service | Port | Username | Password | Mục đích |
|---------|------|----------|----------|---------|
| **fpm-builder** | - | - | - | Build FPM libraries |
| **mysql** | 3306 | root | root_password | Database |
| **rabbitmq** | 5672, 15672 | guest | guest | Message broker |
| **redis** | 6379 | - | redis_password | Cache |
| **pgadmin** | 5050 | admin@example.com | admin | PG Web UI |

---

## 📦 CI/CD Pipeline Setup

### 1. GitHub Actions Workflow

Tạo file `.github/workflows/build-and-test.yml`:

```yaml
name: Build & Test

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v2
      
      - name: Build Docker image
        uses: docker/build-push-action@v4
        with:
          context: ./libs
          file: ./libs/Dockerfile
          push: false
          tags: fpm-libraries:latest
          cache-from: type=gha
          cache-to: type=gha,mode=max
      
      - name: Test Maven Build
        run: |
          cd libs/fpm-libs
          mvn clean verify
```

### 2. GitHub Actions Release Workflow

Tạo file `.github/workflows/release.yml`:

```yaml
name: Release & Publish

on:
  push:
    tags:
      - 'v*'

jobs:
  release:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v2
      
      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      
      - name: Push Docker image
        uses: docker/build-push-action@v4
        with:
          context: ./libs
          file: ./libs/Dockerfile
          push: true
          tags: |
            ${{ secrets.DOCKER_USERNAME }}/fpm-libraries:${{ github.ref_name }}
            ${{ secrets.DOCKER_USERNAME }}/fpm-libraries:latest
```

### 3. Setup GitHub Secrets

1. Truy cập: **Settings** → **Secrets and variables** → **Actions**
2. Thêm secrets:
   - `DOCKER_USERNAME`: Username Docker Hub
   - `DOCKER_PASSWORD`: Access token Docker Hub (không dùng password)

### 4. GitLab CI (Thay thế GitHub Actions)

Tạo phần ngoài thư mục gốc)

```yaml
default:
  image: docker:latest
  services:
    - docker:dind

stages:
  - build
  - test
  - push

build:image:
  stage: build
  script:
    - cd libs
    - docker build -t fpm-libraries:latest .
    - docker save fpm-libraries:latest | gzip > image.tar.gz
  artifacts:
    paths:
      - libs/image.tar.gz

test:maven:
  stage: test
  image: maven:3.9-eclipse-temurin-21
  script:
    - cd libs/fpm-libs
    - mvn clean verify

push:docker:
  stage: push
  script:
    - docker load < libs/image.tar.gz
    - docker login -u $DOCKER_LOGIN -p $DOCKER_PASSWORD
    - docker tag fpm-libraries:latest $CI_REGISTRY_IMAGE:latest
    - docker push $CI_REGISTRY_IMAGE:latest
  only:
    - tags
```

---

## 🐛 Troubleshooting

### Lỗi: "Docker daemon not running"

```powershell
# Windows: Khởi động Docker Desktop
# Hoặc:
Start-Service Docker
# Hoặc mở Docker Desktop từ Start Menu
```

### Lỗi: "Cannot connect to Docker daemon"

```powershell
# Kiểm tra quyền (Windows)
# Thêm user vào docker group:
net localgroup docker-users %USERNAME% /add

# Sau đó restart PowerShell/Terminal
```

### Lỗi: "Maven dependency error"

```powershell
# Xóa cache Maven
docker-compose exec fpm-builder mvn clean

# Hoặc xóa volume
docker-compose down -v
docker-compose up -d
```

### Lỗi: "Port already in use"

```powershell
# Tìm process chiếm port (ví dụ port 3306)
netstat -ano | findstr :3306

# Kill process
taskkill /PID <PID> /F

# Hoặc thay đổi port trong docker-compose.yml:
# ports:
#   - "3307:3306"  # Dùng 3307 thay vì 3306
```

### Lỗi: "Build context too large"

```powershell
# Kiểm tra .dockerignore
cat .dockerignore

# Thêm các file không cần:
# target/
# .git/
# node_modules/
```

### Container keeps exiting

```powershell
# Xem chi tiết logs
docker logs fpm-builder

# Chạy bằng tay để debug
docker run -it fpm-libraries:1.0.0 /bin/sh

# Kiểm tra Dockerfile CMD
docker inspect fpm-libraries:1.0.0 | findstr -i cmd
```

---

## 📊 Monitoring & Logs

### Xem Logs

```powershell
# Tất cả logs
docker-compose logs

# Chỉ fpm-builder với 50 dòng cuối
docker-compose logs -n 50 fpm-builder

# Follow logs (realtime)
docker-compose logs -f

# Lọc logs theo keyword
docker-compose logs | findstr "ERROR"
```

### stats (Tài nguyên CPU/Memory)

```powershell
# Xem tài nguyên thực tế
docker stats

# Chỉ fpm-builder
docker stats fpm-builder
```

### Health Check

```powershell
# Kiểm tra health status
docker-compose ps

# Output mong đợi: healthy, starting, unhealthy
```

---

## 🔒 Security Best Practices

### 1. Non-root User
✅ Dockerfile sử dụng app user (UID 1000)

### 2. Image Scanning

```powershell
# Scan với Trivy (nếu cài)
trivy image fpm-libraries:1.0.0

# Hoặc dùng Docker Scout
docker scout cves fpm-libraries:1.0.0
```

### 3. Secrets Management

```powershell
# Không hardcode credentials
# Dùng environment variables hoặc Docker secrets
```

---

## 📋 Checklist Deployment

- [ ] Docker Desktop cài và chạy
- [ ] Git repo clone/updated
- [ ] Chạy `docker build` thành công
- [ ] Container chạy mà không lỗi
- [ ] docker-compose ps show healthy
- [ ] CI/CD workflow đã push (.github/workflows)
- [ ] Docker Hub credentials setup (nếu cần push)
- [ ] README.md có instructions rõ ràng

---

## 📞 Support & Resources

### Documentation
- [Docker Official Docs](https://docs.docker.com/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)
- [GitHub Actions Docs](https://docs.github.com/en/actions)

### Tools
- Docker Desktop: https://www.docker.com/products/docker-desktop
- Visual Studio Code Docker Extension: ms-azuretools.vscode-docker

### Tệp tham khảo trong dự án
- `Dockerfile` - Docker configuration
- `docker-compose.yml` - Multi-container setup
- `.github/workflows/` - CI/CD pipelines
- `docker-deployment/GUIDE.md` - File này

---

## 🎯 Next Steps

1. **Chạy Quick Start**
   ```powershell
   cd d:\WorkSpace\App_Dev\FPM_Project\libs
   docker build -t fpm-libraries:1.0.0 .
   ```

2. **Test Docker Compose**
   ```powershell
   docker-compose up -d
   docker-compose logs
   ```

3. **Setup CI/CD**
   - Tạo `.github/workflows/` folder
   - Copy workflow files từ phần trên
   - Push lên GitHub

4. **Deploy to Production**
   - Tag release: `git tag -a v1.0.0 -m "Release v1.0.0"`
   - Push tag: `git push origin v1.0.0`
   - CI/CD tự động build và push image

---

**Made with ❤️ for FPM Libraries**  
**Version:** 1.0.0 | **Last Updated:** March 2026
