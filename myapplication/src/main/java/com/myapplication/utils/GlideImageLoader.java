package com.myapplication.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.myapplication.view.BannerLayout;

/**
 * 图片加载类
 * @author : chenhao
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView, int errImage) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.errorOf(errImage))
                .into(imageView);
    }
}
