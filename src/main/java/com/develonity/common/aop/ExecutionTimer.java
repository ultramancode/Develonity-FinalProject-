package com.develonity.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {


//패키지 내 Controller로 끝나는 패키지에 있는 메서드를 조인포인트 적용
  @Pointcut("within(*..*Controller)")
  private void cut(){}

  // 메서드 실행 전,후로 시간을 공유해야 하기 때문에 Around 설정.
  @Around("cut()")
  public Object AssumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    Object proceed = joinPoint.proceed();// 조인포인트의 메서드 실행
    stopWatch.stop();

    long totalTimeMillis = stopWatch.getTotalTimeMillis();

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String methodName = signature.getMethod().getName();

    log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
    return proceed;
  }
}