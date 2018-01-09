package com.myapplication.bean;

/**
 * 图片集bean类
 * @author : chenhao
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
