package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.frankmoley.lil.fid.aspect.Loggable)")
    public void executeLogging(){ }

    @Around(value = "executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        StringBuilder message = new StringBuilder("Method : " );
        message.append(joinPoint.getSignature().getName());
        message.append(" totalTime: ").append(totalTime).append(" ms");
        Object[] args = joinPoint.getArgs();
        if(args != null &  args.length > 0 ) {
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg -> {
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        if(returnValue instanceof Collection) {
            message.append(", returining: ").append(((Collection) returnValue).size()).append(" instance(s)");
        }else{
            message.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());
        return returnValue;
    }

//    @AfterReturning(value = "executeLogging()", returning = "returnValue")
//    public void logMethodCall(JoinPoint joinPoint, Object returnValue) {
//        StringBuilder message = new StringBuilder("Method : " );
//        message.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs();
//        if(args != null &  args.length > 0 ) {
//            message.append(" args=[ | ");
//            Arrays.asList(args).forEach(arg -> {
//                message.append(arg).append(" | ");
//            });
//            message.append("]");
//        }
//        if(returnValue instanceof Collection) {
//            message.append(", returining: ").append(((Collection) returnValue).size()).append(" instance(s)");
//        }else{
//            message.append(", returning: ").append(returnValue.toString());
//        }
//        LOGGER.info(message.toString());
//    }

//    @Before(value = "executeLogging()")
//    public void logMethodCall(JoinPoint joinPoint) {
//        StringBuilder message = new StringBuilder("Method : " );
//        message.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs();
//        if(args != null &  args.length > 0 ) {
//            message.append(" args=[ | ");
//            Arrays.asList(args).forEach(arg -> {
//                message.append(arg).append(" | ");
//            });
//            message.append("]");
//        }
//
//        LOGGER.info(message.toString());
//    }
}
