#!/bin/bash

echo "======================================"
echo "Building FPM Library Architecture"
echo "======================================"

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print success
success() {
    echo -e "${GREEN}✓ $1${NC}"
}

# Function to print error
error() {
    echo -e "${RED}✗ $1${NC}"
    exit 1
}

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    error "Maven is not installed. Please install Maven first."
fi

# Clean previous builds
echo ""
echo "Cleaning previous builds..."
mvn clean || error "Clean failed"
success "Clean completed"

# Build parent and all modules
echo ""
echo "Building all modules..."
mvn install -DskipTests || error "Build failed"
success "Build completed"

# Run tests (optional)
if [ "$1" == "--with-tests" ]; then
    echo ""
    echo "Running tests..."
    mvn test || error "Tests failed"
    success "Tests passed"
fi

echo ""
echo "======================================"
echo "Build completed successfully!"
echo "======================================"
echo ""
echo "Libraries installed to local Maven repository:"
echo "  - fpm-core:1.0.0"
echo "  - fpm-domain:1.0.0"
echo "  - fpm-grpc:1.0.0"
echo "  - fpm-messaging:1.0.0"
echo "  - fpm-testing:1.0.0"
echo ""
echo "You can now use these libraries in your projects."
echo ""
