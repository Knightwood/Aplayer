package com.crystal.module_base.tools;

import android.app.Application;
import android.content.Context;

import com.crystal.module_base.tools.threadpool.SimpleThreadPool;

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
        try {
            initData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化全局数据
     */
    protected void initData() throws ClassNotFoundException {
    }

    public static Context getInstance() {
        return mContext;
    }
}
