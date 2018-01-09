package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.myapplication.adapter.PicTypeAdapter;
import com.myapplication.base.ResultItem;
import com.myapplication.bean.PictureTypeItem;
import com.myapplication.net.ReqCallBack;
import com.myapplication.net.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PictureTypeActivity extends AppCompatActivity {

    private RecyclerView rv_type;
    private ImageView iv;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        requestManager = RequestManager.getInstance(this);
        initView();
    }

    private void initView() {
        rv_type = (RecyclerView) findViewById(R.id.rv_type);

        initData();

    }

    private void initData() {
//        Glide.with(this)
//                .load(R.mipmap.banner1)
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(iv);

        rv_type.setLayoutManager(new LinearLayoutManager(this));
        loadData();

    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("showapi_appid", "48619");
        params.put("showapi_sign", "00cc32d2d2784d52ab30e785509e59de");
        requestManager.requestAsyn("1208-1", RequestManager.TYPE_POST_JSON, params, new ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(ResultItem result) {
                int resultCode = result.getInt("showapi_res_code");
                if (resultCode == 0){
                    ResultItem showapi_res_body = (ResultItem) result.get("showapi_res_body");
                    List<ResultItem> items = showapi_res_body.getItems("data");
                    showList(items);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void showList(List<ResultItem> items) {
        if (items.size() == 0) return;
        final List<PictureTypeItem> pictureTypeItems= new ArrayList<>();
        for (ResultItem item : items) {
            String id = item.getString("id");
            String title = item.getString("title");
            pictureTypeItems.add(new PictureTypeItem(id, title));
        }
        BaseQuickAdapter adapter = new PicTypeAdapter(R.layout.pic_type_item_view, pictureTypeItems);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(PictureTypeActivity.this, PictureSetActivity.class);
                intent.putExtra("id",pictureTypeItems.get(position).id);
                startActivity(intent);
            }
        });
        rv_type.setAdapter(adapter);
    }
}
