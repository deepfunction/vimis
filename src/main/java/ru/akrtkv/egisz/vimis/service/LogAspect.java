package ru.akrtkv.egisz.vimis.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    private static final String POINTCUT = "@annotation(ru.akrtkv.egisz.vimis.service.Log)";

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterException(Exception e) {
        LOG.error(e.getMessage(), e.getCause());
    }

    @Around(POINTCUT)
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        if (signature.getParameterNames().length != 0) {
            var parameterNames = signature.getParameterNames();
            LOG.info("method: {}, parameter names: {},  input data: {}", joinPoint.getSignature().getName(), parameterNames, joinPoint.getArgs());
        }
        var response = joinPoint.proceed();
        if (response != null) {
            LOG.info("returned {} from method: {}", response, joinPoint.getSignature().getName());
        }
        return response;
    }
}
