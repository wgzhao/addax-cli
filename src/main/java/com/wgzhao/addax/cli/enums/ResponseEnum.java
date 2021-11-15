package com.wgzhao.addax.cli.enums;


/**
 * @author yangkai
 */

public enum ResponseEnum
{
    /**
     * 接口调用成功
     */
    SUCCESS("0", "接口调用成功"),
    /**
     * 失败
     */
    ERROR("1", "失败"),

    /* 参数错误：10001-19999 */
    /**
     * 参数无效
     */
    ARGUMENTS_ARE_INVALID("10001", "参数无效,未查询到相关信息"),

    /**
     * 参数为空
     */
    ARGUMENTS_IS_NULL("10002", "参数为空"),

    /**
     * 参数类型错误
     */
    ARGUMENTS_TYPE_ERROR("10003", "参数类型错误"),

    /**
     * 参数缺失
     */
    ARGUMENTS_LOST("10004", "参数缺失"),

    /* 参数错误 END */

    /* 系统错误：30001-39999 */
    /**
     * 系统繁忙，请稍后重试
     */
    SYSTEM_REQUEST_TIMEOUT("30001", "系统繁忙，请稍后重试"),

    /**
     * 接口调用异常
     */
    INTERFACE_CALL_EXCEPTION("30002", "接口调用异常"),

    /**
     * 该接口禁止访问
     */
    INTERFACE_ACCESS_FORBIDDEN("30003", "该接口禁止访问"),

    /**
     * 接口地址无效
     */
    INTERFACE_ADDRESS_IS_NOT_VALID("30004", "接口地址无效"),

    /**
     * 系统错误，请联系管理员
     */
    SYSTEM_ERROR("30005", "系统错误，请联系管理员"),

    /**
     * 接口正在维护中，暂时无法访问
     */
    INTERFACE_IS_BEING_MAINTAINED("30005", "接口正在维护中，暂时无法访问"),

    /* 系统错误 END */

    /* 接口鉴权错误：40001-49999 */

    /**
     * 签名鉴权失败
     */
    SIGNATURE_AUTHENTICATION_FAILED("40001", "签名鉴权失败"),

    /**
     * appId不存在
     */
    APP_ID_IS_NULL("40002", "appId不存在"),

    /**
     * appId已失效
     */
    APP_ID_INVALID("40003", "appId已失效"),

    /**
     * 签名已失效，需重新计算签名
     */
    SIGNATURE_INVALID("40004", "签名已失效，需重新计算签名"),

    /* 接口鉴权错误 END */

    /* TOKEN异常 START */

    TOKEN_INVALID("50001","token失效，请重新登录"),

    /* TOKEN异常 END */

    /* 业务异常定义 START */


    /* 业务异常定义 END */

    ;

    private final String returnCode;

    private final String returnMsg;

    ResponseEnum(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }
}