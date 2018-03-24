package com.redli.rxjava2retrofittmvp.http;

/**
 * @author RedLi
 * @data 2017/10/27
 */


public class UrlConfig {
    /**
     * URL的简单构成：
     *  [scheme:][//authority][path][?query]
     *
     * 例：http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4
     *
     *  scheme:  http
     *  authority:  www.java2s.com:8080
     *  path:  /yourpath/fileName.htm
     *  query: 在？后的部分为：stove=10&path=32&id=4
     *
     *  整个网络请求中参数主要可以分成：scheme、authority、path、query、header、body 这六块
     *  header（请求头）和 body（常用于post请求中的请求体，有多种封装方法，不暴露在url中）这两个参数。
     *
     *  Url header body 等可以动态添加。
     */

    /**
     * 测试服务器
     */
    public static final String BASE_URL_DEBUG = "https://api.douban.com/v2/movie/";

    /**
     * 正式服务器
     */
    public static final String BASE_URL_RELEASE = "https://api.douban.com/v2/movie/";

}

