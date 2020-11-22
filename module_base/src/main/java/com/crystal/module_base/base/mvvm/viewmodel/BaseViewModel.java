package com.crystal.module_base.base.mvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.crystal.module_base.base.mvvm.model.StateModel;
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.module_base.tools.observable.Observable;
import com.crystal.module_base.tools.observable.Observer;

/**
 * 创建者 kiylx
 * 创建时间 9/22/2020 11:09 PM
 * packageName：com.crystal.aplayer.base.mvvm.viewmodel
 * 描述：基础viewmodel
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
        }
    }

    /**
     * 子类提供DataProvider实例
     */
    protected abstract D setDataProvider();

    /**
     * DataProvider更新或获取数据后，此方法可以监听到。
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object... arg) {
            whenUpdate(arg);
    }

    /**
     * 接收dataProvider的数据更新通知
     *
     * @param arg
     */
    protected abstract void whenUpdate(Object[] arg);

    @Override
    protected void onCleared() {
        super.onCleared();
        dataProvider.unRegister(this);
        dataProvider = null;
    }
}
