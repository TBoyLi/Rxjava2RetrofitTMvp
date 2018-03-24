package com.redli.rxjava2retrofittmvp.http.rxjava;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.redli.rxjava2retrofittmvp.R;
import com.redli.rxjava2retrofittmvp.base.BaseResponse;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.BAD_NETWORK;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.CONNECT_ERROR;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.CONNECT_TIMEOUT;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.PARSE_ERROR;
import static com.redli.rxjava2retrofittmvp.http.rxjava.ApiException.UNKNOWN_ERROR;

/**
 * @author RedLi
 * @date 2018/3/21
 */

public class ProgressSubscriber<T extends BaseResponse> implements
        ProgressCancelListener, Observer<T> {

    private static final String TAG = ProgressSubscriber.class.getSimpleName();

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    private boolean isShowLoading;
    private Disposable disposable;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShowLoading) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowLoading = isShowLoading;
        this.mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
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
        if (isShowLoading) {
            showProgressDialog();
        }
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: ");
        /**
         * 这里的话是通过success 或者 code 来判断 可惜豆辫放回数据没有这个字段 就只能通多count来判断了
         */
        if (t.getCount() > 0 && mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t.getSubjects());
        } else {
            onFail(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.getMessage());
        dismissProgressDialog();
        if (e instanceof SocketException) {
            onException(context, CONNECT_ERROR);
        } else if (e instanceof HttpException) {
            //   HTTP错误
            onException(context, BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(context, CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(context, CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(context, PARSE_ERROR);
        } else {
            onException(context, UNKNOWN_ERROR);
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
        if (response.getCount() == 0 && mSubscriberOnNextListener != null) {
//            mSubscriberOnNextListener.onFail(context.getString(R.string.response_return_error));
            mSubscriberOnNextListener.onFail("暂无数据");
        }
//        else {
//            mSubscriberOnNextListener.onFail(message);
//        }
    }


    /**
     * 请求异常
     *
     * @param reason
     */
    private void onException(Context context, ApiException reason) {

        switch (reason) {
            case CONNECT_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_error));
                break;
            case CONNECT_TIMEOUT:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_timeout));
                break;
            case BAD_NETWORK:
                mSubscriberOnNextListener.onFail(context.getString(R.string.bad_network));
                break;
            case PARSE_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.parse_error));
                break;
            case UNKNOWN_ERROR:
            default:
                mSubscriberOnNextListener.onFail(context.getString(R.string.unknown_error));
                break;
        }
    }
}
