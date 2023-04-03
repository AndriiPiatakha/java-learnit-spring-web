package com.itbulls.learnit.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoAspect {

	/**
	 * This is pointcut for all methods execution in the package com.itbulls.learnit.spring.aop
	 */
	@Pointcut("execution(* com.itbulls.learnit.spring.aop.*.*(..))")
	private void selectAll() {}

	/**
	 * This is pointcut for all methods execution in the bean "user"
	 */
	@Pointcut("bean(user)")
	private void userBeanPointcut() {}
	

	@Before("selectAll()")
	public void doAccessCheck() {
		System.out.println("Before Advice");
	}

	@Before("userBeanPointcut()")
	public void userBeanAdvice() {
		System.out.println("Before Advice for user bean");
	}
	
	@AfterReturning(pointcut = "selectAll()", returning = "returnObj")
	public void afterReturningAdvice(Object returnObj) {
		System.out.println("After returning Advice, returning object = " + returnObj);
	}
	
	@AfterThrowing(pointcut = "selectAll()", throwing = "exception")
	public void afterThrowingAdvice(Exception exception) {
		System.out.println("After throwing Advice, exception thrown class = " + exception.getClass());
	}
	
	@After("selectAll()")
 	public void afterAdvice() {
		System.out.println("After Advice");
	}
	
	@Around("selectAll()")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Around Advice - before calling method");
        Object retVal = pjp.proceed();
        System.out.println("Around Advice - after calling method");
        
//		If you need to pass parameters to the method - you can use API below
//		pjp.proceed(new Object[] {"param1", "param2"});
        return retVal;
	}
}
