package com.bs.response;

/**
 * 响应信息码
 * @author
 */

public enum ResponseStatus {

    /**
     * 通用码100**
     * 登录相关错误151**
     */

    // 通用码100**
    // 请求成功
    OK(10000,"Success"),
    // 请求失败
    ERROR(10001,"Error"),
    // 请求失败
    TOKEN_INVALID(10002,"无效的Token!"),
    // 请求失败
    AUTH_INVALID(10003,"无权限访问!"),
    // 获取上下文信息失败
    CTX_INVALID(10003,"获取上下文信息失败!"),
    // 非法请求
    ILLEGAL_REQUEST(10004,"非法请求!"),



    // 登录相关错误151**
    // 用户名密码不匹配
    LOGIN_FAIL_PASSWORD_ERROR(15101,"登录失败！用户名密码不匹配，请校对后重试！"),
    // 账号停用
    LOGIN_FAIL_ACCOUNT_FROZEN(15102,"登录失败！此账号已停用，请联系管理员。"),
    // 用户不存在或密码错误
    LOGIN_ERROR(15103,"登录失败！用户不存在或密码错误！"),
    //无效的Token
    LOGIN_INVALID_TOKEN(15104,"无效的Token！"),
    //无权限访问
    LOGIN_INVALID_ACCESS(15105,"无权限访问！");



    private Integer status;
    private String msg;

    ResponseStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}