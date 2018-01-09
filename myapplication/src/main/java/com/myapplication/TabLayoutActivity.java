package com.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapplication.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenhao
 */
public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        initData();
    }

    private void initData() {
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < 3; i++) {
            tabIndicators.add("Tab" + i);
            tabFragments.add(PageFragment.newInstance(i));
        }
        mViewPager.setAdapter(contentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//设置TabLayout与ViewPager同步

        for (int i = 0; i < tabIndicators.size(); i++) {
            mTabLayout.getTabAt(i).setCustomView(contentPagerAdapter.getTabView(i));
        }

        View view = mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.textView);
        startScaleXY(view, 0, 1f, 1.2f);//初始动画

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() ==null) return;
                View view = tab.getCustomView().findViewById(R.id.textView);
                startScaleXY(view, 300, 1f, 1.2f);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() ==null) return;
                View view = tab.getCustomView().findViewById(R.id.textView);
                startScaleXY(view, 300, 1.2f, 1f);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 开始缩放动画
     * @param view
     * @param duration
     * @param startValue
     * @param endValue
     */
    private void startScaleXY(View view, long duration, float startValue, float endValue) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", startValue, endValue);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", startValue, endValue);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(duration);
        set.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//菜单功能
        getMenuInflater().inflate(R.menu.menu_tab_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tab_add:
                tabIndicators.add("Tab " + tabIndicators.size());
                tabFragments.add(PageFragment.newInstance(tabIndicators.size()-1));
                contentPagerAdapter.notifyDataSetChanged();//刷新adapter
                for (int i = 0; i < tabIndicators.size(); i++) {
                    mTabLayout.getTabAt(i).setCustomView(contentPagerAdapter.getTabView(i));//将自定义布局加入到TabLayout中
                }

                View view = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.textView);
                startScaleXY(view, 0, 1f, 1.2f);//初始动画
                return true;

            case R.id.tab_mode_fixed:
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                return true;

            case R.id.tab_mode_scrollable:
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 内容页adapter
     */
    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return tabIndicators.get(position);
            return null;
        }

        public View getTabView(int position){
            View view = LayoutInflater.from(TabLayoutActivity.this).inflate(R.layout.tab_item, null);
            TextView tv= (TextView) view.findViewById(R.id.textView);
            tv.setText(tabIndicators.get(position));
            //控件宽度放大1.2倍，执行动画时不会超出边界
            float measureText = tv.getPaint().measureText(tabIndicators.get(position));
            tv.setWidth((int) (measureText * 1.2));
            ImageView img = (ImageView) view.findViewById(R.id.imageView);
            img.setImageResource(R.mipmap.ic_launcher_round);
            return view;
        }

    }

}
