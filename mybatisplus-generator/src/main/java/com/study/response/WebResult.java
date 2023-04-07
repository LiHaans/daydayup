package com.golaxy.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WebResult<T> {
    /**
     * 响应业务状态
     */
    @ApiModelProperty(value = "响应业务状态")
    private Integer status;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String msg;

    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应中的数据")
    private T data;

    public WebResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> WebResult build(Integer status, String msg, T data) {
        return new WebResult(status, msg, data);
    }

    public WebResult(T data) {
        this.status = ResponseStatus.OK.getStatus();
        this.msg = ResponseStatus.OK.getMsg();
        this.data = data;
    }

    public static <T> WebResult error(T data) {
        return new WebResult(ResponseStatus.ERROR.getStatus(), ResponseStatus.ERROR.getMsg(), data);
    }

    public static <T> WebResult error(String msg,T data) {
        return new WebResult(ResponseStatus.ERROR.getStatus(), msg, data);
    }

    public static <T> WebResult loginError(String msg, T data) {
        return new WebResult(ResponseStatus.LOGIN_ERROR.getStatus(), msg, data);
    }

    public static <T>  WebResult success(String msg, T data) {
        return new WebResult(ResponseStatus.OK.getStatus(), msg, data);
    }

    public static <T> WebResult success(T data) {
        return new WebResult(ResponseStatus.OK.getStatus(), ResponseStatus.OK.getMsg(), data);
    }

    public static WebResult error() {
        return new WebResult(ResponseStatus.ERROR.getStatus(), ResponseStatus.ERROR.getMsg(), null);
    }

    public static WebResult success() {
        return new WebResult(ResponseStatus.OK.getStatus(), ResponseStatus.OK.getMsg(), null);
    }

    public static WebResult loginError() {
        return new WebResult(ResponseStatus.LOGIN_ERROR.getStatus(), ResponseStatus.LOGIN_ERROR.getMsg(), null);
    }

    public static WebResult error(ResponseStatus responseStatus){
        return new WebResult(responseStatus.getStatus(),responseStatus.getMsg(),null);
    }

    public static <T> WebResult error(ResponseStatus responseStatus, T data){
        return new WebResult(responseStatus.getStatus(),responseStatus.getMsg(),data);
    }

    /**
     * 需添加getter setter方法，否则会导致异常：No converter found for return id of type。
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
