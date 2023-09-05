/* Licensed under Apache-2.0 2021-2023 */
package com.rxdata.paymentservice.config.logging;

import com.rxdata.paymentservice.utils.AppConstants;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
                    + " || within(@org.springframework.stereotype.Service *)"
                    + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // pointcut definition
    }

    @Pointcut("@within(com.example.paymentservice.config.logging.Loggable) || "
                    + "@annotation(com.rxdata.paymentservice.config.logging.Loggable)")
    public void applicationPackagePointcut() {
        // pointcut definition
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(AppConstants.PROFILE_NOT_PROD))) {
            log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getCause() == null ? "NULL" : e.getCause(),
                    e.getMessage(),
                    e);

        } else {
            log.error("Exception in {}.{}() with cause = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getCause() == null ? "NULL" : e.getCause());
        }
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isTraceEnabled()) {
            log.trace("Enter: {}.{}()",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
        }
        //long start = System.currentTimeMillis();
        final long start = Instant.now().toEpochMilli();
        Object result = joinPoint.proceed();
        long end = Instant.now().toEpochMilli();
        if (log.isTraceEnabled()) {
            log.trace("Exit: {}.{}(). Time taken: {} millis",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    end - start);
        }
        return result;
    }
}
