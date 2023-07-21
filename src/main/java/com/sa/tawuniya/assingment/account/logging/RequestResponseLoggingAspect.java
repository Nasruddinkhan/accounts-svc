package com.sa.tawuniya.assingment.account.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RequestResponseLoggingAspect {
    private final ObjectMapper objectMapper;


    @Pointcut("@annotation(LogRequestResponse)")
    public void logRequestResponsePointcut() {
    }

    @Before("logRequestResponsePointcut()")
    public void logRequest(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object request = args[0];
            log.debug("Request: {}", objectMapper.writeValueAsString(request));
        }
    }

    @AfterReturning(pointcut = "logRequestResponsePointcut()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) throws JsonProcessingException {
        log.debug("Response: {}", objectMapper.writeValueAsString(response));
    }
}


