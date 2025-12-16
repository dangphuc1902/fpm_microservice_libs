package com.fpm_2025.fpm_microservice_libs.Aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for monitoring method performance
 */
@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() {
        // Method is empty as this is just a Pointcut
    }

    @Around("servicePointcut()")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > 1000) { // Log if execution time > 1 second
            log.warn("SLOW METHOD: {}.{}() took {}ms",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    executionTime);
        } else if (log.isDebugEnabled()) {
            log.debug("Performance: {}.{}() took {}ms",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    executionTime);
        }

        return result;
    }
}
