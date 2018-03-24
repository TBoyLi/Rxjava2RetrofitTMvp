package com.redli.rxjava2retrofittmvp.http.rxjava;

/**
 * @author RedLi
 * @date 2018/3/21
 * <p>
 * 请求失败的原因
 */

public enum ApiException {

    /**
     * 解析数据失败
     **/
    PARSE_ERROR,

    /**
     * 网络问题
     **/
    BAD_NETWORK,

    /**
     * 连接错误
     **/
    CONNECT_ERROR,

    /**
     * 连接超时
     **/
    CONNECT_TIMEOUT,

    /**
     * section token past due 过期
     **/
    TOKEN_PAST_DUE,

    /**
     * 未知错误
     **/
    UNKNOWN_ERROR,
}
