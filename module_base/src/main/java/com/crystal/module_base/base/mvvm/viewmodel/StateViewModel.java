package com.crystal.module_base.base.mvvm.viewmodel;

import com.crystal.module_base.base.mvvm.model.StateModel;
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.module_base.base.mvvm.viewmodel.base.BaseViewModel;

public abstract class StateViewModel<D extends BaseDataProvider> extends BaseViewModel<D> {
    private static final String tag = "StateViewModel";
    public StateModel stateModel;//持有页面状态，改变页面状态时可被各个activity或fragment监听到，以改变各自页面


    public StateViewModel() {
        if (this.stateModel == null) {
            this.stateModel = new StateModel();
        }
    }

    @Override
    protected void whenUpdate(Object[] arg) {
    }

}
