package com.crystal.aplayer.module_base.common.ui;

import com.crystal.aplayer.module_base.base.ui.fragments.LoadingRefreshFragment;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.CommonStateViewModel;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/14 15:24
 * packageName：com.crystal.aplayer.module_base.common.ui
 * 描述：
 */
public abstract class CommonReFreshFragment<VM extends CommonStateViewModel> extends LoadingRefreshFragment<VM> {

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
