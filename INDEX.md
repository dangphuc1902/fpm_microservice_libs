# 📖 Documentation Index - Docker & CI/CD Guides

## 🎯 Quick Navigation by Use Case

### "I want to BUILD a Docker image RIGHT NOW"
→ [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#build-docker-image) (Section: Build Docker Image)

**Quick steps:**
```bash
docker build -t fpm-libraries:latest .
docker images | grep fpm
```

---

### "I want to RUN a Docker container"
→ [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#chạy-docker-container) (Section: Run Container)

**Quick steps:**
```bash
docker run -it --rm fpm-libraries:latest /bin/sh
```

---

### "I want to START all services (MySQL, RabbitMQ, etc.)"
→ [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#docker-compose) (Section: Docker Compose)

**Quick steps:**
```bash
docker-compose up -d
docker-compose ps
```

---

### "I want to understand CI/CD WORKFLOWS"
→ [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) (Complete guide)

**Start with:**
- [CI/CD là gì?](HUONG_DAN_CICD.md#cicd-là-gì)
- [GitHub Actions Basics](HUONG_DAN_CICD.md#github-actions-basics)

---

### "I want to CREATE a RELEASE"
→ [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md#release-workflow) & [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md#phase-6-create-first-release-15-minutes)

**Quick steps:**
```bash
git tag -a v1.0.0 -m "Release 1.0.0"
git push origin v1.0.0
```

---

### "Something is BROKEN, HELP!"
→ [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) (Troubleshooting guide)

**Find your error:**
1. Go to [Quick Issue Finder](SUA_LOI_DOCKER_CICD.md#quick-issue-finder)
2. Find your problem in the table
3. Click link to solution

---

### "I need quick COMMANDS reference"
→ [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) (Cheat sheet)

**Sections:**
- [Docker Commands](THAM_KHAO_NHANH.md#-docker---essential-commands)
- [Docker Compose Commands](THAM_KHAO_NHANH.md#-docker-compose---essential-commands)
- [Maven Commands](THAM_KHAO_NHANH.md#-maven---essential-commands)
- [Git Commands](THAM_KHAO_NHANH.md#-git---essential-commands)

---

### "I'm NEW, where do I START?"
→ [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) (Step-by-step action plan)

**Follow phases:**
1. Phase 1 - Verify Setup (30 min)
2. Phase 2 - Build Docker Image (45 min)
3. Phase 3 - Docker Compose (30 min, optional)
4. Phase 4 - Setup GitHub (30 min)
5. Phase 5 - Verify CI/CD (15 min)
6. Phase 6 - Create Release (15 min)

---

### "I want COMPLETE OVERVIEW"
→ [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) (Comprehensive guide)

**Covers:**
- Quick start (5 min)
- All workflows
- Best practices
- FAQ
- Next steps

---

## 📚 Document Descriptions

| Document | Purpose | Read Time | Level |
|----------|---------|-----------|-------|
| [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) | Step-by-step implementation plan | 20 min | Beginner |
| [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) | Complete overview of all components | 30 min | Beginner |
| [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Detailed Docker guide | 45 min | Beginner-Intermediate |
| [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | GitHub Actions & CI/CD workflows | 45 min | Beginner-Intermediate |
| [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) | Quick reference & cheat sheet | 10 min | All levels |
| [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Problem solving & troubleshooting | Variable | All levels |
| [INDEX.md](INDEX.md) | This file - documentation map | 5 min | All levels |

---

## 🎓 Learning Path

### For Developers (Want to contribute code)

**Recommended reading order:**
1. Start: [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) (understand setup)
2. Learn: [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) (get overview)
3. Deep dive: [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) → Scenarios section (understand workflow)
4. Reference: [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) (bookmark for daily use)

**Key learnings:**
- How to create feature branch
- How to create pull request
- How CI/CD automatically tests your code
- How to view test results on GitHub

---

### For DevOps/Infrastructure (Want to manage deployment)

**Recommended reading order:**
1. Start: [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) (understand containerization)
2. Learn: [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) (understand automation)
3. Deep dive: [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) → Performance section
4. Reference: [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) (bookmark for daily use)
5. Advanced: Setup Kubernetes deployment (not covered in these docs)

**Key learnings:**
- How Docker images are built
- How to optimize Docker image size
- How to setup deployment pipeline
- Performance tuning for CI/CD

---

### For DevOps/SRE (Monitoring & troubleshooting)

**Recommended reading order:**
1. Start: [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) (know what can go wrong)
2. Learn: [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) → Troubleshooting section
3. Learn: [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) → Debugging section
4. Reference: All guides (for context when debugging)

**Key learnings:**
- How to diagnose build failures
- How to read logs effectively
- How to resolve common issues
- Performance optimization

---

### For Team Leads/Architects

**Recommended reading order:**
1. Start: [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) (big picture)
2. Review: [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) → Overview section
3. Review: [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) → GitHub Actions Basics
4. Plan: [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) (timeline & phases)

**Key learnings:**
- Overall architecture
- CI/CD benefits and workflow
- Team onboarding plan
- Monitoring & alerting strategy

---

## 🔍 Find Documentation by Topic

### Docker Topics

| Topic | Document | Section |
|-------|----------|---------|
| Build image | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Build Docker Image |
| Run container | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Run Container |
| View logs | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Monitor Build |
| Mount volumes | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Volume Mount |
| Docker Compose | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Docker Compose |
| Cleanup | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) | Cleanup |
| Troubleshooting | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Docker Problems |

---

### CI/CD Topics

| Topic | Document | Section |
|-------|----------|---------|
| What is CI/CD | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | Concepts |
| Workflows | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | Workflow Basics |
| Build workflow | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | Build & Test |
| Release workflow | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | Release |
| GitHub Actions | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | GitHub Actions |
| Usage examples | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) | Scenarios |
| Troubleshooting | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Workflow Issues |

---

### Git & GitHub Topics

| Topic | Document | Section |
|-------|----------|---------|
| Git setup | [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) | Phase 4 |
| Create branch | [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) | Git Commands |
| Create tag | [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) | Git Commands |
| Push code | [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) | Development Workflow |
| Create release | [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md) | Phase 6 |

---

### Troubleshooting Topics

| Topic | Document | Section |
|-------|----------|---------|
| Build fails | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Build Failures |
| Docker issues | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Docker Problems |
| Container issues | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Runtime Issues |
| Port conflicts | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Port Conflicts |
| Workflow not running | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Workflow Triggers |
| Out of space | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Resource Issues |
| Slow builds | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) | Performance Issues |

---

## 📁 File Organization in Project

```
d:\WorkSpace\App_Dev\FPM_Project\libs\
│
├── 📄 Documentation Files (START HERE!)
│   ├── INDEX.md                       ← You are here
│   ├── KE_HOACH_HANH_DONG.md         ← Step-by-step plan
│   ├── HUONG_DAN_TONG_HOP.md         ← Complete guide
│   ├── HUONG_DAN_DOCKER.md           ← Docker deep dive
│   ├── HUONG_DAN_CICD.md             ← CI/CD deep dive
│   ├── THAM_KHAO_NHANH.md            ← Command reference
│   └── SUA_LOI_DOCKER_CICD.md        ← Troubleshooting
│
├── 🐳 Docker Files
│   ├── Dockerfile                    ← Build specification
│   ├── docker-compose.yml            ← Services config
│   └── .dockerignore                 ← Files to skip
│
├── 🚀 GitHub Actions
│   └── .github/workflows/
│       ├── build-and-test.yml        ← CI workflow
│       └── release.yml               ← Release workflow
│
├── 📦 Source Code
│   ├── fpm-libs/
│   │   ├── pom.xml                  ← Maven config
│   │   ├── fpm-core/
│   │   ├── fpm-common/
│   │   └── ... (other modules)
│   └── package.json
│
└── 📝 Configuration
    ├── pom.xml                      ← Root Maven config
    └── .gitignore
```

---

## 🚀 Common Tasks Guide

### Task: Build Docker image

**Time:** 5-10 minutes  
**Guide:** [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#build-docker-image)  
**Quick reference:** [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#build-images)

```bash
docker build -t fpm-libraries:latest .
```

---

### Task: Test code works in container

**Time:** 3-5 minutes  
**Guide:** [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#chạy-docker-container)  
**Quick reference:** [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#run-containers)

```bash
docker run -it --rm fpm-libraries:latest /bin/sh
```

---

### Task: Create feature and push to GitHub

**Time:** 5 minutes  
**Guide:** [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md#scenario-a-daily-development)  
**Quick reference:** [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#branches)

```bash
git checkout -b feature/my-feature
git add .
git commit -m "Implement feature"
git push origin feature/my-feature
# Then create PR on GitHub.com
```

---

### Task: Review test results

**Time:** 2 minutes  
**Guide:** [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md#view-workflow-results)

```
GitHub.com → Your-Repo → Actions → workflow-run → job-logs
```

---

### Task: Create release

**Time:** 2 minutes  
**Guide:** [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md#scenario-b-create-release) or [KE_HOACH_HANH_DONG.md](KE_HOACH_HANH_DONG.md#phase-6a-create-tag-)  
**Quick reference:** [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#tags--release)

```bash
git tag -a v1.0.0 -m "Release 1.0.0"
git push origin v1.0.0
```

---

### Task: Troubleshoot build failure

**Time:** Variable  
**Guide:** [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md#build-failures)

1. Find error in logs
2. Search [Quick Issue Finder](SUA_LOI_DOCKER_CICD.md#quick-issue-finder)
3. Follow solution

---

## 🔗 Cross-References

### Workflow Explanation

Check all three to understand complete flow:
1. [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md#workflow-scenarios) - High-level view
2. [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md#build--test-workflow) - Detailed steps
3. [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#-github-actions---essential-concepts) - YAML syntax

---

### Docker Troubleshooting

- Issue while building? → [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md#docker-problems)
- Command reference? → [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#-docker---essential-commands)
- Need to learn Docker? → [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md#overview)

---

### CI/CD Understanding

- New to CI/CD? → [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md#cicd-là-gì)
- Want to know workflows? → [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md#github-actions-basics)
- Need YAML syntax? → [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md#-github-actions---essential-concepts)

---

## 📞 Getting Help

### Step 1: Search Documentation

Use Ctrl+F to search all files for keywords:
- Error message
- Command name
- Concept name

### Step 2: Check Specific Document

| Question | Check First |
|----------|------------|
| "How do I...?" | [THAM_KHAO_NHANH.md](THAM_KHAO_NHANH.md) |
| "Why did...fail?" | [SUA_LOI_DOCKER_CICD.md](SUA_LOI_DOCKER_CICD.md) |
| "How do workflows work?" | [HUONG_DAN_CICD.md](HUONG_DAN_CICD.md) |
| "How does Docker work?" | [HUONG_DAN_DOCKER.md](HUONG_DAN_DOCKER.md) |
| "What's the full picture?" | [HUONG_DAN_TONG_HOP.md](HUONG_DAN_TONG_HOP.md) |

### Step 3: Check Related Sections

- Most guides have internal links
- Use browser back button to return
- Search "See also" sections

### Step 4: Community Resources

- GitHub Issues (your repo)
- Stack Overflow (tags: docker, maven, github-actions)
- GitHub Discussions (if enabled)

---

## 📊 Quick Stats

- **Total Guides:** 7 comprehensive guides
- **Total Content:** ~15,000 words
- **Code Examples:** 200+ examples
- **Troubleshooting:** 50+ solutions
- **Diagrams:** Mermaid flowcharts

---

## 🎯 Success Markers

You're making progress when:

✅ Can build Docker image  
✅ Can run container  
✅ Code on GitHub  
✅ Workflows auto-trigger  
✅ Understand CI/CD flow  
✅ Can create release  
✅ Can debug issues  

---

## 📖 Reading Estimated Times

| Document | Quick Read | Deep Read | Reference |
|----------|-----------|-----------|-----------|
| KE_HOACH_HANH_DONG.md | 10 min | 30 min | 🔄 Ongoing |
| HUONG_DAN_TONG_HOP.md | 15 min | 30 min | ✅ Once |
| HUONG_DAN_DOCKER.md | 20 min | 60 min | 🔄 As needed |
| HUONG_DAN_CICD.md | 20 min | 60 min | 🔄 As needed |
| THAM_KHAO_NHANH.md | 5 min | 15 min | 🔄 Daily |
| SUA_LOI_DOCKER_CICD.md | Variable | Variable | 🔄 Troubleshooting |
| INDEX.md (this file) | 5 min | 10 min | 🔄 Navigation |

---

## 💡 Pro Tips

1. **Bookmark this file** - It's your navigation hub
2. **Use Ctrl+F** to search across documents
3. **Follow links** - Documents reference each other
4. **Keep THAM_KHAO_NHANH.md** as quick reference
5. **Save SUA_LOI_DOCKER_CICD.md** for troubleshooting

---

## 🔄 Keep Updated

- Check documentation on major changes
- Update guides when you discover new patterns
- Share findings with your team
- Document lessons learned

---

**Happy learning! 🚀**

---

**This Index File Created:** March 2025  
**Last Updated:** March 2025  
**Status:** Ready to use

For the latest updates, check GitHub repository.
