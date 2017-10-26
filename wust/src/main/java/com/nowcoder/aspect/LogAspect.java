package com.nowcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/10/25 0025.
 */
@Aspect
@Component
public class LogAspect {
    private static final org.slf4j.Logger log= LoggerFactory.getLogger(LogAspect.class);

                   //返回值                                        //方法参数类型
//    @Before("execution(* com.nowcoder.controller.IndexController.index(..))")
//    public void before(JoinPoint joinPoint){
//        StringBuilder sb=new StringBuilder();
//        for(Object args:joinPoint.getArgs()){
//            sb.append(args.toString());
//        }
//
//        log.info("befor aspetc"+" "+sb.toString());
//    }
//
//    @After("execution(* com.nowcoder.controller.IndexController.index(..))")
//    public void after(){
//        log.info("after aspetc"+new Date().toString());
//    }

}
