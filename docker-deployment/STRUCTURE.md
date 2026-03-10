# 🔍 Docker & CI/CD Files Structure

## Cấu Trúc Hoàn Chỉnh

```
FPM_Project/
├── .github/
│   └── workflows/
│       ├── build-and-test.yml     ← Tự động build & test (push/PR)
│       └── release.yml             ← Tự động publish (tag release)
│
├── libs/                           ← Main project folder
│   ├── Dockerfile                  ← Docker build config
│   ├── docker-compose.yml          ← All services config
│   ├── .dockerignore               ← Exclude from Docker context
│   ├── docker-setup.ps1            ← Windows helper script
│   ├── docker-setup.sh             ← Linux/Mac helper script
│   │
│   ├── docker-deployment/          ← Documentation folder
│   │   ├── README.md               ← Quick navigation
│   │   ├── GUIDE.md                ← Complete guide (THIS IS IT!)
│   │   └── STRUCTURE.md            ← This file
│   │
│   ├── fpm-libs/                   ← Source code
│   │   ├── pom.xml
│   │   ├── fpm-bom/
│   │   ├── fpm-common/
│   │   ├── fpm-core/
│   │   └── ...
│   │
│   └── docker/                     ← Config files for services
│       ├── mysql/
│       │   └── init.sql
│       └── rabbitmq/
│           └── rabbitmq.conf
```

## 📄 Chi Tiết Files

### CI/CD Files (`.github/workflows/`)

#### `build-and-test.yml`
- **Kích hoạt:** Push to `main`/`develop`, Pull Request
- **Làm gì:** Build Java → Test → Build Docker image
- **Output:** Maven artifacts, Build report

#### `release.yml`
- **Kích hoạt:** Tag `v*` (ví dụ: `v1.0.0`)
- **Làm gì:** Build → Push to GitHub Container Registry (ghcr.io)
- **Output:** Published Docker image

### Docker Files (`libs/`)

#### `Dockerfile`
- Multi-stage build
- Stage 1 (Builder): Build Maven project
- Stage 2 (Runtime): Copy artifacts, setup non-root user
- Final image: ~400MB

#### `docker-compose.yml`
Services:
- `fpm-builder` - Build container (main)
- `mysql` - Database (v8.4.0)
- `rabbitmq` - Message broker
- `redis` - Cache
- `pgadmin` - DB management UI (optional)
- `kafka` - Message streaming (optional)
- `zookeeper` - Kafka coordinator (optional)

#### `.dockerignore`
Loại trừ files không cần (giảm image size):
- `.git/`
- `target/`
- `node_modules/`
- `.env`
- etc.

### Helper Scripts

#### `docker-setup.ps1` (Windows)
Commands:
```
.\docker-setup.ps1 setup          # Full setup
.\docker-setup.ps1 build          # Just build image
.\docker-setup.ps1 start          # Start services
.\docker-setup.ps1 logs           # View logs
.\docker-setup.ps1 clean          # Remove resources
```

#### `docker-setup.sh` (Linux/Mac)
Commands:
```
./docker-setup.sh setup           # Full setup
./docker-setup.sh build           # Just build image
./docker-setup.sh compose-up      # Start services
./docker-setup.sh logs            # View logs
./docker-setup.sh clean           # Remove resources
```

### Documentation

#### `docker-deployment/GUIDE.md` ⭐
**MAIN FILE** - 🔥 **BẮT ĐẦU TỪÂY!**
- Quick start (3 phút)
- Chi tiết từng bước
- Docker Compose guide
- CI/CD setup
- Troubleshooting
- Security best practices

#### `docker-deployment/README.md`
- Navigation guide
- Quick checklist
- File structure overview

## 🔄 Workflow Mô Phỏng

### Local Development
```
1. Code → 2. Git push → 3. GitHub Actions runs
   ↓
4. Maven build → 5. Tests run → 6. Docker image builds
   ↓
7. Success! → Artifact stored in GitHub
```

### Release Publication
```
1. Create tag: git tag -a v1.0.0
   ↓
2. Push tag: git push origin v1.0.0
   ↓
3. GitHub Actions (release.yml) triggered
   ↓
4. Build → Push to ghcr.io
   ↓
5. Image available: ghcr.io/your-org/fpm-libraries:v1.0.0
```

## 🎯 Quick Links

| Cần làm gì | File | Xem phần |
|-----------|------|---------|
| Bắt đầu ngay | GUIDE.md | Quick Start |
| Setup từng bước | GUIDE.md | Chi Tiết Từng Bước |
| Config docker-compose | docker-compose.yml | Services section |
| Fix lỗi | GUIDE.md | Troubleshooting |
| Setup GitHub CI/CD | build-and-test.yml, release.yml | Xem nội dung file |
| Windows setup | docker-setup.ps1 | Chạy: `.\docker-setup.ps1 --help` |
| Linux/Mac setup | docker-setup.sh | Chạy: `./docker-setup.sh help` |

---

**Chú ý:** Tất cả files CI/CD đã được tạo và ready to use! 🚀

Version: 1.0.0 | Last Updated: March 2026
