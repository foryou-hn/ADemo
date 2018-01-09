package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.myapplication.adapter.HomeAdapter;
import com.myapplication.bean.HomeItem;
import com.myapplication.bean.RollItem;
import com.myapplication.utils.GlideImageLoader;
import com.myapplication.view.BannerLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final Class<?>[] ACTIVITY = {PictureTypeActivity.class, TabLayoutActivity.class};
    private static final String[] TITLE = {"Glide", "TabLayout"};
    private static final int[] IMG = {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    private RecyclerView mRecyclerView;
    private BannerLayout banner;
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private LinearLayout ll_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        banner = (BannerLayout) findViewById(R.id.banner);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        initData();
    }

    private void initData() {
        initTitleBar();

        initBannerData();
        BaseQuickAdapter homeAdapter = new HomeAdapter(R.layout.home_item_view, getData());
        homeAdapter.openLoadAnimation();
//        View top = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);
//        BannerLayout bannerLayout = (BannerLayout) top.findViewById(R.id.banner);
//        homeAdapter.addHeaderView(top);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(homeAdapter);
    }

    private void initTitleBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(null);

//        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if( state == State.EXPANDED ) {
//                    //展开状态
//                    ll_title.setVisibility(View.INVISIBLE);
//                }else if(state == State.COLLAPSED){
//                    //折叠状态
//                    ll_title.setVisibility(View.VISIBLE);
//                }else {
//                    //中间状态
//                    ll_title.setVisibility(View.INVISIBLE);
//                }
//
//            }
//        });
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int total51 = totalScrollRange / 5;
                int total54 = total51 * 4;
                int offset = Math.abs(verticalOffset);
                if (offset >= total54) {
                    int off = offset - total54;
                    float i = (off * 1f) / total51;
                    ll_title.setAlpha(i);
                } else {
                    ll_title.setAlpha(0);
                }
            }
        });
    }

    private void initBannerData() {
        List<RollItem> items = new ArrayList<>();
        items.add(new RollItem("",R.mipmap.banner1,""));
        items.add(new RollItem("",R.mipmap.banner2,""));
        items.add(new RollItem("",R.mipmap.banner1,""));
        items.add(new RollItem("",R.mipmap.banner2,""));
        items.add(new RollItem("",R.mipmap.banner1,""));
        banner.setImageLoader(new GlideImageLoader());
        banner.setViewUrls(items);

        //添加监听事件
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ArrayList<HomeItem> getData() {
        ArrayList<HomeItem> mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            item.setImageResource(IMG[i]);
            mDataList.add(item);
        }
        return mDataList;
    }
}
