package com.redli.rxjava2retrofittmvp.http.rxjava;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.redli.rxjava2retrofittmvp.R;

import java.lang.ref.WeakReference;

/**
 * @author RedLi
 * @date 2018/3/21
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog dialog = null;

    private Context context;
    private final WeakReference<Context> reference;
    private boolean outSizeCancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean outSizeCancelable) {
        super();
        this.reference = new WeakReference<>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.outSizeCancelable = outSizeCancelable;
    }

    private void create(){

        if (dialog == null) {
            context = reference.get();

            dialog = new Dialog(context, R.style.LoadStyle);
            View dialogView = LayoutInflater.from(context).inflate(
                    R.layout.dialog_custom_load, null);
            dialog.setCanceledOnTouchOutside(outSizeCancelable);
            dialog.setCancelable(outSizeCancelable);
            dialog.setContentView(dialogView);
            if (outSizeCancelable) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mProgressCancelListener != null) {
                            mProgressCancelListener.onCancelProgress();
                        }
                    }
                });
            }
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_VERTICAL
                    | Gravity.CENTER_HORIZONTAL);
        }

        if (!dialog.isShowing() && context != null) {
            dialog.show();
        }
    }

    public void show() {
        create();
    }

    public void dismiss() {
        context = reference.get();
        if (dialog != null && dialog.isShowing() && !((Activity) context).isFinishing()) {
            String name = Thread.currentThread().getName();
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                show();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismiss();
                break;
            default:
                break;
        }
    }
}
