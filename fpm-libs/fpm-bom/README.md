# 📦 FPM Bill of Materials (BOM)

Centralized dependency management for FPM 2025 libraries and third-party dependencies.

## 🎯 Purpose

- ✅ Single source of truth for all dependency versions
- ✅ Prevent version conflicts across microservices
- ✅ Simplify dependency declarations
- ✅ Easy version upgrades in one place

## 📖 Usage

### In Microservices

Add to your service's `pom.xml`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.fpm2025.libs</groupId>
            <artifactId>fpm-bom</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>