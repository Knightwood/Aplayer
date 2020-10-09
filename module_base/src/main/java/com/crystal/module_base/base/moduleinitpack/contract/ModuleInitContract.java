package com.crystal.module_base.base.moduleinitpack.contract;

import android.app.Application;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/17 14:44
 * packageName：com.crystal.moudle_base
 * 描述： 动态配置组件Application,有需要在application中初始化的组件，可以使用一个类实现该接口,统一在宿主app 的Application进行初始化
 */
public interface ModuleInitContract<T extends Application> {
    void beforeInit(T application);

    void afterInit(T application);
}
