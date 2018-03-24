package com.redli.rxjava2retrofittmvp.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.redli.rxjava2retrofittmvp.R
import com.redli.rxjava2retrofittmvp.adapter.TopMoviesAdapter
import com.redli.rxjava2retrofittmvp.base.BaseActivity
import com.redli.rxjava2retrofittmvp.bean.Subject
import com.redli.rxjava2retrofittmvp.contract.TopMoviesContract
import com.redli.rxjava2retrofittmvp.presenter.TopMoviesPresenter
import kotlinx.android.synthetic.main.activity_head_navigation.*
import kotlinx.android.synthetic.main.activity_top_movies.*

/**
 * @author RedLi
 * @date   2018/3/24
 */
class TopMoviesActivity : BaseActivity<TopMoviesContract.View, TopMoviesPresenter>(), TopMoviesContract.View {

    private var start: Int = 1
    private var count: Int = 10
    private var isRefresh: Boolean = false
    private var isLoadMore: Boolean = false
    private var subjects: MutableList<Subject> = ArrayList()
    private val topMoviesAdapter: TopMoviesAdapter by lazy {
        TopMoviesAdapter(this@TopMoviesActivity, R.layout.item_top_movies, subjects)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_movies)
        mPresenter?.attachModel()
        initListener()
        initRecycler()
        initDate()
    }

    private fun initListener() {
        navigationBack.setOnClickListener { finish() }
        navigationTitle.text = "电影列表"
    }

    private fun initRecycler() {
        refreshLayout.setOnRefreshListener {
            isRefresh = true
            isLoadMore = false
            start = 1
            mPresenter.getTopMovies(false, start, count)
        }
        refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            isLoadMore = true
            start += count
            mPresenter.getTopMovies(false, start, count)
        }

        recycler.layoutManager = LinearLayoutManager(this@TopMoviesActivity)
        recycler.addItemDecoration(DividerItemDecoration(this@TopMoviesActivity, DividerItemDecoration.VERTICAL))
        topMoviesAdapter.setOnItemClickListener { adapter, view, position ->

        }
        topMoviesAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
        recycler.adapter = topMoviesAdapter
    }

    private fun initDate() {
        mPresenter.getTopMovies(true, start, count)
    }

    override fun getTopMoviesSuccess(list: List<Subject>) {
        //进入界面
        if (!isRefresh && !isLoadMore) {
            this.subjects.addAll(list)
        }

        //刷新
        if (isRefresh && !isLoadMore) {
            this.subjects.clear()
            this.subjects.addAll(list)
            refreshLayout.finishRefresh()
        }

        //加载更多
        if (isLoadMore && !isRefresh) {
            this.subjects.addAll(list)
            refreshLayout.finishLoadMore()
        }
        /**
         * 后期可能加上列表高性能优化diff，现暂时粗暴刷新
         */
        this.topMoviesAdapter.notifyDataSetChanged()
    }

    override fun onFail(err: String) {
        tips(err)
        if (isRefresh && !isLoadMore) {
            refreshLayout.finishRefresh(false)
        }

        if (isLoadMore && !isRefresh) {
            refreshLayout.finishLoadMore(false)
        }
    }

    private fun tips(msg: String) {
        Toast.makeText(this@TopMoviesActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachModel()
    }
}