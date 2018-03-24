package com.redli.rxjava2retrofittmvp.http.rxjava;

/**
 * The interface Subscriber on next listener.
 *
 * @param <T> the type parameter
 * @author RedLi
 * @date 2018 /3/21
 */
public interface SubscriberOnNextListener<T> {

    /**
     * 返回数据
     *
     * @param t the t
     */
    void onNext(T t);


    /**
     * On fail.
     * 失败
     *
     * @param err the err
     */
    void onFail(String err);
}
