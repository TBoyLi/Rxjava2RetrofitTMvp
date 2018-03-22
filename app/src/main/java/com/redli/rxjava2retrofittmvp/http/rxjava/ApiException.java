package com.redli.rxjava2retrofittmvp.http.rxjava;

import android.content.Context;
import android.widget.Toast;

import com.redli.rxjava2retrofittmvp.R;

/**
 * @author RedLi
 * @date 2018/3/21
 *
 * 请求失败的原因
 */

public class ApiException {

    public enum Exception {

        /** 解析数据失败 **/
        PARSE_ERROR,

        /** 网络问题 **/
        BAD_NETWORK,

        /** 连接错误 **/
        CONNECT_ERROR,

        /** 连接超时 **/
        CONNECT_TIMEOUT,

        /** section token past due 过期 **/
        TOKEN_PAST_DUE,

        /** 未知错误 **/
        UNKNOWN_ERROR,
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public static void onException(Context context, Exception reason) {

        switch (reason) {
            case CONNECT_ERROR:
                tips(context, context.getString(R.string.connect_error));
                break;
            case CONNECT_TIMEOUT:
                tips(context, context.getString(R.string.connect_timeout));
                break;
            case BAD_NETWORK:
                tips(context, context.getString(R.string.bad_network));
                break;
            case PARSE_ERROR:
                tips(context, context.getString(R.string.parse_error));
                break;
            case UNKNOWN_ERROR:
            default:
                tips(context, context.getString(R.string.unknown_error));
                break;
        }
    }

    private static void tips(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
