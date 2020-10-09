package com.crystal.module_base.base.mvvm.model;

import androidx.lifecycle.MutableLiveData;

import com.crystal.module_base.base.mvvm.contract.LoadState;
import com.crystal.module_base.tools.networkpack.NetworkLiveData;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 17:24
 * packageName：com.crystal.module_base.base.mvvm.model
 * 描述：
 */
public class StateModel {
    //数据载入状态
    public MutableLiveData<LoadState> loadStateLiveData;
    //网络状况
    public MutableLiveData<NetworkLiveData> networkStateLiveData;

    public StateModel() {
        loadStateLiveData=new MutableLiveData<>();
        networkStateLiveData=new MutableLiveData<>();
    }
}
