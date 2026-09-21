package com.codenza.enotes.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // ---------- Pointcuts (named, reusable) ----------

    @Pointcut("execution(* com.codenza.enotes.controller..*(..))")
    public void controllerLayer() {
    }

    @Pointcut("execution(* com.codenza.enotes.service..*(..))")
    public void serviceLayer() {
    }

	@Pointcut("execution(* com.codenza.enotes.repository..*(..))")
	public void repositoryLayer() {
	}

    // ---------- Advice ----------

    @Around("controllerLayer() || serviceLayer() || repositoryLayer()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("Calling :: {} :: {}()", className, methodName);

        if (log.isDebugEnabled()) {
            log.debug("Args :: {} :: {}() :: {}", className, methodName,
                    Arrays.toString(joinPoint.getArgs()));
        }

        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.warn("Failed :: {} :: {}() :: {} - {}", className, methodName,
                    ex.getClass().getSimpleName(), ex.getMessage());
            throw ex; 
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("Ending :: {} :: {}() :: {} ms", className, methodName, duration);
        }
    }
}