package com.xiongzehua.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by 31339 on 2018/5/20.
 */
//切面即是拦截器
@Aspect
@Component
public class MarryServiceAspect {
    @Before("execution(* com.xiongzehua.service.MarryService.*(..))")
    public void before() {
        System.out.println("婚礼开始");
    }
    @After("execution(* com.xiongzehua.service.impl.MarryServiceImpl.marry(..))")
    public void after() {
        System.out.println("婚礼结束");
    }
    @AfterReturning("execution(* com.xiongzehua.service.impl.MarryServiceImpl.marry(..))")
    public void aa() {
        System.out.println("没有异常!");
    }
    @AfterThrowing("execution(* com.xiongzehua.service.impl.MarryServiceImpl.marry(..))")
    public void bb() {
        System.out.println("发生了异常!");
    }
}
