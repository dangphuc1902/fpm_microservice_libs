# Multi-stage build for FPM Libraries
# Stage 1: Build Stage
FROM eclipse-temurin:21-jdk-alpine AS builder

LABEL maintainer="FPM Development Team"
LABEL description="FPM Libraries Build Image - Multi-module Maven Project"

# Install Maven
RUN apk add --no-cache maven git

# Set working directory
WORKDIR /build

# Copy project files
COPY . .

# Build the project
RUN mvn clean install -DskipTests -q

# Stage 2: Runtime Stage (for libraries, this just preserves artifacts)
FROM eclipse-temurin:21-jdk-alpine

LABEL maintainer="FPM Development Team"
LABEL description="FPM Libraries Runtime Image"

# Create app user for security (non-root)
RUN addgroup -g 1000 app && \
    adduser -D -u 1000 -G app app

# Set working directory
WORKDIR /app

# Copy built artifacts from builder stage
COPY --from=builder --chown=app:app /build /app

# Switch to non-root user
USER app

# Health check (Maven project)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD test -d /app/fpm-libs/fpm-core/target && echo "Build artifacts present"

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV MAVEN_CONFIG="/home/app/.m2"

# Default command - print build info
CMD ["mvn", "--version"]
