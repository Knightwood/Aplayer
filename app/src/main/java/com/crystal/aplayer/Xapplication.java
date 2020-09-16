package com.crystal.aplayer;

import android.app.Application;
import android.content.Context;


/**
 * 创建者 kiylx
 * 创建时间 2020/9/9 22:36
 * packageName：com.crystal.myplayer
 * 描述：
 */
public class Xapplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();
    }

    /**
     * 初始化全局数据
     */
    private void initData() {

    }

    public static Context getInstance() {
        return mContext;
    }
}
