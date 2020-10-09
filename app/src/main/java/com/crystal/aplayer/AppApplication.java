package com.crystal.aplayer;

import android.content.Context;

import com.crystal.module_base.base.BaseApplication;
import com.crystal.module_base.base.moduleinitpack.ModuleInitManager;
import com.crystal.module_base.tools.threadpool.SimpleThreadPool;


/**
 * 创建者 kiylx
 * 创建时间 2020/9/9 22:36
 * packageName：com.crystal.myplayer
 * 描述：
 */
public class AppApplication extends BaseApplication {
    private static Context mContext;
    private SimpleThreadPool threadPool;

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
// 初始化需要初始化的组件
        //ModuleInitManager.getInstance().initModule(this);
        threadPool=SimpleThreadPool.getInstance();
    }

    public static Context getInstance() {
        return mContext;
    }

    public SimpleThreadPool getThreadPool() {
        return threadPool;
    }
}
