package com.redli.rxjava2retrofittmvp.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.redli.rxjava2retrofittmvp.R
import com.redli.rxjava2retrofittmvp.bean.Subject

/**
 * @author RedLi
 * @date   2018/3/23
 */
class TopMoviesAdapter(private val context: Context, layoutId: Int, data: MutableList<Subject>) : BaseQuickAdapter<Subject, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: Subject?) {
        helper?.setText(R.id.item_base_maps_title, item?.title)
                ?.setText(R.id.item_base_maps_note, item?.original_title)
                ?.setText(R.id.item_base_maps_ctime, "发布于：" + item?.year + "年")

        val img: ImageView? = helper?.getView(R.id.item_base_maps_img)

        val imgPath: String? = item?.images?.medium

        img?.let {
            Glide.with(context)
                    .load(imgPath)
                    .placeholder(R.mipmap.icon_load)
                    .into(img)
        }
    }
}