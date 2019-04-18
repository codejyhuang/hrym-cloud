package com.everg.hrym.common.core.interceptor;

import com.everg.hrym.common.core.annotation.ReturnVO;
import com.everg.hrym.common.core.annotation.ReturnVOField;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Aspect
@Component
public class ReturnVOAspect {
    private static Logger logger = LoggerFactory.getLogger(ReturnVOAspect.class);

    @Pointcut("@annotation(com.everg.hrym.common.core.annotation.ReturnVO)")
    public void controllerInteceptor() {

    }

    @Around("controllerInteceptor()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
//        // 如果非Map类或者长度为0，则直接返回处理结果
//        if (!(result instanceof Map) || ((Map) result).size() == 0) {
//            return result;
//        }
//        Map<String, Object> requestMap = (Map<String, Object>) result;
//        Map<String, Object> resultMap = new HashMap<>();
        // 如果注解
        ReturnVO returnVO = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(ReturnVO.class);

        setField(result,returnVO);
        return result;
    }

    private void doSetField(Object value,ReturnVO vo) {

        try {
            Object tempObject = vo.targetClass().newInstance();

            for (ReturnVOField voField : vo.value()){

            }



        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    private Object setField(Object value, ReturnVO vo) {

        if( value instanceof Collection){
            ((Collection) value).forEach((v) -> {
                doSetField(v, vo);
            });
        } else{
            doSetField(value, vo);
        }


        return null;
    }
}