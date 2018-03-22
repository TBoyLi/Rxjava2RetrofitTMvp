package com.redli.rxjava2retrofittmvp.http.rxjava;

/**
 * @author RedLi
 * @date 2018/3/21
 */

public interface SubscriberOnNextListener<T> {

    /**
     * 返回数据
     * @param t
     */
    void onNext(T t);
}
