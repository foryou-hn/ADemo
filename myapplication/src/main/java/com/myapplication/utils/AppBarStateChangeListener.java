package com.myapplication.utils;

import android.support.design.widget.AppBarLayout;

/**
 * 文 件 名：AppBarStateChangeListener
 * 描    述：
 * 作    者：chenhao
 * 时    间：2017/10/24
 * 版    权：v1.0
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}