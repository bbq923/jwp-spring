package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class PerformanceAspect {
	private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);
	
	@Pointcut("within(next.controller..*) || within(next.dao..*) || within(next.service..*)")
	public void myPointcut() {
		
	}
	
	@Around("myPointcut()")
	@Order(1)
	public Object profiling(ProceedingJoinPoint pjp) throws Throwable {
		log.debug("execution class : {}", pjp.getTarget());
		
		StopWatch watch = new StopWatch();
		watch.start();
		Object ret = pjp.proceed();
		watch.stop();
		log.debug("execution time : {}", watch.getTotalTimeMillis());
		return ret;
	}
	
	@Around("myPointcut()")
	@Order(2)
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
	    final Logger log = LoggerFactory.getLogger(pjp.getTarget().getClass());
	
	    final String methodName = pjp.getSignature().getName();
	    log.debug("{}(): {}", methodName, pjp.getArgs());
	
	    Object result = pjp.proceed();
	    log.debug("{}(): result={}", methodName, result);
	
	    return result;
	}
}
