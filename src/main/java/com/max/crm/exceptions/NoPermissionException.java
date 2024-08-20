package com.max.crm.exceptions;

/**
 * 自定义参数异常
 */
public class NoPermissionException extends RuntimeException {
    private Integer code=300;
    private String msg="用户没有授予权限!";


    public NoPermissionException() {
        super("用户没有授予权限!");
    }

    public NoPermissionException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoPermissionException(Integer code) {
        super("用户没有授予权限!");
        this.code = code;
    }

    public NoPermissionException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
