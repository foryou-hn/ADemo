package com.myapplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.myapplication.R;
import com.myapplication.bean.HomeItem;
import com.myapplication.bean.PictureTypeItem;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PicTypeAdapter extends BaseQuickAdapter<PictureTypeItem, BaseViewHolder> {
    public PicTypeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PictureTypeItem item) {
        helper.setText(R.id.text, item.title);
    }
}
