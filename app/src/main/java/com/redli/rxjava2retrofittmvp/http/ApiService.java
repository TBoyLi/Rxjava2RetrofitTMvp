package com.redli.rxjava2retrofittmvp.http;


import com.redli.rxjava2retrofittmvp.base.BaseResponse;
import com.redli.rxjava2retrofittmvp.bean.Subject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * The interface Api service.
 *
 * @author RedLi
 * @data 2017 /10/27
 */


public interface ApiService {


    /**
     * Gets top movie.
     * @param params
     * @return the top movie
     */
    @POST("top250")
    Observable<BaseResponse<List<Subject>>> getTopMovies(@QueryMap Map<String, String> params);
}
