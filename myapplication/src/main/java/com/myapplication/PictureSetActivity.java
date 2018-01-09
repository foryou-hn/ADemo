package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.myapplication.bean.ResultItem;
import com.myapplication.bean.PictureSetItem;
import com.myapplication.net.ReqCallBack;
import com.myapplication.net.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PictureSetActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rv_type;
    private ImageView iv;
    private RequestManager requestManager;
    private String id;
    private int page = 1;
    private SwipeRefreshLayout refresh;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture_set);
        requestManager = RequestManager.getInstance(this);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        rv_type = (RecyclerView) findViewById(R.id.rv_type);

        initData();

    }

    private void initData() {
//        Glide.with(this)
//                .load(R.mipmap.banner1)
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(iv);

        rv_type.setLayoutManager(new GridLayoutManager(this, 2));
        refresh.setOnRefreshListener(this);
        loadData();

    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("showapi_appid", "48619");
        params.put("showapi_sign", "00cc32d2d2784d52ab30e785509e59de");
        params.put("type", id);
        params.put("page", page + "");
        params.put("rows", 10 + "");
        requestManager.requestAsyn("1208-2", RequestManager.TYPE_POST_JSON, params, new ReqCallBack<Object>() {
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
        final List<PictureSetItem> pictureSetItems= new ArrayList<>();
        for (ResultItem item : items) {
            String id = item.getString("id");
            String imgurl = item.getString("imgurl");
            String title = item.getString("title");
            pictureSetItems.add(new PictureSetItem(id, title, imgurl));
        }
        if (adapter == null) {
            adapter = new BaseQuickAdapter<PictureSetItem, BaseViewHolder>(R.layout.pic_set_item_view, pictureSetItems) {
                @Override
                protected void convert(BaseViewHolder helper, PictureSetItem item) {
                    helper.setText(R.id.text, item.title);
                    Glide.with(mContext)
                            .load(item.url)
                            .apply(RequestOptions.errorOf(R.mipmap.ic_launcher_round).centerCrop())
                            .into((ImageView) helper.getView(R.id.icon));
                }
            };
            adapter.setOnLoadMoreListener(this, rv_type);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(PictureSetActivity.this, PictureActivity.class);
                    intent.putExtra("id", pictureSetItems.get(position).id);
                    startActivity(intent);
                }
            });
            rv_type.setAdapter(adapter);
        } else {
            if (refresh.isRefreshing()) {
                adapter.setNewData(pictureSetItems);
                refresh.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            } else {
                adapter.addData(pictureSetItems);
                adapter.loadMoreComplete();
                refresh.setEnabled(true);
            }
        }
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData();
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        refresh.setEnabled(false);
        if (adapter.getData().size() < 10){
            adapter.loadMoreEnd(true);
        } else {
            page ++;
            loadData();
        }
    }
}
