# 📋 Action Plan - Docker & CI/CD Implementation

## Overview

Bạn sắp hoàn thành quá trình setup Docker và CI/CD cho FPM Project. Document này là bước-by-bước action plan để bạn thực hiện.

**Timeline:** 1-2 ngày  
**Difficulty:** Easy to Medium  
**Prerequisites:** 
- ✅ Build đã thành công (`mvn clean install`)
- ✅ Docker Desktop installed
- ✅ Git configured
- ✅ GitHub account

---

## Phase 1: Verify Current Setup (30 minutes)

### Phase 1a: Verify Build ✅

```powershell
# 1. Navigate to project
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# 2. Check Maven version
mvn --version
# Expected: Maven 3.8.6+, Java 21

# 3. Verify last build
mvn clean install -DskipTests
# Expected: BUILD SUCCESS

# 4. Check artifacts
dir fpm-libs\fpm-core\target\*.jar
dir fpm-libs\fpm-common\target\*.jar
# Should see JAR files

# ✅ If all good, proceed to Phase 1b
```

### Phase 1b: Verify Docker ✅

```powershell
# 1. Check Docker install
docker --version
# Expected: Docker version 20.10+

docker run hello-world
# Expected: "Hello from Docker!"

# 2. Check available disk space
# Windows: File Explorer → C: → Properties
# Need: 20GB+ free space

# 3. Verify Dockerfile exists
test-path Dockerfile
# Expected: True

# ✅ If all good, proceed to Phase 2
```

### Phase 1c: Verify Git ✅

```powershell
# 1. Check Git
git --version
# Expected: git version 2.3+

# 2. Check git config
git config --global user.name
git config --global user.email
# If empty:
git config --global user.name "Your Name"
git config --global user.email "your@email.com"

# 3. Check remote (if repo exists)
git remote -v
# If empty, no problem - we'll setup later

# ✅ Phase 1 complete!
```

---

## Phase 2: Build Docker Image Locally (45 minutes)

### Phase 2a: Build Image ✅

```powershell
# 1. Navigate to project root
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# 2. Clear any previous builds
docker image rm fpm-libraries:latest -f  # Ignore if not exists

# 3. Build Docker image
# This will take 5-10 minutes first time
docker build -t fpm-libraries:latest .

# Wait for it to complete...
# Expected last line: "👉 Successfully tagged fpm-libraries:latest"
```

**If build fails:** Check `SUA_LOI_DOCKER_CICD.md` → "Build Failures" section

### Phase 2b: Verify Image Created ✅

```powershell
# 1. List Docker images
docker images | findstr fpm

# Expected output:
# REPOSITORY           TAG      IMAGE ID       SIZE
# fpm-libraries        latest   abc123def456   650MB

# 2. Inspect image
docker image inspect fpm-libraries:latest | findstr -A 1 "RepoTags"

# Expected: RepoTags includes "fpm-libraries:latest"

# ✅ Image created successfully!
```

### Phase 2c: Test Image ✅

```powershell
# 1. Run container interactively
docker run -it --rm fpm-libraries:latest /bin/sh

# You should now be inside the container
# $ prompt appears

# 2. Verify Java
$ java -version
# Expected: openjdk version "21"

# 3. Verify Maven
$ mvn --version
# Expected: Apache Maven 3.x.x

# 4. List artifacts
$ find /app/fpm-libs -name "*.jar" -type f
# Expected: Multiple JAR files listed

# 5. Exit container
$ exit

# ✅ Container works!
```

---

## Phase 3: Docker Compose Setup (Optional, 30 minutes)

### Phase 3a: Start Services ✅

```powershell
# 1. Start docker-compose services
docker-compose up -d

# Wait for startup (30 seconds)

# 2. Check services status
docker-compose ps

# Expected:
# NAME            STATUS
# fpm-builder     Up 2 minutes
# mysql           Up 2 minutes
# rabbitmq        Up 2 minutes
# ... (other services)
```

### Phase 3b: Test Database Connection ✅

```powershell
# 1. Check MySQL is running
docker-compose ps mysql
# Expected: mysqld running

# 2. Test connection (from Windows)
mysql -h 127.0.0.1 -u fpm_user -pfpm_password fpm_dev

# If mysql client installed:
# mysql> SELECT 1;
# mysql> exit

# If no mysql client, that's ok - just verify running in ps

# 3. Check RabbitMQ management UI
# Open browser: http://localhost:15672
# Username: guest
# Password: guest
# Should see RabbitMQ dashboard
```

### Phase 3c: Stop Services ✅

```powershell
# When done testing
docker-compose down

# Expected:
# Stopping fpm-builder ... done
# Stopping mysql ... done
# ...

# (Services are stopped but data persists in volumes)

# ✅ Compose setup complete!
```

---

## Phase 4: Setup GitHub Repository (30 minutes)

### Phase 4a: Initialize Git Repository ✅

```powershell
# 1. Navigate to project (if not already there)
cd d:\WorkSpace\App_Dev\FPM_Project\libs

# 2. Check if git repo exists
git status
# If error "not a git repository", need to init:
git init

# 3. Configure git (if not done)
git config user.name "Your Name"
git config user.email "your@email.com"

# 4. Add all files
git add .

# 5. Create initial commit
git commit -m "Initial commit: Docker and CI/CD setup"

# ✅ Local git repo ready!
```

### Phase 4b: Create GitHub Repository ✅

```powershell
# 1. Go to GitHub.com
# - Login or create account

# 2. Create new repository
# - Click "+" → "New repository"
# - Name: "fpm-project" (or your choice)
# - Description: "FPM Libraries - Maven Multi-module Project"
# - Visibility: Public or Private
# - Skip "Initialize with README" (we have code already)
# - Click "Create repository"

# 3. You'll see a page with instructions like:
# git remote add origin https://github.com/YOUR-USERNAME/fpm-project.git
# git branch -M main
# git push -u origin main

# ✅ GitHub repo created!
```

### Phase 4c: Connect Local to GitHub ✅

```powershell
# 1. Add remote (use URL from GitHub page)
git remote add origin https://github.com/YOUR-USERNAME/fpm-project.git

# Replace YOUR-USERNAME with your GitHub username

# 2. Verify remote
git remote -v
# Expected:
# origin https://github.com/YOUR-USERNAME/fpm-project.git (fetch)
# origin https://github.com/YOUR-USERNAME/fpm-project.git (push)

# 3. Set branch to main (if needed)
git branch -M main

# 4. Push code to GitHub
git push -u origin main

# Wait for upload (might take 1-2 minutes)
# Expected: "✓ branch main set up to track origin/main"

# ✅ Code on GitHub!
```

---

## Phase 5: Verify GitHub Actions Setup (15 minutes)

### Phase 5a: Check Workflows Exist ✅

```powershell
# 1. Verify workflow files are in place
dir .github/workflows/

# Expected files:
# - build-and-test.yml
# - release.yml

# 2. Both files should be committed and pushed
git log --oneline .github/workflows/

# Should show commit message from Phase 4a
```

### Phase 5b: Monitor First Workflow Run ✅

```
1. Go to GitHub.com → Your-Repository
2. Click "Actions" tab (top navigation)
3. You should see a workflow run in progress:
   - "Initial commit: Docker and CI/CD setup"
   - Shows "in progress" or "✓ passed"

4. Wait for it to complete (5-10 minutes)
   - Green ✓ = Success
   - Red ✗ = Failed (check logs)

5. Click on the run to see details:
   - "Build and Test" job logs
   - "Build Docker Image" job logs
   - "Code Quality Check" job logs

Expected result: All jobs ✓ passed
```

**If any job fails:** Check `SUA_LOI_DOCKER_CICD.md` for solutions

### Phase 5c: Verify Docker Image Pushed ✅

```
1. Go to GitHub.com → Your-Profile (top right)
2. Click "Packages" 
3. You should see "fpm-libraries" package
4. Click it to see tags:
   - Branch names (main, develop, etc.)
   - Latest tag
   - SHA tags

Expected: Image successfully pushed to GitHub Container Registry (ghcr.io)
```

If not pushed, it might be normal for private repos - no issue yet.

---

## Phase 6: Create First Release (15 minutes)

### Phase 6a: Create Tag ✅

```powershell
# 1. Ensure you're on main branch with latest code
git checkout main
git pull origin main

# 2. Create annotated tag (recommended)
git tag -a v0.1.0 -m "Release version 0.1.0 - Initial release"

# 3. Push tag to GitHub
git push origin v0.1.0

# Expected: "To https://github.com/.../fpm-project.git
#            * [new tag]         v0.1.0 -> v0.1.0"

# ✅ Tag created and pushed!
```

### Phase 6b: Monitor Release Workflow ✅

```
1. Go to GitHub.com → Your-Repository → Actions
2. You should see new workflow running:
   - "Release - Maven Artifacts"
   - Shows "in progress" or "✓ passed"

3. Wait for completion (5-10 minutes)
   - Green ✓ = Success
   - Red ✗ = Failed (check logs)

4. Check results:
   - Go to Releases (GitHub → Releases tab)
   - Should see "Release v0.1.0"
   - Download links for artifacts (if any)
   
5. Check Docker image:
   - GitHub → Packages → fpm-libraries
   - Should see tag "v0.1.0"
   - Also tagged "latest"

Expected result: Release complete, Docker pushed with version tag
```

---

## Phase 7: Document & Learn (30 minutes)

### Read Documentation ✅

Now that everything is set up, deepen your understanding:

```
1. Read HUONG_DAN_TONG_HOP.md
   - Gives complete overview
   - Explains all components
   - Shows common workflows

2. Read HUONG_DAN_DOCKER.md
   - Deep dive into Docker
   - All Docker commands
   - Troubleshooting guide

3. Read HUONG_DAN_CICD.md
   - GitHub Actions explained
   - Workflow execution flow
   - Debugging workflows

4. Keep THAM_KHAO_NHANH.md as reference
   - Quick command lookup
   - Common patterns
   - Copy-paste ready

5. Use SUA_LOI_DOCKER_CICD.md when issues occur
   - Problem → Solution mapping
   - Step-by-step fixes
```

### Test Your Knowledge ✅

Try these exercises (10 minutes each):

**Exercise 1: Create Feature Branch**
```bash
git checkout -b feature/test-workflow
echo "# Test" >> README.md
git add .
git commit -m "Test commit"
git push origin feature/test-workflow

# Now create Pull Request on GitHub
# Watch workflow run tests automatically
```

**Exercise 2: Update Code and Watch Build**
```bash
git checkout main
echo "# Updated" >> README.md
git add .
git commit -m "Update main"
git push origin main

# Watch "build-and-test.yml" run
# See Docker image built and pushed
```

**Exercise 3: Create Second Release**
```bash
git tag -a v0.2.0 -m "Version 0.2.0 - Second release"
git push origin v0.2.0

# Watch "release.yml" workflow
# See new release created with v0.2.0 tag
```

---

## Phase 8: Setup & Learning (Ongoing)

### Best Practices to Follow ✅

```yaml
# 1. Branch naming
feature/TASK-ID-description   # New features
bugfix/TASK-ID-description    # Bug fixes  
hotfix/critical-fix           # Critical fixes

# 2. Commit messages
git commit -m "Add feature X"        # Good
git commit -m "WIPS"                 # Bad - unclear

# 3. Always work on feature branches
git checkout -b feature/new-feature  # Not main!

# 4. Create Pull Requests
# - Describe changes
# - Wait for CI/CD ✓
# - Code review
# - Then merge

# 5. Use releases for versions
git tag -a v1.0.0 -m "Version 1.0.0"
# Only tags matching "v*" trigger release workflow

# 6. Keep Docker image optimized
# - Check size: docker images | grep fpm
# - Should be around 600-800MB
```

### Recommended Next Steps ✅

**Short term (this week):**
- [ ] Create 2-3 feature branches and PRs
- [ ] Monitor workflows and fix any issues
- [ ] Create v0.2.0 release
- [ ] Read all documentation guides
- [ ] Practice Docker commands

**Medium term (this month):**
- [ ] Setup deployment pipeline (optional)
- [ ] Add more tests
- [ ] Optimize Docker image
- [ ] Create team-specific guidelines
- [ ] Setup branch protection rules

**Long term (ongoing):**
- [ ] Monitor build times (optimize if slow)
- [ ] Update dependencies regularly
- [ ] Check GitHub security alerts
- [ ] Maintain documentation
- [ ] Share knowledge with team

---

## Phase 9: Troubleshooting (As needed)

### If Something Goes Wrong

1. **Check error message carefully**
   - Read full error, not just last line

2. **Search documentation**
   - `SUA_LOI_DOCKER_CICD.md` → find your error
   - `HUONG_DAN_DOCKER.md` → Docker help
   - `HUONG_DAN_CICD.md` → Workflows help

3. **Try local reproduction**
   ```bash
   # For build issues
   cd fpm-libs
   mvn clean install
   
   # For Docker issues
   docker build -t fpm:test .
   docker run -it fpm:test /bin/sh
   ```

4. **Check logs**
   ```bash
   # GitHub Actions logs
   # GitHub.com → Actions → Your workflow → Job logs
   
   # Docker logs
   docker logs container-name
   docker-compose logs -f service-name
   ```

5. **When requesting help, provide:**
   - Error message (full, not screenshot)
   - Command you ran
   - Output from failing command
   - System info: `docker --version`, `java -version`

---

## Checklist - Mark as You Complete

### Phase 1: Verify Current Setup
- [ ] Build verified with Maven (`mvn clean install`)
- [ ] Docker Desktop running
- [ ] Git configured with name and email
- [ ] Dockerfile exists in project root

### Phase 2: Build Docker Image
- [ ] Docker image built successfully
- [ ] Image listed in `docker images`
- [ ] Container runs with shell access
- [ ] Java 21 verified in container
- [ ] JAR artifacts verified in `/app/fpm-libs`

### Phase 3: Docker Compose (Optional)
- [ ] Services started with `docker-compose up -d`
- [ ] All services show in `docker-compose ps`
- [ ] Database connection tested
- [ ] RabbitMQ management UI accessible
- [ ] Services stopped with `docker-compose down`

### Phase 4: GitHub Repository
- [ ] Git repository initialized locally
- [ ] GitHub repository created
- [ ] Remote configured with `git remote add origin`
- [ ] Code pushed to GitHub
- [ ] `.github/workflows/` directory on GitHub

### Phase 5: GitHub Actions
- [ ] First workflow run completed
- [ ] All jobs passed (green ✓)
- [ ] Docker image pushed to ghcr.io
- [ ] Image visible in GitHub Packages

### Phase 6: Release
- [ ] Release tag created (`git tag -a v0.1.0`)
- [ ] Tag pushed to GitHub
- [ ] Release workflow completed
- [ ] Release visible on GitHub Releases page
- [ ] Docker image tagged with version

### Phase 7: Learning
- [ ] Read HUONG_DAN_TONG_HOP.md
- [ ] Read HUONG_DAN_DOCKER.md
- [ ] Read HUONG_DAN_CICD.md
- [ ] Have THAM_KHAO_NHANH.md bookmarked
- [ ] Know where to find SUA_LOI_DOCKER_CICD.md

### Phase 8: Best Practices
- [ ] Understand Git branching strategy
- [ ] Know CI/CD workflow triggers
- [ ] Can create feature branch and PR
- [ ] Can create release tag
- [ ] Can read and understand workflow logs

---

## Quick Start (Fastest Path)

Already set up Docker/Git correctly?  
Skip to Phase 5 straight:

```powershell
# Assuming source already built and git initialized:

# 1. Create GitHub repo
# (Go to GitHub.com, create new repo, copy commands)

# 2. Add remote and push
git remote add origin https://github.com/YOUR-USERNAME/fpm-project.git
git push -u origin main

# 3. Create release tag
git tag -a v1.0.0 -m "Release"
git push origin v1.0.0

# 4. Monitor GitHub Actions
# (Go to GitHub.com → Actions tab)

# Done! Workflows should run automatically
```

---

## Success Criteria

You're done when:

✅ Docker image builds successfully  
✅ Container runs without errors  
✅ Code is on GitHub  
✅ GitHub Actions workflows trigger on push  
✅ Tests run automatically  
✅ Docker image pushes to registry  
✅ Release creates with version tag  
✅ You understand the workflow  

---

## Need Help?

Check these in order:

1. **Error message?**  
   → `SUA_LOI_DOCKER_CICD.md` (search for error)

2. **How to use Docker?**  
   → `HUONG_DAN_DOCKER.md`

3. **How do workflows work?**  
   → `HUONG_DAN_CICD.md`

4. **Quick command lookup?**  
   → `THAM_KHAO_NHANH.md`

5. **Complete overview?**  
   → `HUONG_DAN_TONG_HOP.md`

---

**You got this! 🚀 Follow the phases step by step, and you'll have a professional CI/CD pipeline in no time.**

---

## Document History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2025-03 | Initial action plan with Vietnamese guide |

---

**Last Updated:** March 2025  
**Status:** Ready for implementation  
**Estimated Time:** 2-3 hours total  

Good luck! 🎉
