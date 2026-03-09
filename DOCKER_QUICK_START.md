# FPM Libraries - Quick Docker Commands

## 🚀 Quick Start

### Build Docker Image
```bash
docker build -t fpm-libraries:latest .
```

### Run Container
```bash
docker run -it --rm fpm-libraries:latest /bin/sh
```

### View Build Artifacts
```bash
docker run --rm fpm-libraries:latest find /app/fpm-libs -name "*.jar" -type f
```

---

## 🔄 CI/CD Workflows

### Automatic Triggers

| Event | Workflow | Action |
|-------|----------|--------|
| Push to `main` | build-and-test.yml | Build, Test, Build Docker |
| Push to `develop` | build-and-test.yml | Build, Test, Build Docker |
| Pull Request | build-and-test.yml | Build, Test (no push) |
| Tag `v*` | release.yml | Build, Release, Push Docker |

### Manual Release

```bash
# Create release tag
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0

# This triggers automatic release workflow
```

---

## 📊 Files Created

| File | Purpose |
|------|---------|
| `Dockerfile` | Multi-stage Docker build configuration |
| `.dockerignore` | Exclude files from Docker build context |
| `.github/workflows/build-and-test.yml` | CI/CD for push and PR events |
| `.github/workflows/release.yml` | Release and publish workflow |
| `DOCKER_CICD_SETUP.md` | Complete setup documentation |

---

## ✅ Verify Setup

```bash
# 1. Check Dockerfile
test -f Dockerfile && echo "✓ Dockerfile exists"

# 2. Check workflows
test -f .github/workflows/build-and-test.yml && echo "✓ Build workflow exists"
test -f .github/workflows/release.yml && echo "✓ Release workflow exists"

# 3. Build image locally
docker build -t fpm-libraries:test . && echo "✓ Docker build successful"

# 4. Check image
docker image inspect fpm-libraries:test && echo "✓ Image created successfully"
```

---

## 🆘 Need Help?

See `DOCKER_CICD_SETUP.md` for:
- Detailed setup instructions
- Troubleshooting guide
- Security best practices
- Advanced configuration

