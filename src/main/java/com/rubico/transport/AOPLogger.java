package com.rubico.transport;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AOPLogger {


    @Before("execution(* com.rubico.transport..*.*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
    }

//    @AfterReturning("execution(* com.rubico.transport..*.*(..))")
//    public void logMethodAccessAfter(JoinPoint joinPoint) {
//        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
//    }


//    @Around("execution (* com.rubico.transport..*.*(..))")
//    public void swallowException(ProceedingJoinPoint pjp) throws Throwable {
//        try {
//            pjp.proceed();
//        } catch (Throwable exception) {
//            log.error(pjp.getSignature().getName());
//        }
//    }


    @AfterThrowing(pointcut = "execution (* com.rubico.transport..*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        log.error(ex);
    }

}