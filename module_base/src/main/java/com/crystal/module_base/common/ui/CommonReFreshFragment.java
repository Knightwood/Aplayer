package com.crystal.module_base.common.ui;

import android.view.View;

import androidx.lifecycle.Observer;

import com.crystal.module_base.base.mvvm.state.LoadDataState;
import com.crystal.module_base.base.ui.fragments.LoadingRefreshFragment;
import com.crystal.module_base.common.http.bean2.Discovery;
import com.crystal.module_base.common.vm.CommonViewModel;
import com.crystal.module_base.tools.LogUtil;

import static com.crystal.module_base.base.mvvm.state.DataRefreshState.LOAD_FAILED;
import static com.crystal.module_base.base.mvvm.state.DataRefreshState.LOAD_FINISHED;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/14 15:24
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
public abstract class CommonReFreshFragment<VM extends CommonViewModel> extends LoadingRefreshFragment<VM> {

    /*@Override
    protected VM setViewModel() {
        return null;
    }

    @Override
    protected View setContentLayout() {
        return null;
    }

    @Override
    protected void observeLiveData(VM viewModel) {
        viewModel.stateModel.getDataRefreshState().observe(getViewLifecycleOwner(), dataRefreshState -> {
            switch (dataRefreshState){
                case LOAD_FINISHED:
                    defaultRefreshLayout.finishRefresh(true);
                    break;
                case LOAD_FAILED:
                    defaultRefreshLayout.finishRefresh(false);
                    showToast("刷新失败");
                    break;
            }
        });
        viewModel.getFooterRefreshState().observe(getViewLifecycleOwner(),new Ob);
    }

    @Override
    public void reTryToLoadData() {
        viewModel.freshData();
    }

    @Override
    protected void observeRefreshLayout(){
        defaultRefreshLayout.setOnRefreshListener(refreshLayout -> {
            LogUtil.d(tag,"触发下拉刷新");
            viewModel.freshData();

        });
        defaultRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            LogUtil.d(tag, "触发上拉刷新");
            viewModel.loadMore();
        });
    }*/
}
