package com.redli.rxjava2retrofittmvp.presenter

import com.redli.rxjava2retrofittmvp.base.BasePresenter
import com.redli.rxjava2retrofittmvp.bean.Subject
import com.redli.rxjava2retrofittmvp.contract.TopMoviesContract
import com.redli.rxjava2retrofittmvp.model.TopMoviesModel

/**
 * @author RedLi
 * @date   2018/3/24
 */
class TopMoviesPresenter : BasePresenter<TopMoviesContract.View>(), TopMoviesContract.Presenter {

    private var model: TopMoviesModel? = null
    private var infoHint: InfoHint? = null

    override fun attachModel() {
        if (model == null) {
            model = TopMoviesModel(mView.context)
        }
        if (infoHint == null) {
            infoHint = InfoHint()
        }
    }

    override fun detachModel() {
        if (model != null) {
            model = null
        }

        if (infoHint != null) {
            infoHint = null
        }
    }

    override fun getTopMovies(isShowLoading: Boolean, start: Int, count: Int) {
        infoHint?.let { model?.getTopMovies(it, isShowLoading, start, count) }
    }

    internal inner class InfoHint : TopMoviesModel.InfoHint {
        override fun getTopMoviesSuccess(topMovies: List<Subject>) {
            if (mView != null) {
                mView.getTopMoviesSuccess(topMovies)
            }
        }

        override fun onFail(err: String) {
            if (mView != null) {
                mView.onFail(err)
            }
        }

    }

}