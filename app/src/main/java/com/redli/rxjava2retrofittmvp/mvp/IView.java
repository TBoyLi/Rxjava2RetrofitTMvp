package com.redli.rxjava2retrofittmvp.mvp;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.subjects.PublishSubject;


/**
 * @author RedLi
 * @date
 */

public interface IView {

     /**
      * 获取上下文（当前的view）
      * @return
      */
     Context getContext();

     /**
      * 获取LifecycleTransformer方便生命结束管控请求
      * @return
      */
     LifecycleTransformer bindLifecycle();
}
