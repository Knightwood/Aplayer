package com.crystal.module_base.tools.live_data_bus.core;

import androidx.lifecycle.MutableLiveData;

/**
 * 创建者 kiylx
 * 创建时间 2020/12/29 17:02
 * packageName：com.crystal.module_base.tools.live_data_bus
 * 描述：修改observe方法，并使用自定义的ObserverWrapper替代原有ObserverWrapper。
 */
public class LiveDataMod<T> extends MutableLiveData<T> {
    public static final int START_VERSION = -1;
    private static int mVersion = START_VERSION;//自定义mVersion，取代liveData中对version值的控制

    @Override
    public void postValue(T value) {
        super.postValue(value);
        mVersion++;
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        mVersion++;
    }

    public int getVersion() {
        return mVersion;
    }
}
