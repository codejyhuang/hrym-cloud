package com.everg.hrym.common.core.interceptor;

import com.everg.hrym.common.core.entity.BaseResult;
import com.everg.hrym.common.core.exception.CommonResultCode;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.exception.HrymFeignException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hong on 2019/4/2.
 */
@ControllerAdvice
@ResponseBody
public class HrymExceptionAdvice {

    //其他错误
    @ExceptionHandler({Exception.class})
    public BaseResult exception(Exception ex) {

        String code ="";
        if(ex.getCause() instanceof HrymFeignException){

            HrymFeignException e = (HrymFeignException)ex.getCause();
            code = e.getErrorCode();
            return new BaseResult(code,ex.getMessage());
        }
        return new BaseResult(CommonResultCode.CODE3001);
    }

    // 自定义异常
    @ExceptionHandler({HrymException.class})
    public BaseResult hrymException(HrymException ex) {
        return new BaseResult(ex);
    }

    // 自定义feign异常
    @ExceptionHandler({HrymFeignException.class})
    public BaseResult hrymFeignException(HrymFeignException ex) {
        return new BaseResult(ex.getErrorCode(),ex.getErrorDesc());
    }
}
