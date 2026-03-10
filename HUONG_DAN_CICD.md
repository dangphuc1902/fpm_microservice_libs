# 🚀 Hướng Dẫn GitHub Actions CI/CD Chi Tiết

## Mục Lục
1. [CI/CD là gì?](#cicd-là-gì)
2. [GitHub Actions Basics](#github-actions-basics)
3. [Build & Test Workflow](#build--test-workflow)
4. [Release Workflow](#release-workflow)
5. [Cách Sử Dụng](#cách-sử-dụng)
6. [Debugging & Troubleshooting](#debugging--troubleshooting)
7. [Best Practices](#best-practices)

---

## CI/CD là gì?

### Khái Niệm

**CI = Continuous Integration (Tích hợp liên tục)**
- ✅ Mỗi khi push code → Tự động build
- ✅ Tự động chạy tests
- ✅ Phát hiện lỗi sớm

**CD = Continuous Deployment (Triển khai liên tục)**
- ✅ Build thành công → Tự động package (Docker)
- ✅ Tự động release
- ✅ Tự động deploy (nếu cần)

### Workflow của FPM Project

```
┌─────────────────────────────────────────┐
│  Developer Push Code                    │
│  Commit → GitHub                        │
└────────────────┬────────────────────────┘
                 │
         ┌───────▼────────┐
         │ GitHub Actions │
         │ Workflows Run  │
         └───────┬────────┘
                 │
    ┌────────────┴─────────────┐
    │                          │
┌───▼──────────┐        ┌──────▼─────┐
│ 1. Build     │        │ 2. Test    │
│ Maven build  │        │ Run tests  │
│ Compile      │        │ Generate   │
│ Package      │        │ Report     │
└───┬──────────┘        └──────┬─────┘
    │                          │
    └────────────┬─────────────┘
                 │
         ┌───────▼──────────┐
         │ 3. Docker Build  │
         │ Build image      │
         │ Push to registry │
         └───────┬──────────┘
                 │
         ┌───────▼──────────┐
         │ ✅ Success       │
         │ Artifacts ready  │
         └──────────────────┘
```

---

## GitHub Actions Basics

### Là Gì?

GitHub Actions là CI/CD platform tích hợp trong GitHub:
- 🏃 Chạy workflows tự động dựa trên events
- 📁 Workflows lưu trong `.github/workflows/` (YAML format)
- 🆓 Free cho public repositories
- 🔐 Secure (built-in secrets management)

### Anatomy của Một Workflow

```yaml
# .github/workflows/build-and-test.yml

name: 'Build and Test - FPM Libraries'  # Tên workflow (hiển thị trên GitHub)

# TRIGGERS - Khi nào chạy workflow này?
on:
  push:                    # Trigger on push
    branches:
      - main
      - develop
  pull_request:            # Trigger on PR
    branches:
      - main
      - develop

# ENVIRONMENT VARIABLES - Dùng chung bởi tất cả jobs
env:
  REGISTRY: ghcr.io                          # Container registry
  IMAGE_NAME: fpm-libraries

# JOBS - Các công việc chạy
jobs:
  build:                                     # Job 1: Build
    name: 'Build and Test'
    runs-on: ubuntu-latest                   # Run trên Ubuntu
    timeout-minutes: 30                      # Timeout sau 30 phút
    steps:                                   # Các steps trong job
      - name: 'Checkout code'
        uses: actions/checkout@v4            # Custom action (có sẵn)
        
      - name: 'Set up Java 21'
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: maven                       # Cache Maven dependencies
          
      - name: 'Build with Maven'
        run: |
          cd fpm-libs
          mvn clean install -DskipTests -q
        env:
          MAVEN_OPTS: '-Xmx1024m'

  docker-build:                              # Job 2: Docker Build
    name: 'Build Docker Image'
    runs-on: ubuntu-latest
    needs: build                             # Chỉ chạy khi Job 1 thành công
    steps:
      - name: 'Build and push Docker image'
        uses: docker/build-push-action@v5
```

### Key Concepts

| Concept | Meaning |
|---------|---------|
| **Workflow** | File YAML định nghĩa tất cả tự động hóa (.yml file) |
| **Event** | Trigger workflow (push, pull_request, schedule, manual dispatch) |
| **Job** | Một runtime unit độc lập, có thể chạy song song |
| **Step** | Công việc nhỏ trong job (chạy tuần tự) |
| **Action** | Reusable unit code (like function) |
| **Secrets** | Sensitive data (API keys, tokens, passwords) |

---

## Build & Test Workflow

### File: `.github/workflows/build-and-test.yml`

**Location:** `d:\WorkSpace\App_Dev\FPM_Project\libs\.github\workflows\build-and-test.yml`

### Triggers

```yaml
on:
  push:
    branches:
      - main        # Chạy khi push vào main
      - develop     # Chạy khi push vào develop
  pull_request:
    branches:
      - main        # Chạy khi mở PR vào main
      - develop     # Chạy khi mở PR vào develop
```

**Tình huống:**
```
❌ KHÔNG trigger:
- Push vào feature branch (e.g., feature/auth)
- Push vào local test branch
- Commit history rewrite

✅ TRIGGER:
- git push origin feature/auth:main           (push vào main)
- git push origin feature/auth:develop        (push vào develop)
- Create PR from feature → main/develop       (create PR)
- Push commit vào PR branch (automatic)
```

### Job 1: Build and Test

**Purpose:** Compile, package, test code

```yaml
jobs:
  build:
    name: 'Build and Test'
    runs-on: ubuntu-latest        # Chạy trên Ubuntu runner (GitHub provided)
    timeout-minutes: 30           # Stop nếu vượt 30 phút

    steps:
      # Step 1: Checkout code từ repository
      - name: 'Checkout code'
        uses: actions/checkout@v4
        with:
          fetch-depth: 0            # Fetch full history (cho analysis)

      # Step 2: Setup Java 21
      - name: 'Set up Java 21'
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'   # OpenJDK distribution
          cache: maven              # Cache ~/.m2/repository (speed up)

      # Step 3: Build Maven project
      - name: 'Build with Maven (skip tests)'
        run: |
          cd fpm-libs
          mvn clean install -DskipTests -q
        env:
          MAVEN_OPTS: '-Xmx1024m'   # Java heap size

      # Step 4: Run tests
      - name: 'Run tests'
        run: |
          cd fpm-libs
          mvn test
        env:
          MAVEN_OPTS: '-Xmx1024m'

      # Step 5: Generate test report
      - name: 'Generate test report'
        if: always()                # Chạy ngay cả khi bước trước fail
        uses: dorny/test-reporter@v1
        with:
          name: 'Maven Test Results'
          path: 'fpm-libs/**/target/surefire-reports/TEST-*.xml'
          reporter: 'java-junit'
          fail-on-error: false      # Không fail workflow nếu report fail
```

**What happens:**
1. ✅ Checkout source code
2. ✅ Install Java 21
3. ✅ Run `mvn clean install` (compile + package)
4. ✅ Run `mvn test` (execute unit tests)
5. ✅ Parse test reports format JUnit
6. ✅ Display test results trên GitHub (summary + details)

**Output:**
- ✅ Compiled classes (/target/classes)
- ✅ Packaged JARs (fpm-libs/*/target/*.jar)
- ✅ Test reports (./target/surefire-reports/TEST-*.xml)

### Job 2: Code Quality Check

**Purpose:** Kiểm tra security vulnerabilities, dependencies

```yaml
  code-quality:
    name: 'Code Quality Check'
    runs-on: ubuntu-latest
    needs: build              # Chạy sau build job
    timeout-minutes: 20

    steps:
      # Build again to generate dependencies
      - name: 'Check CVE vulnerabilities'
        run: |
          cd fpm-libs
          mvn clean install -DskipTests
        
      # Check for outdated dependencies
      - name: 'Run dependency check'
        run: |
          cd fpm-libs
          mvn dependency:check-updates
        continue-on-error: true      # Không fail workflow
```

### Job 3: Docker Build and Push

**Purpose:** Build Docker image, push to registry

```yaml
  docker-build:
    name: 'Build Docker Image'
    runs-on: ubuntu-latest
    needs: build                    # Chạy sau build job thành công
    timeout-minutes: 20

    steps:
      - name: 'Checkout code'
        uses: actions/checkout@v4

      # Setup Docker buildx (advanced builder)
      - name: 'Set up Docker Buildx'
        uses: docker/setup-buildx-action@v3

      # Login to container registry
      - name: 'Log in to Container Registry'
        uses: docker/login-action@v3
        with:
          registry: ${{ env.REGISTRY }}   # ghcr.io
          username: ${{ github.actor }}   # GitHub username
          password: ${{ secrets.GITHUB_TOKEN }}  # Auto provided

      # Extract version/tags từ git
      - name: 'Extract metadata'
        id: meta
        uses: docker/metadata-action@v5
        with:
          images: ${{ env.REGISTRY }}/${{ github.repository }}/fpm-libraries
          tags: |
            type=semver,pattern={{version}}        # v1.0.0 → 1.0.0
            type=semver,pattern={{major}}.{{minor}}  # v1.0.0 → 1.0
            type=ref,event=branch                   # Branch name
            type=sha,prefix={{branch}}-             # Git commit SHA
            type=raw,value=latest,enable={{is_default_branch}}  # main → latest

      # Build and push Docker image
      - name: 'Build and push Docker image'
        uses: docker/build-push-action@v5
        with:
          context: .                    # Build context = project root
          push: ${{ github.event_name == 'push' && github.ref == 'refs/heads/main' }}
          # ☝️ Push only on main branch push (not PR)
          tags: ${{ steps.meta.outputs.tags }}      # Multi tags
          labels: ${{ steps.meta.outputs.labels }}
          cache-from: type=gha           # Use GitHub cache
          cache-to: type=gha,mode=max    # Save cache
```

**Important:**
- ❌ Docker image NOT pushed on PR (security)
- ✅ Docker image pushed on main/develop push
- 🚀 Uses cache → builds faster

---

## Release Workflow

### File: `.github/workflows/release.yml`

**Location:** `d:\WorkSpace\App_Dev\FPM_Project\libs\.github\workflows\release.yml`

### Trigger

```yaml
on:
  push:
    tags:
      - 'v*'      # Any tag starting with "v"
                  # v1.0.0, v1.0.1, v2.0.0-beta
```

### Release Job

```yaml
jobs:
  release:
    name: 'Release Maven Artifacts'
    runs-on: ubuntu-latest
    timeout-minutes: 30

    steps:
      # Build project again
      - name: 'Build release'
        run: |
          cd fpm-libs
          mvn clean install -DskipTests -q

      # Create GitHub Release with notes
      - name: 'Create GitHub Release'
        uses: actions/create-release@v1
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          tag_name: ${{ github.ref }}           # Tag name (v1.0.0)
          release_name: Release ${{ github.ref_name }}
          body: |
            ## Release Notes
            - Maven Modules Built Successfully
            - All tests passed
            - Docker image: ghcr.io/.../fpm-libraries:v1.0.0
          draft: false
          prerelease: false
```

### Docker Release Job

```yaml
  docker-release:
    name: 'Release Docker Image'
    runs-on: ubuntu-latest
    needs: release
    timeout-minutes: 20

    steps:
      # Login to registry
      - name: 'Log in to Container Registry'
        uses: docker/login-action@v3
        with:
          registry: ${{ env.REGISTRY }}
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      # Extract version từ tag
      - name: 'Extract version from tag'
        id: version
        run: echo "version=${GITHUB_REF#refs/tags/}" >> $GITHUB_OUTPUT

      # Build and push with version tags
      - name: 'Build and push Docker image'
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true                    # Always push for release
          tags: |
            ${{ env.REGISTRY }}/${{ github.repository }}/fpm-libraries:${{ steps.version.outputs.version }}
            ${{ env.REGISTRY }}/${{ github.repository }}/fpm-libraries:latest
```

---

## Cách Sử Dụng

### Scenario 1: Normal Development

```bash
# Developer A: Tạo feature branch
git checkout -b feature/new-auth
# ... Làm việc ...
git commit -am "Add new auth"
git push origin feature/new-auth

# GitHub Actions:
# ❌ Không trigger (không phải main/develop branch)

# ===========================

# Create Pull Request (GitHub UI)
# GitHub Actions:
# ✅ Trigger build-and-test workflow
# ✅ Chạy: Build → Test → Code Quality
# ✅ Docker build (NOT pushed to registry)
# ✅ Show results on PR page
# ✅ Green ✓ hoặc Red ✗ status

# Code review through, merge PR
git checkout develop
git merge feature/new-auth
git push origin develop

# GitHub Actions:
# ✅ Trigger build-and-test workflow
# ✅ Build → Test → Docker build + push
# ✅ Docker image pushed to ghcr.io as tagged
```

### Scenario 2: Creating a Release

```bash
# Khi sẵn sàng release (e.g., version 1.0.0)

# 1. Create release tag
git tag -a v1.0.0 -m "Release version 1.0.0"

# 2. Push tag to GitHub
git push origin v1.0.0

# GitHub Actions:
# ✅ Trigger release.yml workflow
# ✅ Build project with version
# ✅ Create GitHub Release page
# ✅ Build + push Docker image with version tags
# ✅ Docker available as:
#    - ghcr.io/.../.../fpm-libraries:v1.0.0
#    - ghcr.io/.../.../fpm-libraries:latest
```

### View Workflow Results

**Method 1: GitHub Website**
1. Go to repository GitHub page
2. Click **Actions** tab
3. See all workflow runs
4. Click one to view details

**Method 2: Local Terminal**
```bash
# List recent workflow runs
git log --oneline -10

# View workflow status in browser
open https://github.com/your-repo/actions
```

**Output Example:**
```
✅ build-and-test (just now)
   ✓ Build and Test (5m 23s)
   ✓ Build Docker Image (3m 12s)
   ✓ Code Quality Check (2m 45s)

✅ release (10m ago)
   ✓ Release Maven Artifacts (5m 10s)
   ✓ Release Docker Image (4m 35s)
```

---

## GitHub Actions Context Variables

### Built-in Variables

```yaml
${{ github.event_name }}           # push, pull_request, schedule
${{ github.ref }}                  # refs/heads/main, refs/tags/v1.0.0
${{ github.ref_name }}             # main, develop, v1.0.0
${{ github.repository }}           # owner/repo
${{ github.actor }}                # username thực hiện event
${{ github.sha }}                  # commit SHA
${{ secrets.GITHUB_TOKEN }}        # Auto-provided token
${{ env.REGISTRY }}                # Custom env var
```

### Outputs từ Steps

```yaml
steps:
  - name: 'Extract version'
    id: version           # ← Step ID
    run: echo "version=1.0.0" >> $GITHUB_OUTPUT

  - name: 'Use version'
    run: echo "Version is ${{ steps.version.outputs.version }}"
    # ☝️ Access như: ${{ steps.STEP_ID.outputs.OUTPUT_NAME }}
```

---

## Debugging & Troubleshooting

### Enable Debug Logging

Add repository secret:
- Name: `ACTIONS_STEP_DEBUG`
- Value: `true`

**Or** in workflow file:
```yaml
env:
  ACTIONS_STEP_DEBUG: true
```

Then rerun workflow → More verbose logs

### Common Workflow Issues

### ❌ "Build failed - Maven error"

**Debug steps:**
1. Check Job log for error message
2. Look for specific error ("OutOfMemory", "dependency not found", etc.)
3. Can reproduce locally:
```bash
cd fpm-libs
mvn clean install -DskipTests
```

### ❌ "Docker push failed - auth error"

**Solution:**
```yaml
# Verify secrets exist in Settings → Secrets
# GITHUB_TOKEN should auto-exist (added by GitHub)

# If custom registry, add secret:
# Settings → Secrets → New repository secret
# Name: DOCKERHUB_TOKEN
# Value: <your token>
```

### ❌ "Workflow stuck/timeout"

```
Default timeout: 30 minutes (configurable in job)

timeout-minutes: 60    # Increase timeout
```

**Optimize:**
- Use caching (Maven, Docker)
- Parallelize jobs (when possible)
- Review slow steps in logs

### ❌ "Tags not created / Docker not pushed"

**Check:**
1. Is tag in correct format? (v1.0.0, not 1.0.0)
2. Did you push tag? `git push origin v1.0.0`
3. Is branch correct? (main/develop)

```bash
# List your tags
git tag -l

# Verify tag pushed
git ls-remote --tags origin | grep v1.0.0
```

### View Past Runs

```bash
# CLI approach (install GitHub CLI first)
gh workflow list
gh run list
gh run view <RUN_ID>
gh run logs <RUN_ID>
```

---

## Best Practices

### 1. Use Caching

```yaml
# ✅ GOOD - Cache Maven dependencies
- name: 'Set up Java'
  uses: actions/setup-java@v4
  with:
    java-version: '21'
    cache: maven              # ← Caches ~/.m2/repository

# ✅ GOOD - Cache Docker layers
- name: 'Build and push Docker'
  uses: docker/build-push-action@v5
  with:
    cache-from: type=gha
    cache-to: type=gha,mode=max
```

### 2. Security

```yaml
# ✅ GOOD - Never log secrets
- name: 'Build'
  run: |
    mvn -settings settings.xml ...
  env:
    DB_PASSWORD: ${{ secrets.DB_PASSWORD }}  # Safe

# ❌ BAD - Exposing secrets
- name: 'Build'
  run: echo "Password is ${{ secrets.DB_PASSWORD }}"  # Logged!
```

### 3. Parallel Jobs

```yaml
# ✅ GOOD - Run tests in parallel
jobs:
  unit-tests:
    runs-on: ubuntu-latest
  
  integration-tests:
    runs-on: ubuntu-latest

  docker-build:
    needs: [unit-tests, integration-tests]  # Wait for both
```

### 4. Conditional Steps

```yaml
steps:
  - name: 'Deploy to production'
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    run: deploy.sh

  - name: 'Skip on PR'
    if: github.event_name != 'pull_request'
    run: echo "Not on PR"
```

### 5. Notifications

Add job summary:
```yaml
- name: 'Job Summary'
  run: |
    echo "## Build Summary" >> $GITHUB_STEP_SUMMARY
    echo "- Build: ✅ Passed" >> $GITHUB_STEP_SUMMARY
    echo "- Tests: ✅ Passed (125 tests)" >> $GITHUB_STEP_SUMMARY
    echo "- Docker: ✅ Pushed to registry" >> $GITHUB_STEP_SUMMARY
```

### 6. Version Strategy

Sử dụng semantic versioning:
```
v1.0.0  - Major.Minor.Patch
  ↑ Breaking change
    ↑ New feature
      ↑ Bug fix

Release tags: v1.0.0, v1.0.1, v1.1.0, v2.0.0
```

---

## Cheat Sheet - Common Commands

```bash
# ========== Create Release ==========
git tag -a v1.0.0 -m "Release message"
git push origin v1.0.0

# ========== View Workflow ==========
gh workflow list
gh run list
gh run view <run-id> --log

# ========== Workflow Troubleshooting ==========
git log --oneline -5                    # Recent commits
git tag -l                              # List tags
git ls-remote --tags origin             # Tags on remote

# ========== Local Debug Build ==========
cd fpm-libs
mvn clean install -DskipTests
docker build -t fpm-libraries:test .
```

---

## Next Steps

✅ Understand CI/CD workflows  
👉 Tiếp theo: 
1. Setup GitHub Personal Access Token (if needed)
2. Create first release tag: `git tag -a v1.0.0`
3. Monitor workflow on GitHub Actions tab
4. Read `HUONG_DAN_TONG_HOP.md` for complete guide
