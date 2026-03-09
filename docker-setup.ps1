# FPM Libraries - Docker & CI/CD Quick Start Script (PowerShell)
# This script automates the Docker setup for local development on Windows

param(
    [Parameter(Position=0)]
    [string]$Command = "help"
)

# Error handling
$ErrorActionPreference = "Stop"

# Colors
$Colors = @{
    Green  = "`e[32m"
    Blue   = "`e[34m"
    Yellow = "`e[33m"
    Red    = "`e[31m"
    Reset  = "`e[0m"
}

function Write-Header {
    param([string]$Message)
    Write-Host "`n$($Colors.Blue)==================================================$($Colors.Reset)"
    Write-Host "$($Colors.Blue)$Message$($Colors.Reset)"
    Write-Host "$($Colors.Blue)==================================================$($Colors.Reset)`n"
}

function Write-Success {
    param([string]$Message)
    Write-Host "$($Colors.Green)✓ $Message$($Colors.Reset)"
}

function Write-Error-Custom {
    param([string]$Message)
    Write-Host "$($Colors.Red)✗ $Message$($Colors.Reset)" -ForegroundColor Red
    exit 1
}

function Write-Info {
    param([string]$Message)
    Write-Host "$($Colors.Yellow)ℹ $Message$($Colors.Reset)"
}

# Check prerequisites
Write-Header "Checking Prerequisites"

try {
    docker --version | Out-Null
    Write-Success "Docker is installed"
} catch {
    Write-Error-Custom "Docker is not installed. Please install Docker Desktop first."
}

try {
    docker-compose --version | Out-Null
    Write-Success "Docker Compose is installed"
} catch {
    Write-Error-Custom "Docker Compose is not installed. Please install Docker Compose first."
}

try {
    git --version | Out-Null
    Write-Success "Git is installed"
} catch {
    Write-Error-Custom "Git is not installed. Please install Git first."
}

try {
    docker ps | Out-Null
    Write-Success "Docker daemon is running"
} catch {
    Write-Error-Custom "Docker daemon is not running. Please start Docker Desktop."
}

# Process commands
switch ($Command.ToLower()) {
    "setup" {
        Write-Header "Setting up FPM Libraries"
        
        Write-Info "Building Docker image..."
        docker build -t fpm-libraries:latest .
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Docker build failed" }
        Write-Success "Docker image built successfully"
        
        Write-Info "Starting services with Docker Compose..."
        docker-compose up -d postgres rabbitmq redis
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Docker Compose startup failed" }
        Write-Success "Services started successfully"
        
        Write-Info "Starting builder container..."
        docker-compose up -d fpm-builder
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Builder startup failed" }
        Write-Success "Builder container started"
        
        Write-Host "`n"
        Write-Header "Setup Complete!"
        Write-Host "Services are now running:"
        Write-Host "  - PostgreSQL      : postgres:5432 (user: fpm_user, pass: fpm_password)"
        Write-Host "  - RabbitMQ        : rabbitmq:5672 (UI: localhost:15672)"
        Write-Host "  - Redis           : redis:6379 (password: redis_password)"
        Write-Host "  - FPM Builder     : Building libraries...`n"
        Write-Info "To view builder logs: docker-compose logs -f fpm-builder"
    }
    
    "build" {
        Write-Header "Building Docker Image"
        docker build -t fpm-libraries:latest .
        if ($LASTEXITCODE -eq 0) {
            Write-Success "Image built: fpm-libraries:latest"
        } else {
            Write-Error-Custom "Docker build failed"
        }
    }
    
    "start" {
        Write-Header "Starting Services"
        docker-compose up -d
        if ($LASTEXITCODE -eq 0) {
            Write-Success "All services started"
            docker-compose ps
        } else {
            Write-Error-Custom "Failed to start services"
        }
    }
    
    "stop" {
        Write-Header "Stopping Services"
        docker-compose down
        if ($LASTEXITCODE -eq 0) {
            Write-Success "All services stopped"
        } else {
            Write-Error-Custom "Failed to stop services"
        }
    }
    
    "restart" {
        Write-Header "Restarting Services"
        docker-compose restart
        if ($LASTEXITCODE -eq 0) {
            Write-Success "All services restarted"
            docker-compose ps
        } else {
            Write-Error-Custom "Failed to restart services"
        }
    }
    
    "logs" {
        Write-Info "Showing logs (Press Ctrl+C to exit)"
        docker-compose logs -f
    }
    
    "clean" {
        Write-Header "Cleaning Docker Resources"
        Write-Info "This will remove containers, volumes, and images"
        $response = Read-Host "Are you sure? (y/N)"
        
        if ($response -eq 'y' -or $response -eq 'Y') {
            docker-compose down -v
            docker rmi fpm-libraries:latest -f 2>$null
            Write-Success "Cleanup completed"
        } else {
            Write-Info "Cleanup cancelled"
        }
    }
    
    "test-build" {
        Write-Header "Testing Docker Build"
        docker build --no-cache -t fpm-libraries:test .
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Test build failed" }
        Write-Success "Test build successful"
        docker run --rm fpm-libraries:test mvn --version
    }
    
    "publish-image" {
        Write-Header "Publishing Docker Image"
        $registry = Read-Host "Enter registry URL (e.g., ghcr.io/username)"
        $tag = Read-Host "Enter image tag (default: latest)"
        if ([string]::IsNullOrWhiteSpace($tag)) { $tag = "latest" }
        
        $imageName = "$registry/fpm-libraries:$tag"
        Write-Info "Tagging image: $imageName"
        
        docker tag fpm-libraries:latest "$imageName"
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Failed to tag image" }
        
        Write-Info "Pushing to registry..."
        docker push "$imageName"
        if ($LASTEXITCODE -eq 0) {
            Write-Success "Image published: $imageName"
        } else {
            Write-Error-Custom "Failed to push image"
        }
    }
    
    "full-setup" {
        Write-Header "Full Setup with Optional Services"
        
        Write-Info "Building Docker image..."
        docker build -t fpm-libraries:latest .
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Docker build failed" }
        Write-Success "Docker image built"
        
        Write-Info "Starting all services (including Kafka and pgAdmin)..."
        docker-compose --profile kafka --profile admin-tools up -d
        if ($LASTEXITCODE -ne 0) { Write-Error-Custom "Startup failed" }
        Write-Success "All services started"
        
        Write-Host "`n"
        Write-Header "Full Setup Complete!"
        Write-Host "Available services:"
        Write-Host "  - PostgreSQL       : postgres:5432"
        Write-Host "  - pgAdmin          : http://localhost:5050 (admin@example.com / admin)"
        Write-Host "  - RabbitMQ         : rabbitmq:5672 (UI: localhost:15672)"
        Write-Host "  - Redis            : redis:6379"
        Write-Host "  - Zookeeper        : zookeeper:2181"
        Write-Host "  - Kafka            : kafka:9092 (plaintext)`n"
        Write-Info "Run 'docker-compose ps' to see all services"
    }
    
    "help" {
        Write-Header "FPM Libraries - Docker Setup Help"
        Write-Host "Usage: .\docker-setup.ps1 [command]`n"
        Write-Host "Commands:"
        Write-Host "  setup              - Initial setup (build image + start containers)"
        Write-Host "  build              - Build Docker image locally"
        Write-Host "  start              - Start Docker Compose services"
        Write-Host "  stop               - Stop Docker Compose services"
        Write-Host "  restart            - Restart Docker Compose services"
        Write-Host "  logs               - View Docker Compose logs"
        Write-Host "  clean              - Clean up Docker resources"
        Write-Host "  test-build         - Test Docker build locally"
        Write-Host "  publish-image      - Push image to registry (requires auth)"
        Write-Host "  full-setup         - Complete setup with all optional services"
        Write-Host "  help               - Show this help message`n"
    }
    
    default {
        Write-Error-Custom "Unknown command: $Command"
    }
}
