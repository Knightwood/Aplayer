package com.crystal.module_base.base;


import com.crystal.module_base.tools.Xapplication;



/**
 * 创建者 kiylx
 * 创建时间 2020/9/17 16:20
 * packageName：com.crystal.aplayer.base
 * 描述：
 */
public class BaseApplication extends Xapplication {
    @Override
    protected void initData() throws ClassNotFoundException {
        super.initData();
        /*LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();*/
    }
}
