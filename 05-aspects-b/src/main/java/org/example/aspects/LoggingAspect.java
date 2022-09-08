package org.example.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

@Component
@Aspect
public class LoggingAspect {

    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* org.example.services.CommentService.publishComment(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        List<Object> args = asList(joinPoint.getArgs());
        logger.info("Method " + methodName + " with args " + args + " will execute");

        Object returned = joinPoint.proceed(new Object[]{new Comment("Jane", "Bye!")});

        logger.info("Method executed and returned " + returned);
        return "FAILED";
    }
}
