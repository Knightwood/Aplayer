package com.crystal.module_base.common.mvvm;

import androidx.annotation.CallSuper;

import com.crystal.module_base.base.http.retrofit.ResponseMes;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.module_base.base.mvvm.viewmodel.BaseViewModel;
import com.crystal.module_base.tools.LogUtil;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/16 17:43
 * packageName：com.crystal.module_base.common.mvvm
 * 描述：统一各state流程
 * LoadDataState:初始化界面时获取数据的过程
 * 初始化时state：NOTHING->开始准备数据显示在界面上wait_load_data->成功：LOAD_FINISHED->数据显示在界面上显示完成加载，重置为done;
 * 失败：LOAD_FAILED;
 * 刷新界面，刷新数据DataRefreshState：
 * state：NOTHING->start_refresh->成功：load_finished->成功后重置为DONE
 * 失败：load_failed
 */
public abstract class CommonViewModel<D extends BaseDataProvider> extends BaseViewModel<D> {
    private static final String tag = "CommonViewModel";
    
    public CommonViewModel() {
        super();
        stateModel.setLoadDataState(LoadDataState.WAIT_LOAD_DATA,false);
    }

    /*
     * 若数据获取成功，由子类直接通过此方法获得的数据更新界面
     * 若获取数据失败，改变数据获取状态，以及， 将失败原因通知子类
     * */
    @Override
    protected void whenUpdate(Object[] arg) {
        LogUtil.d(tag, "数据更新");
        if (arg[0] == null && arg[1] instanceof ResponseMes) {
            getRemoteDataFailed((ResponseMes) arg[1]);
        } else {
            getRemoteDataSuccess(arg);
        }
    }

    @CallSuper
    protected void getRemoteDataSuccess(Object[] arg){
        stateModel.setLoadDataState(LoadDataState.LOAD_FINISHED,true);
    }

    /**
     * 获取网络数据失败，将失败原因推送至各观察者
     *
     * @param mes
     */
    @CallSuper
    protected void getRemoteDataFailed(ResponseMes mes){
        stateModel.setLoadDataState(LoadDataState.LOAD_FAILED,true);
    }
}
