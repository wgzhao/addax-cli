package com.wgzhao.addax.cli.enums;

/**
 * @author yangkai
 */

public enum taskStatusEnum
{
    /**
     * 初始
     */
    INITIAL(0, "初始"),
    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAIL(2, "失败"),
    ;

    private final Integer code;

    private final String msg;

    taskStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
