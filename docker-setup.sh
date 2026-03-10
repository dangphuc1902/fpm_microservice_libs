#!/bin/bash
# FPM Libraries - Docker Setup Script for Linux/Mac
# Usage: ./docker-setup.sh [command]

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
PROJECT_NAME="fpm-libraries"
IMAGE_TAG="1.0.0"
DOCKERFILE="Dockerfile"

# Helper functions
print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_warn() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Check prerequisites
check_prerequisites() {
    print_info "Checking prerequisites..."
    
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed"
        echo "Download from: https://www.docker.com/products/docker-desktop"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed"
        exit 1
    fi
    
    if ! command -v git &> /dev/null; then
        print_warn "Git is not installed"
    fi
    
    print_success "All prerequisites checked"
}

# Build Docker image
build_image() {
    print_info "Building Docker image: $PROJECT_NAME:$IMAGE_TAG"
    docker build -t "$PROJECT_NAME:$IMAGE_TAG" -f "$DOCKERFILE" .
    print_success "Image built successfully"
    docker images | grep "$PROJECT_NAME"
}

# Run container
run_container() {
    local container_id
    print_info "Running Docker container..."
    container_id=$(docker run -d -it "$PROJECT_NAME:$IMAGE_TAG" /bin/sh)
    print_success "Container running: $container_id"
    docker logs "$container_id"
}

# Start docker-compose
start_compose() {
    print_info "Starting docker-compose services..."
    docker-compose up -d
    print_success "Services started"
    docker-compose ps
}

# Stop docker-compose
stop_compose() {
    print_info "Stopping docker-compose services..."
    docker-compose down
    print_success "Services stopped"
}

# View logs
view_logs() {
    print_info "Showing logs (Ctrl+C to exit)..."
    docker-compose logs -f "$1"
}

# Clean up
cleanup() {
    print_warn "Cleaning up Docker resources..."
    
    print_info "Removing containers..."
    docker-compose down -v || true
    
    print_info "Removing images..."
    docker rmi "$PROJECT_NAME:$IMAGE_TAG" || true
    
    print_success "Cleanup completed"
}

# Show help
show_help() {
    cat << EOF
${BLUE}FPM Libraries - Docker Setup Script${NC}

Usage: ./docker-setup.sh [COMMAND]

Commands:
  setup            Run complete setup (build + compose)
  check            Check prerequisites
  build            Build Docker image
  run              Run container
  compose-up       Start docker-compose services
  compose-down     Stop docker-compose services
  logs [service]   View logs (default: all)
  clean            Remove all Docker resources
  help             Show this help message

Examples:
  ./docker-setup.sh setup
  ./docker-setup.sh compose-up
  ./docker-setup.sh logs fpm-builder
  ./docker-setup.sh clean

EOF
}

# Main script logic
main() {
    case "${1:-help}" in
        setup)
            check_prerequisites
            build_image
            start_compose
            print_success "Setup completed!"
            ;;
        check)
            check_prerequisites
            ;;
        build)
            build_image
            ;;
        run)
            run_container
            ;;
        compose-up)
            start_compose
            ;;
        compose-down)
            stop_compose
            ;;
        logs)
            view_logs "${2:-.}"
            ;;
        clean)
            cleanup
            ;;
        help)
            show_help
            ;;
        *)
            print_error "Unknown command: $1"
            show_help
            exit 1
            ;;
    esac
}

# Run main function
main "$@"

# FPM Libraries - Docker & CI/CD Quick Start Script
# This script automates the Docker setup for local development

set -e

# Colors
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Functions
print_header() {
    echo -e "\n${BLUE}======================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}======================================${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
    exit 1
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

# Check prerequisites
print_header "Checking Prerequisites"

if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker Desktop first."
fi
print_success "Docker is installed"

if ! command -v docker-compose &> /dev/null; then
    print_error "Docker Compose is not installed. Please install Docker Compose first."
fi
print_success "Docker Compose is installed"

if ! command -v git &> /dev/null; then
    print_error "Git is not installed. Please install Git first."
fi
print_success "Git is installed"

# Check if Docker daemon is running
if ! docker ps &> /dev/null; then
    print_error "Docker daemon is not running. Please start Docker Desktop."
fi
print_success "Docker daemon is running"

print_header "Available Commands"

echo "Usage: $0 [command]"
echo ""
echo "Commands:"
echo "  setup              - Initial setup (build image + start containers)"
echo "  build              - Build Docker image locally"
echo "  start              - Start Docker Compose services"
echo "  stop               - Stop Docker Compose services"
echo "  restart            - Restart Docker Compose services"
echo "  logs               - View Docker Compose logs"
echo "  clean              - Clean up Docker resources"
echo "  test-build         - Test Docker build locally"
echo "  publish-image      - Push image to registry (requires auth)"
echo "  full-setup         - Complete setup with all optional services (kafka, phpmyadmin)"
echo "  help               - Show this help message"
echo ""

# Handle commands
case "${1:-help}" in
    setup)
        print_header "Setting up FPM Libraries"
        
        print_info "Building Docker image..."
        docker build -t fpm-libraries:latest . || print_error "Docker build failed"
        print_success "Docker image built successfully"
        
        print_info "Starting services with Docker Compose..."
        docker-compose up -d mysql rabbitmq redis || print_error "Docker Compose startup failed"
        print_success "Services started successfully"
        
        print_info "Starting builder container..."
        docker-compose up -d fpm-builder || print_error "Builder startup failed"
        print_success "Builder container started"
        
        echo ""
        print_header "Setup Complete!"
        echo "Services are now running:"
        echo "  - MySQL           : mysql:3306 (user: fpm_user, pass: fpm_password)"
        echo "  - RabbitMQ        : rabbitmq:5672 (UI: localhost:15672)"
        echo "  - Redis           : redis:6379 (password: redis_password)"
        echo "  - FPM Builder     : Building libraries..."
        echo ""
        print_info "To view builder logs: docker-compose logs -f fpm-builder"
        ;;
        
    build)
        print_header "Building Docker Image"
        docker build -t fpm-libraries:latest . || print_error "Docker build failed"
        print_success "Image built: fpm-libraries:latest"
        ;;
        
    start)
        print_header "Starting Services"
        docker-compose up -d || print_error "Failed to start services"
        print_success "All services started"
        docker-compose ps
        ;;
        
    stop)
        print_header "Stopping Services"
        docker-compose down || print_error "Failed to stop services"
        print_success "All services stopped"
        ;;
        
    restart)
        print_header "Restarting Services"
        docker-compose restart || print_error "Failed to restart services"
        print_success "All services restarted"
        docker-compose ps
        ;;
        
    logs)
        print_info "Showing logs (Press Ctrl+C to exit)"
        docker-compose logs -f
        ;;
        
    clean)
        print_header "Cleaning Docker Resources"
        print_info "This will remove containers, volumes, and images"
        read -p "Are you sure? (y/N) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            docker-compose down -v
            docker rmi fpm-libraries:latest 2>/dev/null || true
            print_success "Cleanup completed"
        else
            print_info "Cleanup cancelled"
        fi
        ;;
        
    test-build)
        print_header "Testing Docker Build"
        docker build --no-cache -t fpm-libraries:test . || print_error "Test build failed"
        print_success "Test build successful"
        docker run --rm fpm-libraries:test mvn --version
        ;;
        
    publish-image)
        print_header "Publishing Docker Image"
        read -p "Enter registry URL (e.g., ghcr.io/username): " registry
        read -p "Enter image tag (default: latest): " tag
        tag=${tag:-latest}
        
        image_name="${registry}/fpm-libraries:${tag}"
        print_info "Tagging image: $image_name"
        docker tag fpm-libraries:latest "$image_name" || print_error "Failed to tag image"
        
        print_info "Pushing to registry..."
        docker push "$image_name" || print_error "Failed to push image"
        print_success "Image published: $image_name"
        ;;
        
    full-setup)
        print_header "Full Setup with Optional Services"
        
        print_info "Building Docker image..."
        docker build -t fpm-libraries:latest . || print_error "Docker build failed"
        print_success "Docker image built"
        
        print_info "Starting all services (including Kafka and pgAdmin)..."
        docker-compose --profile kafka --profile admin-tools up -d || print_error "Startup failed"
        print_success "All services started"
        
        echo ""
        print_header "Full Setup Complete!"
        echo "Available services:"
        echo "  - MySQL            : mysql:3306"
        echo "  - phpMyAdmin       : http://localhost:5050"
        echo "  - RabbitMQ         : rabbitmq:5672 (UI: localhost:15672)"
        echo "  - Redis            : redis:6379"
        echo "  - Zookeeper        : zookeeper:2181"
        echo "  - Kafka            : kafka:9092 (plaintext)"
        echo ""
        print_info "Run 'docker-compose ps' to see all services"
        ;;
        
    help)
        # Already printed above
        ;;
        
    *)
        print_error "Unknown command: $1"
        ;;
esac
