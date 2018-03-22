package com.redli.rxjava2retrofittmvp.base;

/**
 * @author RedLi
 * @date 2018/3/20
 * <p>
 * 根据Json数据格式构建返回数据类
 */

public class BaseResponse<T> {

    /**
     * {
     * "resultCode": 0,
     * "resultMessage": "成功",
     * "data": {}
     * }
     */


    private int code;
    private boolean success;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
