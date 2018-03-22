package com.redli.rxjava2retrofittmvp.http.rxjava;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.redli.rxjava2retrofittmvp.bean.HttpResult;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.Exception.BAD_NETWORK;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.Exception.CONNECT_ERROR;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.Exception.CONNECT_TIMEOUT;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.Exception.PARSE_ERROR;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.Exception.UNKNOWN_ERROR;

/**
 * @author RedLi
 * @date 2018/3/21
 */

public class ProgressSubscriber<T extends HttpResult> implements
        ProgressCancelListener, Observer<T> {

    private static final String TAG = ProgressSubscriber.class.getSimpleName();

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    private Disposable disposable;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable s) {
        Log.d(TAG, "onSubscribe: ");
        this.disposable = s;
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: ");
        mSubscriberOnNextListener.onNext(t.getSubjects());
//        if (t.isSuccess() && mSubscriberOnNextListener != null) {
//            mSubscriberOnNextListener.onNext(t);
//        } else {
//            onFail(t);
//        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.getMessage());
        dismissProgressDialog();
        if (e instanceof HttpException) {
            //   HTTP错误
            ApiException.onException(context, BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            ApiException.onException(context, CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            ApiException.onException(context, CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            ApiException.onException(context, PARSE_ERROR);
        } else {
            ApiException.onException(context, UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        Log.d(TAG, "onCancelProgress: ");
        //截断信息，下游接受不到信息
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    /**
     * 服务器返回数据，但响应码不为200 或者 success 为false
     *
     * @param response 服务器返回的数据
     */

    private void onFail(T response) {
//        String message = response.getMessage();
//        if (TextUtils.isEmpty(message)) {
//            tips(context.getString(R.string.response_return_error));
//        } else {
//            tips(message);
//        }
    }

    private void tips(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
