package com.cxzq.ds.zeus.enums;

/**
 * @author yangkai
 */

public enum taskTypeEnum
{
    /**
     * 源任务
     */
    READING_SOURCE_TASK("1", "读源任务"),
    /**
     * 建表任务
     */
    CREATE_TABLE_TASK("2", "建表任务"),
    ;

    private final String code;

    private final String msg;

    taskTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
