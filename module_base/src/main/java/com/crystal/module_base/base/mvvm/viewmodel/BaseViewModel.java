package com.crystal.module_base.base.mvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.crystal.module_base.base.http.retrofit.ResponseMes;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.contract.LoadState;
import com.crystal.module_base.base.mvvm.model.StateModel;
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.observable.Observable;
import com.crystal.module_base.tools.observable.Observer;

/**
 * 创建者 kiylx
 * 创建时间 9/22/2020 11:09 PM
 * packageName：com.crystal.aplayer.base.mvvm.viewmodel
 * 描述：初始化时state：
 * 开始准备数据显示在界面上wait_load_data->成功：LOAD_FINISHED->数据显示在界面上表示加载完成done;
 *                                失败：LOAD_FAILED;
 * 刷新界面，刷新数据：
 * state：start_refresh->成功：load_finished->成功后置为初始状态nothing
 *                       失败：load_failed
 *                       初始化，没有在刷新状态为nothing
 *
 */
public abstract class BaseViewModel<D extends BaseDataProvider> extends ViewModel implements Observer {
    private static final String tag="BaseViewModel";
    protected D dataProvider = null;
    public StateModel stateModel;//持有页面状态，改变页面状态时可被各个activity或fragment监听到，以改变各自页面

    public BaseViewModel() {
        this.dataProvider = setDataProvider();
        if (null != dataProvider) {
            dataProvider.register(this);
        }
        if (stateModel == null) {
            stateModel = new StateModel();
            stateModel.loadDataState.setValue(LoadDataState.WAIT_LOAD_DATA);
        }
    }

    /**
     * 子类提供DataProvider实例
     */
    protected abstract D setDataProvider();

    /**
     * DataProvider更新或获取数据后，此方法可以监听到。
     * 若数据获取成功，由子类直接通过此方法获得的数据更新界面
     * 若获取数据失败，改变数据获取状态，以及， 将失败原因通知子类
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object... arg) {
        LogUtil.d(tag,"数据更新");
        if (arg[0] == null && arg[1] instanceof ResponseMes) {
            loadFailed((ResponseMes) arg[1]);
        } else
            whenUpdate(arg);
    }

    /**
     * 接收dataProvider的数据更新通知
     *
     * @param arg
     */
    protected abstract void whenUpdate(Object[] arg);

    /**
     * 获取网络数据失败，将失败原因推送至各观察者
     *
     * @param mes
     */
    protected abstract void loadFailed(ResponseMes mes);

    @Override
    protected void onCleared() {
        super.onCleared();
        dataProvider.unRegister(this);
        dataProvider = null;
    }
}
