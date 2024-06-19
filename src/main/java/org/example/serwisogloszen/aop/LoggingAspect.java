package org.example.serwisogloszen.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LoggingAspect {

    @Around("execution(public * org.example.serwisogloszen..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Start executing: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in {} with cause = {} and message = {}",
                    joinPoint.getSignature(), throwable.getCause(), throwable.getMessage());
            throw throwable;
        }

        log.debug("Completed: {} with result = {}", joinPoint.getSignature(), result);
        return result;
    }
}
