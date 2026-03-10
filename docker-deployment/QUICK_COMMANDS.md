# ⚡ Docker & CI/CD Quick Reference

## 🚀 Cách Chạy (Chọn 1 trong 3)

### Option 1: Dùng Helper Script (Khuyên dùng)

**Windows:**
```powershell
cd d:\WorkSpace\App_Dev\FPM_Project\libs
.\docker-setup.ps1 setup
```

**Linux/Mac:**
```bash
cd /path/to/FPM_Project/libs
chmod +x docker-setup.sh
./docker-setup.sh setup
```

### Option 2: Dùng Docker Commands Trực Tiếp

```bash
# Build image
docker build -t fpm-libraries:1.0.0 .

# Start services
docker-compose up -d

# View logs
docker-compose logs -f
```

### Option 3: Dùng Make (Nếu cài)

```makefile
.PHONY: build run logs clean

build:
	docker build -t fpm-libraries:latest .

run:
	docker-compose up -d

logs:
	docker-compose logs -f

clean:
	docker-compose down -v
```

---

## 📋 Common Commands

### Build & Run

| Command | Mục đích | Thời gian |
|---------|---------|----------|
| `docker build -t fpm-libraries:1.0.0 .` | Build image | 3-5 min |
| `docker run -it fpm-libraries:1.0.0 /bin/sh` | Chạy container | Instant |
| `docker-compose up -d` | Start all services | 10-30 sec |
| `docker-compose down` | Stop all services | 5 sec |
| `docker-compose ps` | Xem status services | Instant |

### Logs & Monitoring

```bash
# Xem logs tất cả services
docker-compose logs

# Follow logs (realtime) của fpm-builder
docker-compose logs -f fpm-builder

# Last 50 lines
docker-compose logs -n 50

# Xem logs từ 5 phút trước
docker-compose logs --since 5m

# View resource usage
docker stats

# Check container health
docker-compose ps
```

### Debug & Development

```bash
# Enter shell của builder
docker-compose exec fpm-builder /bin/sh

# Run command trong container
docker-compose exec fpm-builder mvn --version

# View image layers
docker history fpm-libraries:1.0.0

# Inspect container
docker inspect <container_id>

# View networks
docker network ls
docker network inspect fpm-network
```

### Cleanup & Space Management

```bash
# Stop + remove containers
docker-compose down

# Stop + remove + delete volumes
docker-compose down -v

# Remove images
docker rmi fpm-libraries:1.0.0

# Remove all unused images/containers
docker system prune -a

# Check disk usage
docker system df
```

---

## 🔧 Troubleshooting Quick Fixes

### Docker không chạy
```powershell
# Windows
Start-Service Docker
# Hoặc mở Docker Desktop từ Start Menu
```

### Port đang được dùng
```bash
# Tìm process (port 3306)
netstat -ano | findstr :3306

# Kill process (Windows)
taskkill /PID <PID> /F

# Hoặc thay port trong docker-compose.yml
```

### Build fail - Maven error
```bash
# Xóa cache
docker-compose down -v
docker volume prune -a

# Rebuild
docker build --no-cache -t fpm-libraries:1.0.0 .
```

### Container crash
```bash
# Xem lỗi chi tiết
docker compose logs fpm-builder

# Chạy manual để debug
docker run -it fpm-libraries:1.0.0 /bin/sh
```

---

## 🌐 Accessing Services

Các service chạy trên ports:

| Service | URL | Credentials |
|---------|-----|-------------|
| MySQL | `localhost:3306` | root / root_password |
| RabbitMQ | `amqp://localhost:5672` | guest / guest |
| RabbitMQ UI | `http://localhost:15672` | guest / guest |
| Redis | `redis://localhost:6379` | password: redis_password |
| pgAdmin | `http://localhost:5050` | admin@example.com / admin |
| Kafka | `localhost:9092` | N/A |
| Zookeeper | `localhost:2181` | N/A |

---

## 📝 Environment Variables

Cấu hình các biến trong `docker-compose.yml`:

```yaml
environment:
  JAVA_OPTS: "-Xmx1024m -Xms512m"
  MAVEN_OPTS: "-Xmx1024m"
  MYSQL_ROOT_PASSWORD: "root_password"
  POSTGRES_PASSWORD: "postgres_password"
```

---

## 🔐 Security Checklist

- [ ] Non-root user (UID 1000) trong image
- [ ] Minimal base image (alpine)
- [ ] No secrets in Dockerfile
- [ ] Use environment variables cho configs
- [ ] Health checks enabled
- [ ] Regular image updates
- [ ] Scan images với `trivy` hoặc `docker scout`

---

## 🚀 CI/CD Triggers

### Automatic CI
```yaml
# Tự động chạy khi:
- Push to main/develop
- Pull Request
- Scheduled (nếu config)
```

### Manual Release
```bash
# Create & push tag
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin v1.0.0

# Tự động trigger release.yml workflow
```

---

## 📊 Performance Tips

| Tip | Benefit |
|-----|---------|
| Use `.dockerignore` | Decrease build context |
| Cache Maven/Gradle | Faster rebuild |
| Multi-stage build | Smaller final image |
| Alpine base image | Smaller, lighter |
| BuildKit backend | Faster builds |

```bash
# Enable BuildKit (faster builds)
export DOCKER_BUILDKIT=1
docker build ...

# Or set in docker-compose
DOCKER_BUILDKIT=1 docker-compose build
```

---

## 🔗 Useful Links

- [Docker Documentation](https://docs.docker.com/)
- [Docker Best Practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
- [docker-compose Reference](https://docs.docker.com/compose/compose-file/)
- [GitHub Actions](https://docs.github.com/en/actions)

---

## 📞 Getting Help

**Detailed Guide:** See `docker-deployment/GUIDE.md`

```bash
# View help from scripts
.\docker-setup.ps1 help          # Windows
./docker-setup.sh help           # Linux/Mac
```

---

**Quick Tip:** Bookmark this page! 🔖

Version: 1.0.0 | Last Updated: March 2026
