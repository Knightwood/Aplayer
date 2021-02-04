package com.crystal.module_base.base.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.module_base.R;
import com.crystal.module_base.base.mvvm.model.StateModel;
import com.crystal.module_base.base.mvvm.state.DataRefreshState;
import com.crystal.module_base.base.mvvm.state.LoadDataState;
import com.crystal.module_base.base.mvvm.viewmodel.CommonStateViewModel;
import com.crystal.module_base.databinding.LoadingFreshLayoutBinding;
import com.crystal.module_base.tools.LogUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/18 16:10
 * packageName：com.crystal.aplayer.base.fragment
 * 描述：含有加载状态，刷新状态的fragment的base类
 */
public abstract class LoadingRefreshFragment<VM extends CommonStateViewModel> extends Fragment {
    private static final String tag = "BaseFragment";

    protected VM viewModel;
    protected LoadingFreshLayoutBinding loadingFreshLayoutBinding;//此fragmment的布局文件
    private View[] views;//暂存此fragment中的部分视图。0:loadingView;  1: errorView;  2:添加的视图
    protected SmartRefreshLayout defaultRefreshLayout = null;//默认刷新控件

    /**
     * @return 子类提供ViewModel
     */
    protected abstract VM setViewModel();

    /**
     * @return 子类提供内容的布局。
     * 若使用了自定义的刷新布局而不是默认的，则提供的内容layout应该以smartRefreshLayout作为根布局。
     */
    protected abstract View setContentLayout();

    /**
     * 使子类观察viewModel中的liveData，比如获取的数据之类的。
     *
     * @param viewModel 子类提供的viewModel
     */
    protected abstract void observeLiveData(VM viewModel);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        views = new View[3];
    }

    /**
     * @return 默认使用刷新控件，子类可重写返回false禁用默认刷新控件，以及达到使用自定义刷新控件的效果
     * 默认返回true，将子类提供的内容布局放入默认的刷新控件中。
     */
    protected boolean enableDefaultRefreshLayout() {
        return true;
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = setViewModel();
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingFreshLayoutBinding = LoadingFreshLayoutBinding.inflate(inflater);
        FrameLayout rootView = (FrameLayout) loadingFreshLayoutBinding.getRoot();

        if (enableDefaultRefreshLayout()) {//使用默认刷新控件
            ViewStub reFreshLayoutStub = loadingFreshLayoutBinding.getRoot().findViewById(R.id.default_RefreshLayout_stub);//带有刷新控件的布局
            if (reFreshLayoutStub != null) {
                defaultRefreshLayout = (SmartRefreshLayout) reFreshLayoutStub.inflate();
            }
            SmartRefreshLayout.LayoutParams params = new SmartRefreshLayout.LayoutParams(SmartRefreshLayout.LayoutParams.MATCH_PARENT, SmartRefreshLayout.LayoutParams.MATCH_PARENT);
            defaultRefreshLayout.addView(setContentLayout(), params);//把内容视图放进刷新布局里
            views[2] = defaultRefreshLayout;
        } else {//子类使用自定义刷新控件
            rootView.addView(setContentLayout(), rootView.getChildCount() - 1,setParams());//把内容视图放进根布局里，而不是默认的刷新布局里
            defaultRefreshLayout = (SmartRefreshLayout) setContentLayout();
            views[2] = setContentLayout();
        }
        observeLoadData(viewModel);
        observeLiveData(viewModel);
        initData(savedInstanceState);
        return rootView;
    }

    /**
     * @return 给添加的内容视图设置参数
     */
    public SmartRefreshLayout.LayoutParams setParams() {
        return new SmartRefreshLayout.LayoutParams(SmartRefreshLayout.LayoutParams.MATCH_PARENT, SmartRefreshLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeRefreshLayout();
    }

    /**
     * @param savedInstanceState 在onCreateView中被调用，初始化一些数据
     */
    @CallSuper
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 重置loadDataState的状态，重试一次加载数据
     */
    private void reTry() {
        viewModel.stateModel.setLoadDataState(LoadDataState.WAIT_LOAD_DATA, false);
        viewModel.firstLoadData();
    }

    protected void refreshComplete(DataRefreshState dataRefreshState) {
        switch (dataRefreshState) {
            case LOAD_FINISHED:
                showToast("刷新成功");
                break;
            case LOAD_FAILED:
                showToast("刷新失败");
                break;
        }
    }

    /**
     * 观察loadDataState，
     * 界面初始化时获取数据，显示加载画面，失败画面，加载数据成功后的画面；
     */
    private void observeLoadData(VM vm) {
        vm.stateModel.getLoadDataState().observe(getViewLifecycleOwner(), this::observeLoadDataState);
    }

    /**
     * 子类可重写此方法更改默认界面在不同loadDataState下的样式
     *
     * @param loadDataState
     */
    private void observeLoadDataState(LoadDataState loadDataState) {
        switch (loadDataState) {
            case WAIT_LOAD_DATA:
                break;
            case LOADING:
                ViewStub loadingStub = loadingFreshLayoutBinding.getRoot().findViewById(R.id.loading_view_stub3);
                if (loadingStub != null) {
                    views[0] = loadingStub.inflate();
                }
                setViewVisible(0);
                break;
            case LOAD_FAILED:
                ViewStub errorStub = loadingFreshLayoutBinding.getRoot().findViewById(R.id.error_view_stub);
                if (errorStub != null) {
                    views[1] = errorStub.inflate();
                }
                setViewVisible(1);
                //点击重试
                views[1].setOnClickListener(v -> reTry());
                break;
            case LOAD_FINISHED:
                setViewVisible(2);
                viewModel.stateModel.setLoadDataState(LoadDataState.DONE, false);
                break;
        }
    }

    /**
     * @param i 存放view的数组中，视图在数组中的位置
     *          i位置的视图会被显示，其余视图会被隐藏
     */
    private void setViewVisible(int i) {
        for (int j = 0; j < views.length; j++) {
            if (views[j] != null && j != i) {
                views[j].setVisibility(View.GONE);
            }
        }
        views[i].setVisibility(View.VISIBLE);

    }

    /**
     * 子类可重写
     * 监听刷新布局的动作
     */
    protected void observeRefreshLayout() {
        defaultRefreshLayout.setOnRefreshListener(refreshLayout -> {
            LogUtil.d(tag, "触发下拉刷新");
            viewModel.freshData();

        });
        defaultRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            LogUtil.d(tag, "触发上拉刷新");
            viewModel.loadMore();
            defaultRefreshLayout.finishRefresh(2000, true, false);
        });
    }

    /**
     * @param b           下拉/上拉刷新成功或是加载成功传入true，否则传入false
     *                    改变加载或刷新状态，从而重置视图状态，在获取数据并处理后，无论成功与否都要调用此方法重置视图状态
     * @param nowBehavior 当前执行的获取数据的动作
     */
    public void reSetPageState(boolean b, StateModel.NowBehavior nowBehavior) {
        //无论成功与否，关闭刷新控件动画
        if (defaultRefreshLayout.getState().isHeader || nowBehavior == StateModel.NowBehavior.Refreshing) {
            defaultRefreshLayout.finishRefresh(b);
        }
        if (defaultRefreshLayout.getState().isFooter || nowBehavior == StateModel.NowBehavior.LoadMoreData) {
            defaultRefreshLayout.finishLoadMore(b);
        }
        if (nowBehavior == StateModel.NowBehavior.InitLoad) {
            viewModel.stateModel.setLoadDataState(b ? LoadDataState.LOAD_FINISHED : LoadDataState.LOAD_FAILED, false);
        }
        viewModel.stateModel.nowBehavior = StateModel.NowBehavior.AllDone;//加载数据或是刷新结束，结束所有动作，无论成功与否
    }

    /**
     * 通知上拉刷新控件没有更多数据
     */
    public void notifyNoMoreData() {
        defaultRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    /**
     * @param mes  消息string
     * @param args 格式化mes的object
     */
    protected void showToast(String mes, Object... args) {
        String message = String.format(mes, args);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingFreshLayoutBinding = null;
    }
}