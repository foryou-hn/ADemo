package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.myapplication.bean.ResultItem;
import com.myapplication.net.ReqCallBack;
import com.myapplication.net.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author : chenhao
 */
public class PictureActivity extends AppCompatActivity {

    private RecyclerView rv_type;
    private ImageView iv;
    private RequestManager requestManager;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        requestManager = RequestManager.getInstance(this);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        rv_type = (RecyclerView) findViewById(R.id.rv_type);

        initData();

    }

    private void initData() {
        rv_type.setLayoutManager(new LinearLayoutManager(this));
        loadData();

    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("showapi_appid", "48619");
        params.put("showapi_sign", "00cc32d2d2784d52ab30e785509e59de");
        params.put("id", id);
        requestManager.requestAsyn("1208-3", RequestManager.TYPE_POST_JSON, params, new ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(ResultItem result) {
                int resultCode = result.getInt("showapi_res_code");
                if (resultCode == 0){
                    ResultItem showapi_res_body = (ResultItem) result.get("showapi_res_body");
                    ArrayList items = (ArrayList) showapi_res_body.get("data");
                    showList(items);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void showList(ArrayList items) {
        if (items.size() == 0) return;
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.pic_item_view, items) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                Glide.with(mContext)
                        .load(item)
                        .apply(RequestOptions.errorOf(R.mipmap.ic_launcher_round).centerCrop())
                        .into((ImageView) helper.getView(R.id.icon));
            }
        };
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rv_type.setAdapter(adapter);
    }
}
