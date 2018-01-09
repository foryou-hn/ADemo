package com.myapplication.net;

import com.myapplication.bean.ResultItem;

/**
 * 请求数据回调接口
 * @author : chenhao
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
