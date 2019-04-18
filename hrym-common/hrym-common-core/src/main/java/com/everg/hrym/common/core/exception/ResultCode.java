package com.everg.hrym.common.core.exception;

/**
 * 结果码接口，所有结果码枚举都需要实现该接口
 * Created by hong on 2019/3/29.
 */
public interface ResultCode {

    /**
     * 获取异常类型
     * 
     * @return 异常类型(按业务区分业务)
     */
    public ResultCodeType getType();

    /**
     * 获取结果码
     * 
     * @return 结果码
     */
    String getCode();

    /**
     * 获取结果描述
     * 
     * @return 结果描述
     */
    String getDesc();

}