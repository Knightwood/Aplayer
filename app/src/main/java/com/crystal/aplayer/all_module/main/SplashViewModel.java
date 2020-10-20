package com.crystal.aplayer.all_module.main;

import androidx.lifecycle.ViewModel;

import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.contract.LoadState;
import com.crystal.module_base.base.mvvm.model.StateModel;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/11 15:31
 * packageName：com.crystal.aplayer.all_module.main
 * 描述：
 */
public class SplashViewModel extends ViewModel {
    public final long animateDuration=3000L;
    private StateModel stateModel;

    public SplashViewModel() {
        stateModel=new StateModel();
        stateModel.setLoadDataState(LoadDataState.WAIT_LOAD_DATA,false);
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public void setStateModel(LoadDataState state) {
        this.stateModel.setLoadDataState(state,false);
    }
}
