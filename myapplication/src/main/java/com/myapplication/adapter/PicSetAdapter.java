package com.myapplication.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.myapplication.R;
import com.myapplication.bean.HomeItem;
import com.myapplication.bean.PictureSetItem;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PicSetAdapter extends BaseQuickAdapter<PictureSetItem, BaseViewHolder> {
    public PicSetAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PictureSetItem item) {
        helper.setText(R.id.text, item.title);
        Glide.with(mContext)
             .load(item.url)
             .apply(RequestOptions.errorOf(R.mipmap.ic_launcher_round).centerCrop())
             .into((ImageView) helper.getView(R.id.icon));
    }
}
