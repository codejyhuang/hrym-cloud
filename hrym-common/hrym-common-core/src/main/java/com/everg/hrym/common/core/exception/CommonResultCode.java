package com.everg.hrym.common.core.exception;

/**
 * 通用返回码
 *
 * Created by hong on 2019/3/29.
 */
public enum CommonResultCode implements ResultCode {

    CODE0000("0", "处理成功"),
    CODE1001("1001", "用户尚未登录"),
    CODE1002("1002", "无效token"),
    CODE1003("1003", "用户已注册"),

    CODE2001("2001", "无效的请求数据"),
    CODE2002("2002", "缺少业务参数"),
    CODE2003("2003", "URL请求异常"),

    CODE3001("3001", "系统级异常"),
    CODE3002("3002", "处理异常"),

    CODE4001("4001", "验证码发送失败"),
    CODE4002("4002", "请输入正确的验证码"),
    CODE4003("4003", "无响应内容"),
    CODE4004("4004", "TrackerServer getConnection return null"),
    CODE4005("4005", "getStoreStorage return null"),
    CODE4006("4006", "file upload success"),
    CODE4007("4007", "请输入正确的业务参数"),

    CODE5001("5001", "您已添加过此功课"),

    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param desc 描述
     */
    CommonResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public ResultCodeType getType() {
        return ResultCodeType.SYSTEM;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
