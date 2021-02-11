package com.crystal.aplayer.module_base.base.mvvm.model;

import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.module_base.base.mvvm.state.DataRefreshState;
import com.crystal.aplayer.module_base.base.mvvm.state.LoadDataState;
import com.crystal.aplayer.module_base.tools.networkpack.NetworkLiveData;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 17:24
 * packageName：com.crystal.aplayer.module_base.base.mvvm.model
 * 描述：存放几个常用的状态
 *
 * 刷新界面，刷新数据DataRefreshState：
 *  * state：NOTHING->start_refresh->成功：load_finished->成功后重置为DONE
 *  * 失败：load_failed
 */
public class StateModel {
    //数据载入后，获取新数据刷新界面时的状态
    private MutableLiveData<DataRefreshState> dataRefreshState;//刷新数据
    //网络状况
    private MutableLiveData<NetworkLiveData> networkStateLiveData;
    //刚打开界面时数据载入状态
    private MutableLiveData<LoadDataState> loadDataState;
    public NowBehavior nowBehavior;

    public StateModel() {
        networkStateLiveData = new MutableLiveData<>();
        dataRefreshState = new MutableLiveData<>();
        loadDataState = new MutableLiveData<>();
        init();
    }

    private void init() {
        dataRefreshState.setValue(DataRefreshState.NOTHING);
        loadDataState.setValue(LoadDataState.NOTHING);
        nowBehavior=NowBehavior.NONE;
    }

    /**
     * @param state  状态
     * @param isPost 是否使用postValue在子线程推送数据
     */
    public void setDataFreshState(DataRefreshState state, boolean isPost) {
        if (isPost) {
            this.dataRefreshState.postValue(state);
        } else {
            this.dataRefreshState.setValue(state);
        }
    }

    /**
     * @param state  状态
     * @param isPost 是否使用postValue在子线程推送数据
     */
    public void setLoadDataState(LoadDataState state, boolean isPost) {
        if (isPost) {
            this.loadDataState.postValue(state);
        } else {
            this.loadDataState.setValue(state);
        }
    }

    public MutableLiveData<DataRefreshState> getDataRefreshState() {
        return dataRefreshState;
    }

    public MutableLiveData<NetworkLiveData> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public MutableLiveData<LoadDataState> getLoadDataState() {
        return loadDataState;
    }
    public enum NowBehavior {
        //来自下拉刷新，来自上拉加载更多，来自初始化第一次加载,完成所有动作
        Refreshing, LoadMoreData, InitLoad,AllDone,NONE
    }
}
