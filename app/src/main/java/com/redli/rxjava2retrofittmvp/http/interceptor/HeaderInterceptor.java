package com.redli.rxjava2retrofittmvp.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author RedLi
 * @date 2018/3/20
 *
 * 在retrofit使用中，可以通过@Header来实现Header
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("User-Agent", "Zero")
                .build();
        return chain.proceed(request);
    }
}