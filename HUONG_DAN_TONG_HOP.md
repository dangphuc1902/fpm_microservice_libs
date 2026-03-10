# 📚 Hướng Dẫn Tổng Hợp: Docker & CI/CD cho FPM Project

## Quick Navigation
- [Tổng Quan](#tổng-quan)
- [Quick Start (5 phút)](#quick-start-5-phút)
- [Detailed Guides](#detailed-guides)
- [Workflow Scenarios](#workflow-scenarios)
- [Troubleshooting](#troubleshooting)
- [FAQ](#faq)

---

## Tổng Quan

### Project Structure
```
FPM Project = Java Maven Library (Multi-module)
├── Build → ✅ Thành công (bạn vừa hoàn thành)
├── Docker → 🆕 Đóng gói vào image
├── CI/CD → 🆕 Tự động build & test
└── Deployment → Deploy anywhere
```

### Technology Stack
- **Language:** Java 21
- **Build:** Maven 3.8+
- **Container:** Docker
- **CI/CD:** GitHub Actions
- **Registry:** GitHub Container Registry (ghcr.io)

---

## Quick Start (5 phút)

### Step 1: Verify Build Success ✅
```powershell
cd d:\WorkSpace\App_Dev\FPM_Project\libs
mvn --version              # Verify Maven
java -version              # Verify Java 21
ls fpm-libs/fpm-core/target/*.jar  # Verify artifacts
```

### Step 2: Build Docker Image 🐳
```powershell
# Build Docker image
docker build -t fpm-libraries:latest .

# Verify image created
docker images | grep fpm-libraries
```

**Expected output:**
```
REPOSITORY         TAG      IMAGE ID       SIZE
fpm-libraries      latest   abc123...      650MB
```

### Step 3: Run Container
```powershell
# Run container
docker run -it --rm fpm-libraries:latest /bin/sh

# Inside container, verify artifacts
$ ls /app/fpm-libs/*/target/*.jar
$ exit
```

### Step 4: Start Services (Optional)
```powershell
# Start all services (build + MySQL + RabbitMQ + Redis)
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f fpm-builder

# Stop services
docker-compose down
```

### Step 5: Setup GitHub Actions
```powershell
# 1. Initialize git (if not already)
git init
git add .
git commit -m "Initial commit with Docker & CI/CD setup"
git remote add origin https://github.com/YOUR-USERNAME/fpm-project.git
git push -u origin main

# 2. Workflows automatically detected (.github/workflows/)
# GitHub Actions → Check Actions tab
```

**✅ Done! You now have:**
- ✓ Docker image built locally
- ✓ Container running
- ✓ CI/CD workflows ready
- ✓ Automatic build on push

---

## Detailed Guides

### For Docker Users
📖 Read: `HUONG_DAN_DOCKER.md`
- Build images
- Run containers
- Docker Compose setup
- Volume mounting
- Debugging

### For CI/CD Users
📖 Read: `HUONG_DAN_CICD.md`
- Understand workflows
- GitHub Actions basics
- Build triggers
- Release management
- Troubleshooting

---

## Workflow Scenarios

### Scenario A: Daily Development

**Day 1: Feature Development**
```bash
# Start feature
git checkout -b feature/auth-service

# Make changes
# ... edit code ...
git commit -am "Implement OAuth2"
git push origin feature/auth-service

# GitHub Actions:
# ❌ No trigger (not main/develop branch)
# (But you can test manually with docker build)
```

**Day 2: Create Pull Request**
```bash
# Create PR via GitHub UI
# feature/auth-service → main

# GitHub Actions:
# ✅ Trigger: build-and-test.yml
# ✅ Run: Build → Test (no Docker push)
# 📊 Show results on PR

# If tests fail:
# - Fix locally: git commit
# - Push: This triggers workflow again
# - Workflow re-runs automatically
```

**Day 3: Merge to Main**
```bash
# Approve & merge PR via GitHub UI
git remove-remote-tracking feature/auth-service

# GitHub Actions:
# ✅ Trigger: build-and-test.yml
# ✅ Run: Build → Test → Docker build & push
# 🐳 Docker image pushed to ghcr.io
# ✅ Available as: ghcr.io/your-repo/.../fpm-libraries:develop
```

### Scenario B: Create Release

**Timeline: When ready for release**
```bash
# 1. Ensure everything in main is released
git checkout main
git pull origin main

# 2. Create tag
git tag -a v1.0.0 -m "Release version 1.0.0"

# 3. Push tag
git push origin v1.0.0

# GitHub Actions:
# ✅ Trigger: release.yml
# ✅ Build + Create release notes
# 🐳 Release Docker image with v1.0.0 tag
# ✅ Available as: 
#    - ghcr.io/your-repo/.../fpm-libraries:v1.0.0
#    - ghcr.io/your-repo/.../fpm-libraries:latest
```

### Scenario C: Hotfix

```bash
# 1. Create hotfix branch từ main
git checkout -b hotfix/critical-bug main

# 2. Fix + test locally
git commit -am "Fix critical bug"
docker build -t fpm-libraries:hotfix .
docker run -it --rm fpm-libraries:hotfix /bin/sh

# 3. Create PR
git push origin hotfix/critical-bug
# Create PR via GitHub UI

# 4. Merge after review
git checkout main
git merge hotfix/critical-bug
git push origin main
git tag -a v1.0.1 -m "Hotfix"
git push origin v1.0.1

# GitHub Actions:
# ✅ Test automatically
# 🐳 Release automatically with v1.0.1
```

---

## Development Workflow - Step by Step

### Week 1: Setup & Verify

**Day 1 - Setup**
```powershell
# 1. Clone/setup project
cd .\fpm-project\
git init
git add .
git commit -m "Init project"

# 2. Verify build works locally
mvn clean install
echo "✅ Build successful"

# 3. Verify Docker works
docker build -t fpm-libraries:dev .
echo "✅ Docker image created"

# 4. Verify containers run
docker run -it --rm fpm-libraries:dev /bin/sh
echo "✅ Container runs"
exit

# 5. Setup git + GitHub
git remote add origin https://github.com/YOUR-REPO.git
git push -u origin main
echo "✅ Code on GitHub"
```

**Day 2 - CI/CD Verification**
```powershell
# 1. Make small change
echo "# Updated README" >> README.md

# 2. Push to GitHub
git add .
git commit -m "Update README"
git push origin main

# 3. Monitor GitHub Actions
# - Go to GitHub.com → Your-Repo → Actions tab
# - See workflow running
# - Wait for ✅ success or ❌ failure

# 4. Create release
git tag -a v0.1.0 -m "Initial release"
git push origin v0.1.0

# 5. Monitor release workflow
# - Actions tab shows release.yml running
# - Check Docker image pushed: ghcr.io/.../fpm-libraries:v0.1.0
```

### Week 2-4: Normal Development

```bash
# Each task:
git checkout -b feature/TASK-ID-description

# Work on feature
# ... multiple commits ...

# Push feature branch
git push origin feature/TASK-ID-description

# Create Pull Request (GitHub UI)
# - Automatic test runs
# - Wait for ✅ green status
# - Code review
# - Merge when approved

# After merge:
# - CI/CD auto builds & pushes Docker

# When releasing:
git tag -a v0.2.0 -m "Version 0.2.0"
git push origin v0.2.0
# - Automatic release workflow runs
```

---

## Key Files Explanation

### Build System Files

| File | Purpose | When to Modify |
|------|---------|----------------|
| `fpm-libs/pom.xml` | Maven root config | Add dependencies, plugins |
| `fpm-libs/*/pom.xml` | Module config | Module-specific settings |
| `Dockerfile` | Container image spec | Change base image, build steps |
| `.dockerignore` | Files to skip in image | Add unnecessary files |

### Workflow Files

| File | Purpose | When to Modify |
|------|---------|----------------|
| `.github/workflows/build-and-test.yml` | CI for push/PR | Change triggers, add steps |
| `.github/workflows/release.yml` | CD for releases | Change version format |
| `docker-compose.yml` | Local dev environment | Add services (DB, cache, etc) |

---

## Troubleshooting

### Problem: Docker build fails with "Maven error"

**Solution:**
```powershell
# 1. Try locally first
cd fpm-libs
mvn clean install -DskipTests

# 2. If works locally but fails in Docker:
#    - Docker has less memory
#    - Either increase Docker memory or increase MAVEN_OPTS
#    - Edit Dockerfile or docker-compose.yml

# 3. Force rebuild without cache
docker build --no-cache -t fpm-libraries:dev .
```

### Problem: Port already in use (docker-compose)

**Solution:**
```powershell
# 1. Find what's using port
netstat -ano | findstr ":3306"

# 2. Either kill that process or change port in docker-compose.yml
# ports:
#   - "3307:3306"  ← Change 3306 to 3307

docker-compose up -d
docker-compose ps
```

### Problem: GitHub workflow not triggering

**Check:**
1. Is repository on GitHub? (not local)
2. Is `.github/workflows/` folder pushed?
3. Is branch name correct? (main, develop)

```powershell
# Verify git remote
git remote -v

# List branches
git branch -a

# Force push
git push origin main --force
```

### Problem: Docker image not pushed to registry

**Check:**
```powershell
# 1. Is token available?
# Settings → Secrets → Check GITHUB_TOKEN exists

# 2. Did you login?
docker login ghcr.io -u YOUR-USERNAME -p <GITHUB-TOKEN>

# 3. Check workflow:
# Must be on main branch push OR tag push
git log --oneline | head -1
git branch

# 4. Check result:
# GitHub.com → Your-Repo → Packages → your-image
```

---

## FAQ

### Q: Do I need Docker installed locally?

**A:** No, but recommended for:
- Testing before push
- Understanding how container works
- Debugging issues

GitHub Actions runs Docker in cloud (doesn't need your local Docker).

### Q: What's GITHUB_TOKEN?

**A:** Automatic token created by GitHub for CI/CD.
- Can pull code ✓
- Can push images ✓
- Can create releases ✓
- Expires after job finishes

No need to set up manually.

### Q: Can I use Docker Hub instead of ghcr.io?

**A:** Yes! Modify workflow:
```yaml
env:
  REGISTRY: docker.io
  IMAGE_NAME: your-username/fpm-libraries

# Also add secret: DOCKERHUB_TOKEN
docker/login-action@v3:
  username: ${{ secrets.DOCKERHUB_USERNAME }}
  password: ${{ secrets.DOCKERHUB_PASSWORD }}
```

### Q: How to access private image?

**A:** Use image pull secret in Kubernetes:
```yaml
---
kind: Secret
apiVersion: v1
metadata:
  name: ghcr-secret
type: kubernetes.io/dockercfg
data:
  .dockercfg: <BASE64-ENCODED-CREDENTIALS>
```

Or for local Docker:
```bash
docker login ghcr.io
# Then docker pull ghcr.io/your-repo/.../fpm-libraries:latest
```

### Q: How to skip CI/CD for certain commits?

**A:** Add to commit message:
```bash
git commit -m "Fix typo

[skip ci]  ← GitHub Actions skips this commit"
```

Or:
```bash
git commit -m "Update docs [skip actions]"
```

### Q: Can I run local tests before push?

**A:** Yes!
```bash
cd fpm-libs
mvn clean install              # Full build + test
mvn test                       # Just tests
mvn test -Dtest=TestClass     # Single test class
```

### Q: How to release multiple versions?

**A:** Create multiple tags:
```bash
git tag -a v1.0.0 -m "Version 1.0.0"
git tag -a v1.0.1 -m "Version 1.0.1"
git tag -a v1.1.0 -m "Version 1.1.0"

git push origin --tags  # Push all tags
```

Each tag triggers release workflow.

### Q: Is Docker image automatically deployed?

**A:** No, you need to:
1. ✅ Build → automatic via CI/CD
2. 🐳 Push to registry → automatic
3. 🚀 Deploy to cloud → manual (Azure, AWS, Kubernetes)

For deployment, need additional setup (ArgoCD, Helm, Kubernetes, etc).

---

## Environment Variables

### For builds
- `JAVA_OPTS` - Java memory (-Xmx1024m)
- `MAVEN_OPTS` - Maven memory (-Xmx1024m)
- `MAVEN_CONFIG` - Maven config directory

### GitHub Actions (Auto-provided)
- `GITHUB_TOKEN` - Authentication token
- `GITHUB_SHA` - Commit hash
- `GITHUB_REF` - Branch/tag reference
- `GITHUB_ACTOR` - Username

### For images
- `REGISTRY` - Container registry (ghcr.io)
- `IMAGE_NAME` - Image name (fpm-libraries)

---

## Performance Tips

### 1. Speed up Maven builds
```xml
<!-- pom.xml -->
<properties>
    <maven.compiler.fork>true</maven.compiler.fork>
    <maven.compiler.maxmem>1024m</maven.compiler.maxmem>
</properties>
```

### 2. Docker build caching
```dockerfile
# Copy dependency first (more caching)
COPY pom.xml ./
RUN mvn dependency:resolve

# Then copy source (slow layer cached)
COPY src ./
RUN mvn compile
```

### 3. Skip unnecessary steps in CI/CD
```yaml
- name: 'Skip if only docs changed'
  id: file-changes
  uses: dorny/paths-filter@v2
  with:
    filters: |
      code:
        - '!(README.md|docs/**)'

- name: 'Build'
  if: steps.file-changes.outputs.code == 'true'
  run: mvn clean install
```

---

## Next Steps

### Immediate (This Week)
- [ ] Build Docker image locally: `docker build -t fpm-libraries:dev .`
- [ ] Run container: `docker run -it --rm fpm-libraries:dev /bin/sh`
- [ ] Push code to GitHub
- [ ] Monitor first workflow run

### Short Term (This Month)
- [ ] Create first release tag: `git tag -a v1.0.0`
- [ ] Monitor release workflow
- [ ] Setup Kubernetes deployment (optional)
- [ ] Setup monitoring/logging (optional)

### Long Term (Ongoing)
- [ ] Optimize Docker image size
- [ ] Add more tests
- [ ] Setup deployment pipeline
- [ ] Document deployment procedures

---

## Support Resources

### Local Testing
```bash
# Test build locally
cd fpm-libs && mvn clean install

# Test Docker
docker build -t fpm-libraries:test .
docker run -it --rm fpm-libraries:test /bin/sh

# Test compose
docker-compose up -d mysql
docker exec mysql mysql -u root -proot fpm_dev
```

### View Logs
```bash
# GitHub Actions logs
# Web: GitHub.com → Actions → Your workflow

# Docker logs
docker logs -f container-name

# Docker compose logs
docker-compose logs -f service-name
```

### Useful Commands
```bash
# Check Git status
git status
git log --oneline -10

# List tags
git tag -l

# View remote
git remote -v

# Check Docker
docker images
docker ps -a
docker logs
```

---

## Summary

✅ **You now have:**
- Docker image (Dockerfile)
- CI/CD automation (GitHub Actions)
- Automated testing (Maven tests)
- Automated Docker builds (docker/build-push-action)
- Release management (version tags)

✅ **Whenever you:**
- Push to main/develop → Automatic build + Docker push
- Create tag v* → Automatic release workflow
- Create PR → Automatic test before merge

✅ **Benefits:**
- 🚀 Faster development (automatic checks)
- 🔒 Better quality (tests before merge)
- 📦 Easy distribution (Docker)
- 📚 Easy learning (workflows documented)

**Now you can focus on code, let CI/CD handle the rest! 🎉**

---

## Document History

| Date | Version | Changes |
|------|---------|---------|
| 2024 | 1.0 | Initial guide |
| 2025-03 | 2.0 | Enhanced with Vietnamese translations |

---

**Happy building! 🚀**
