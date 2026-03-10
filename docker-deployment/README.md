# 📁 Docker Deployment Documentation

Folder này chứa tất cả các file hướng dẫn và cấu hình liên quan đến Docker và CI/CD cho dự án FPM Libraries.

## 📋 Nội Dung Folder

### 📚 Hướng Dẫn (Guides)
- **GUIDE.md** - **START HERE!** 📍 Hướng dẫn chi tiết hoàn chỉnh từ A-Z

### 🔧 Cấu Hình Docker (Root Level)
Các file dưới đây nằm ở thư mục gốc `libs/`:
- `Dockerfile` - Cấu hình Docker build (multi-stage)
- `docker-compose.yml` - Cấu hình tất cả services (MySQL, RabbitMQ, Redis, v.v.)
- `.dockerignore` - File/folder cần loại trừ khi build

### 🛠️ Helper Scripts (Root Level)
- `docker-setup.sh` - Script tự động cho Linux/Mac
- `docker-setup.ps1` - Script tự động cho Windows PowerShell

### 🔄 CI/CD Workflows (GitHub Actions)
Các file dưới đây nằm ở `.github/workflows/`:
- `build-and-test.yml` - Tự động build & test khi push code
- `release.yml` - Tự động publish image khi tạo release tag

---

## 🚀 Bắt Đầu Nhanh

### Cho Windows
```powershell
cd d:\WorkSpace\App_Dev\FPM_Project\libs
.\docker-setup.ps1 setup
```

### Cho Linux/Mac
```bash
cd /path/to/FPM_Project/libs
chmod +x docker-setup.sh
./docker-setup.sh setup
```

### Dùng Docker Commands trực tiếp
```bash
cd d:\WorkSpace\App_Dev\FPM_Project\libs
docker build -t fpm-libraries:1.0.0 .
docker-compose up -d
```

---

## 📖 Luồng Quyết Định

```
START HERE?
    ↓
→ Đọc GUIDE.md (5 phút)
    ↓
→ Chọn OS của bạn:
    ├─ Windows? Chạy: .\docker-setup.ps1 setup
    ├─ Linux/Mac? Chạy: ./docker-setup.sh setup
    └─ Thích commands? Xem GUIDE.md - Quick Start section
    ↓
→ Chạy: docker-compose up -d
    ↓
→ Kiểm tra: docker-compose ps
    ↓
DONE! 🎉
```

---

## 🔍 Tìm Gì?

| Nhu cầu | Xem tại |
|--------|---------|
| **Hướng dẫn chi tiết A-Z** | GUIDE.md |
| **Error troubleshooting** | GUIDE.md - Troubleshooting section |
| **Chạy nhanh (Windows)** | `.\docker-setup.ps1 setup` |
| **Chạy nhanh (Linux/Mac)** | `./docker-setup.sh setup` |
| **Setup CI/CD GitHub** | GUIDE.md - CI/CD Pipeline Setup |
| **Danh sách services** | GUIDE.md - Services table |
| **Security best practices** | GUIDE.md - Security Best Practices |

---

## ✅ Checklist Trước Khi Chạy

- [ ] Docker Desktop cài + chạy
- [ ] Git repo clone/update
- [ ] Terminal ở thư mục `libs/`
- [ ] File này có sẵn: `.github/workflows/build-and-test.yml`

---

## 📞 Hỗ Trợ

Gặp lỗi? 👀 Xem phần **Troubleshooting** trong GUIDE.md

---

**Version:** 1.0.0 | **Last Updated:** March 2026
