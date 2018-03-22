package com.redli.rxjava2retrofittmvp.http;


import com.redli.rxjava2retrofittmvp.bean.HttpResult;
import com.redli.rxjava2retrofittmvp.bean.Subject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Api service.
 *
 * @author RedLi
 * @data 2017 /10/27
 */


public interface ApiService {


    /**
     * Gets top movie.
     *
     * @param start the start
     * @param count the count
     * @return the top movie
     */
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count")
            int count);

    @GET("top250")
    Call<ResponseBody> getTopMovieCall(@Query("start") int start, @Query("count") int count);
}
