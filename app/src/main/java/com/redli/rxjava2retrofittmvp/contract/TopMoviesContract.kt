package com.redli.rxjava2retrofittmvp.contract

import com.redli.rxjava2retrofittmvp.bean.Subject
import com.redli.rxjava2retrofittmvp.mvp.IView

/**
 * @author RedLi
 * @date   2018/3/24
 *
 * 自定义协议 一个界面增加新的业务是方便拓展
 */
class TopMoviesContract {

    /**
     * 界面回调
     */
    interface View : IView {

        fun getTopMoviesSuccess(list: List<Subject>)

//        fun getTopTitlesSuccess()

        fun onFail(err: String)
    }

    /**
     * 主持界面动作到model
     */
    interface Presenter {

        fun attachModel()

        fun detachModel()

        fun getTopMovies(isShowLoading: Boolean, start: Int, count: Int)
//        fun getTopTitles()
    }
}