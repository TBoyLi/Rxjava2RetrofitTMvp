package com.redli.rxjava2retrofittmvp.model

import android.content.Context
import com.redli.rxjava2retrofittmvp.base.BaseModel
import com.redli.rxjava2retrofittmvp.base.BaseResponse
import com.redli.rxjava2retrofittmvp.bean.Subject
import com.redli.rxjava2retrofittmvp.http.ApiClient
import com.redli.rxjava2retrofittmvp.http.SubscribeHandler
import com.redli.rxjava2retrofittmvp.http.rxjava.ProgressSubscriber
import com.redli.rxjava2retrofittmvp.http.rxjava.SubscriberOnNextListener

/**
 * @author RedLi
 * @date   2018/3/24
 */
class TopMoviesModel(private val context: Context) : BaseModel() {

    fun getTopMovies(infoHint: InfoHint, isShowLoading: Boolean, start: Int, count: Int) {
        val subscriberOnNextListener = object : SubscriberOnNextListener<List<Subject>> {
            override fun onNext(t: List<Subject>) {
                infoHint.getTopMoviesSuccess(t)
            }

            override fun onFail(err: String) {
                infoHint.onFail(err)
            }
        }

        val params = HashMap<String, String>()
        params.put("start", start.toString())
        params.put("count", count.toString())

        val observable = ApiClient.getInstance(context).getTopMovies(params)
        val observer = ProgressSubscriber<BaseResponse<List<Subject>>>(subscriberOnNextListener, context, isShowLoading)
        SubscribeHandler.observeOn(observable, observer)
    }

    /**
     * 按照自己的需求加相应的方法
     */
    fun getTopTitles() {

    }

    interface InfoHint {
        fun getTopMoviesSuccess(topMovies: List<Subject>)

        /**
         * 如果是一个界面多接口的话 直接添加就行了
         * 当然也需要在contract里面加对应的接口
         * 例如 下
         */
//        fun getTopTitlesSuccess(topTitles: List<Subject>)

        fun onFail(err: String)
    }
}