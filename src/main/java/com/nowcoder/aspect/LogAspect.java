package com.nowcoder.aspect;

import org.apache.commons.lang.text.StrBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.nowcoder.toutiao.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StrBuilder sb = new StrBuilder();
        for (Object arg:joinPoint.getArgs()) {
            sb.append("arg:"+arg.toString()+"|");
        }
        logger.info("before method"+sb.toString());
    }

    @After("execution(* com.nowcoder.toutiao.controller.*Controller.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("after method");
    }


}
