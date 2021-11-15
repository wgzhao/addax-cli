package com.wgzhao.addax.cli.config;

import com.wgzhao.addax.cli.enums.ResponseEnum;

import java.io.Serializable;

/**
 * @author yangkai
 */

public class ServerResponse<T>
        implements Serializable
{

    private final String returnCode;

    private String returnMsg;

    private T data;

    private ServerResponse(String returnCode)
    {
        this.returnCode = returnCode;
    }

    private ServerResponse(String returnCode, T data)
    {
        this.returnCode = returnCode;
        this.data = data;
    }

    private ServerResponse(String returnCode, String returnMsg, T data)
    {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    private ServerResponse(String returnCode, String returnMsg)
    {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public String getReturnCode()
    {
        return returnCode;
    }

    public String getReturnMsg()
    {
        return returnMsg;
    }

    public T getData()
    {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess()
    {
        return new ServerResponse<>(ResponseEnum.SUCCESS.getReturnCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String returnMsg)
    {
        return new ServerResponse<>(ResponseEnum.SUCCESS.getReturnCode(), returnMsg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data)
    {
        return new ServerResponse<>(ResponseEnum.SUCCESS.getReturnCode(), ResponseEnum.SUCCESS.getReturnMsg(),
                data);
    }

    public static <T> ServerResponse<T> createBySuccess(String returnMsg, T data)
    {
        return new ServerResponse<>(ResponseEnum.SUCCESS.getReturnCode(), returnMsg, data);
    }

    public static <T> ServerResponse<T> createByError()
    {
        return new ServerResponse<>(ResponseEnum.ERROR.getReturnCode(), ResponseEnum.ERROR.getReturnCode());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage)
    {
        return new ServerResponse<>(ResponseEnum.ERROR.getReturnCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(String errorCode, String errorMessage)
    {
        return new ServerResponse<>(errorCode, errorMessage);
    }
}
