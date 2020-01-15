package anla.lean.mybatis.mybatisspring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author luoan
 * @version 1.0
 * @date 2020/1/15 15:50
 **/
@Aspect
@Component
public class ReturnAspect implements Ordered {

    @Pointcut("execution(* anla.lean.mybatis.mybatisspring.dao..*(..))")
    private void returnPointcut() {
    }

    @Before("returnPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        String apiName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        System.out.println("========== 调用方法:{} start ==========, " + apiName);

        for (Object arg : joinPoint.getArgs()) {
            System.out.println("arg:," + arg);
        }
        System.out.println("========== 调用方法:{} end ==========, " + apiName);
    }

    /**
     * 拦截器具体实现
     *
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("returnPointcut()")
    public Object intercepter(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("pjp is :" + pjp);
        return pjp.proceed();
    }


    public int getOrder() {
        return 10;
    }
}
