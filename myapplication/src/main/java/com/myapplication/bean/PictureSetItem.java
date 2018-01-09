package com.myapplication.bean;

/**
 * 文 件 名：PictureSetItem
 * 描    述：
 * 作    者：chenhao
 * 时    间：2017/10/26
 * 版    权：v1.0
 */

public class PictureSetItem {

    public String id;
    public String title;
    public String url;

    public PictureSetItem(String id, String title, String url) {
        this.id = id;
        this.url = url;
        this.title = title;
    }

}
