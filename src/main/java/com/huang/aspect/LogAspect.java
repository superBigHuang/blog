package com.huang.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author huangneng
 * @create 2020-04-15 20:37
 * 日志处理
 */
@Aspect
@Component
public class LogAspect {

    //日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*拦截web包下的所有方法*/
    @Pointcut("execution(* com.huang.web.*.*(..))")
    public void log(){}

    //在切面之前的操作
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequersLog requersLog = new RequersLog(url, ip, classMethod, args);
        logger.info("request ： {}",requersLog);
    }

    //在切面之后的操作
    @After("log()")
    public void after(){
        logger.info("-----------after---------");
    }

    //拦截方法返回的内容
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturing(Object result){
        logger.info("Result : {}" + result);
    }

    private class RequersLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        @Override
        public String toString() {
            return "RequersLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public RequersLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }
    }

}
