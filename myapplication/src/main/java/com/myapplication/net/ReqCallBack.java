package com.myapplication.net;

import com.myapplication.base.ResultItem;

/**
 * 文 件 名：ReqCallBack
 * 描    述：
 * 作    者：chenhao
 * 时    间：2017/10/25
 * 版    权：v1.0
 */

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(ResultItem result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}
