# 📚 Complete Documentation Archive

Folder này chứa **copies** của tất cả files Docker & CI/CD documentation từ dự án để tham khảo offline.

## 📋 Files Archive

### Original Files (Nằm ở Root Level)

| File | Vị trí | Mục đích |
|------|--------|---------|
| `Dockerfile` | `libs/` | Docker build config (multi-stage) |
| `docker-compose.yml` | `libs/` | Services configuration |
| `.dockerignore` | `libs/` | Exclude files from Docker context |
| `docker-setup.ps1` | `libs/` | Windows helper script |
| `docker-setup.sh` | `libs/` | Linux/Mac helper script |
| `build-and-test.yml` | `.github/workflows/` | GitHub Actions workflow |
| `release.yml` | `.github/workflows/` | Release & publish workflow |

### Reference Files (Trong folder này)

| File | Mục đích |
|------|---------|
| `README.md` | Quick navigation |
| `GUIDE.md` | Complete setup guide (MAIN) |
| `QUICK_COMMANDS.md` | Common commands reference |
| `STRUCTURE.md` | File structure overview |
| `ARCHIVE.md` | Tài liệu này |

---

## 🔗 File Relationships

```
┌─────────────────────────────────────────────────────┐
│              FPM Project Structure                  │
└─────────────────────────────────────────────────────┘

Root: FPM_Project/
│
├── .github/
│   └── workflows/
│       ├── ✨ build-and-test.yml      (CI/CD Build)
│       └── ✨ release.yml             (CI/CD Release)
│
└── libs/
    ├── ✨ Dockerfile                  (Docker Config)
    ├── ✨ docker-compose.yml          (Services)
    ├── ✨ .dockerignore               (Exclude)
    ├── ✨ docker-setup.ps1            (Windows Helper)
    ├── ✨ docker-setup.sh             (Linux/Mac Helper)
    │
    ├── docker-deployment/             ← DOCUMENTATION
    │   ├── README.md                  (Start here)
    │   ├── GUIDE.md                   (Complete guide)
    │   ├── QUICK_COMMANDS.md          (Commands ref)
    │   ├── STRUCTURE.md               (File structure)
    │   └── ARCHIVE.md                 (This file)
    │
    └── fpm-libs/                      (Source code)
        ├── pom.xml
        ├── fpm-bom/
        ├── fpm-common/
        ├── fpm-core/
        └── ...
```

**✨ = Critical files untuk Docker deployment**

---

## 🎯 Reading Order

### 1️⃣ First Time Setup
1. Read: `docker-deployment/README.md` (2 min)
2. Read: `docker-deployment/GUIDE.md` - Quick Start section (5 min)
3. Run: `.\docker-setup.ps1 setup` hoặc `./docker-setup.sh setup`
4. Check: `docker-compose ps`

### 2️⃣ Development & Debugging
1. Reference: `docker-deployment/GUIDE.md` - Chi Tiết Từng Bước
2. When issues: Check `docker-deployment/GUIDE.md` - Troubleshooting
3. Common commands: `docker-deployment/QUICK_COMMANDS.md`

### 3️⃣ CI/CD Setup
1. Read: `docker-deployment/GUIDE.md` - CI/CD Pipeline Setup
2. Copy files từ `.github/workflows/` to your GitHub repo
3. Configure: Docker Hub credentials (nếu cần)

---

## 📄 File Contents at a Glance

### GUIDE.md 🌟
**Most Important File!**
```
├── Yêu Cầu Hệ Thống
├── Quick Start
│   ├── Windows
│   ├── Linux/Mac
│   └── Docker Commands
├── Chi Tiết Từng Bước
│   ├── Chuẩn Bị Dự Án
│   ├── Build Maven
│   ├── Build Docker
│   ├── Kiểm Tra Image
│   ├── Chạy Container
│   └── Docker Compose
├── Docker Build & Run
├── CI/CD Pipeline Setup
│   ├── GitHub Actions
│   ├── GitLab CI
│   └── Secrets Setup
├── Troubleshooting
├── Monitoring & Logs
├── Security Best Practices
└── Checklist & Resources
```

### QUICK_COMMANDS.md 
```
├── 3 Cách Chạy (Quick Start)
├── Common Commands Table
├── Logs & Monitoring
├── Debug & Development
├── Cleanup
├── Troubleshooting Fixes
├── Service URLs & Credentials
├── Environment Variables
├── Security Checklist
├── CI/CD Triggers
├── Performance Tips
└── Resources
```

### STRUCTURE.md
```
├── Complete File Structure
├── 📄 Files Detail
│   ├── CI/CD Files
│   ├── Docker Files
│   ├── Helper Scripts
│   └── Documentation
├── Workflow Diagram
└── Quick Links Table
```

---

## 🔄 Document Aging Policy

| Document | Last Updated | Review Frequency |
|----------|--------------|-----------------|
| GUIDE.md | March 2026 | Quarterly |
| QUICK_COMMANDS.md | March 2026 | As-needed |
| STRUCTURE.md | March 2026 | Annually |

---

## ✅ Verification Commands

### Verify all files exist
```bash
# Check Docker files
test -f Dockerfile && echo "✓ Dockerfile"
test -f docker-compose.yml && echo "✓ docker-compose.yml"

# Check helper scripts
test -f docker-setup.ps1 && echo "✓ docker-setup.ps1"
test -f docker-setup.sh && echo "✓ docker-setup.sh"

# Check workflows
test -f .github/workflows/build-and-test.yml && echo "✓ build-and-test.yml"
test -f .github/workflows/release.yml && echo "✓ release.yml"

# Check documentation
test -f docker-deployment/GUIDE.md && echo "✓ GUIDE.md"
test -f docker-deployment/README.md && echo "✓ README.md"
test -f docker-deployment/QUICK_COMMANDS.md && echo "✓ QUICK_COMMANDS.md"
```

### Quick Test
```bash
# 1. Build image
docker build -t fpm-libraries:test .

# 2. Run test
docker run --rm fpm-libraries:test java -version

# 3. Check services can start
docker-compose up -d mysql
docker-compose ps
docker-compose down
```

---

## 🚀 Next Actions

- [ ] Read `docker-deployment/GUIDE.md`
- [ ] Run Quick Start section
- [ ] Test `docker build` command
- [ ] Start `docker-compose up -d`
- [ ] Check `docker-compose ps`
- [ ] For CI/CD: Setup GitHub workflows
- [ ] For production: Configure Docker Hub credentials

---

## 📞 When You Need Help

| Issue | Where to Look |
|-------|---------------|
| How to get started? | `GUIDE.md` - Quick Start |
| Specific command? | `QUICK_COMMANDS.md` |
| Error troubleshooting? | `GUIDE.md` - Troubleshooting section |
| File structure? | `STRUCTURE.md` |
| Docker best practices? | `GUIDE.md` - Security Best Practices |
| GitHub Actions setup? | `GUIDE.md` - CI/CD Pipeline Setup |

---

**Navigation:**
- 🏠 Home: `README.md`
- 📖 Main Guide: `GUIDE.md`
- ⚡ Quick Ref: `QUICK_COMMANDS.md`
- 📁 Structure: `STRUCTURE.md`

**Version:** 1.0.0 | **Last Updated:** March 2026 | **Status:** Production Ready ✅
