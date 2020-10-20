package com.crystal.module_base.base.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.module_base.R;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.viewmodel.BaseViewModel;
import com.crystal.module_base.databinding.LoadingFreshLayoutBinding;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/18 16:10
 * packageName：com.crystal.aplayer.base.fragment
 * 描述：
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    private static final String tag = "BaseFragment";

    protected VM viewModel;
    protected LoadingFreshLayoutBinding rootViewBinding;
    private View[] views;//0:loadingView;  1: errorView;  2:添加的视图
    private SmartRefreshLayout defaultRefreshLayout =null;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        views = new View[3];
    }

    /**
     * @return 子类提供ViewModel
     */
    protected abstract VM setViewModel();


    /**
     * @return 子类提供内容的布局
     */
    protected abstract View setChildLayout();

    /**
     * @return 默认使用刷新控件，子类可重写返回false禁用默认刷新控件，以及达到使用自定义刷新控件的效果
     * 默认返回true，将子类提供的内容布局放入默认的刷新控件中。
     */
    protected boolean enableDefaultRefreshLayout(){
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
        rootViewBinding = LoadingFreshLayoutBinding.inflate(inflater);
        FrameLayout rootView = (FrameLayout) rootViewBinding.getRoot();
        if (enableDefaultRefreshLayout()){
            ViewStub reFreshLayoutStub = rootViewBinding.getRoot().findViewById(R.id.default_RefreshLayout_stub);
            if (reFreshLayoutStub != null) {
                defaultRefreshLayout = (SmartRefreshLayout) reFreshLayoutStub.inflate();
            }
            defaultRefreshLayout.addView(setChildLayout());
            views[2] = defaultRefreshLayout;
            observeRefreshLayout(setOnRefreshListener(),setOnLoadMoreListener());
        }else{
            rootView.addView(setChildLayout(), rootView.getChildCount() - 1);
            views[2] = setChildLayout();
        }

        observeLoadState(viewModel);
        observeData(viewModel);
        return rootView;
    }

    /**
     * 使子类观察viewModel中的liveData
     *
     * @param viewModel 子类提供的viewModel
     */
    protected abstract void observeData(VM viewModel);

    /**
     * 重制loadDataState的状态，达到重试一次加载
     */
    protected void reTry() {
        viewModel.stateModel.setLoadDataState(LoadDataState.WAIT_LOAD_DATA, false);
    }

    /**
     * 观察loadDataState，更改加载数据界面
     */
    private void observeLoadState(VM vm) {
        vm.stateModel.getLoadDataState().observe(getViewLifecycleOwner(), this::observeLoadState);
    }

    /**
     * 子类可重写此方法更改默认界面在不同loadDataState下的样式
     *
     * @param loadDataState
     */
    private void observeLoadState(LoadDataState loadDataState) {
        switch (loadDataState) {

            case LOADING:
                ViewStub loadingStub = rootViewBinding.getRoot().findViewById(R.id.loading_view_stub3);
                if (loadingStub != null) {
                    views[0] = loadingStub.inflate();
                }
                setViewVisible(0);
                break;
            case LOAD_FAILED:
                ViewStub errorStub = rootViewBinding.getRoot().findViewById(R.id.error_view_stub);
                if (errorStub != null) {
                    views[1] = errorStub.inflate();
                }
                setViewVisible(1);
                //点击重试
                views[1].setOnClickListener(v -> {
                    reTry();
                });
                break;
            case WAIT_LOAD_DATA:
            case LOAD_FINISHED:
                setViewVisible(2);
                break;
            case DONE:
                break;
        }
    }

    private void setViewVisible(int i) {
        for (int j = 0; j < views.length; j++) {
            if (views[j] != null && j != i) {
                views[j].setVisibility(View.GONE);
            }
        }
        views[i].setVisibility(View.VISIBLE);

    }

    protected OnRefreshListener setOnRefreshListener(){
        return null;
    };

    protected OnLoadMoreListener setOnLoadMoreListener(){
        return null;
    };

    /**
     * @param onRefreshListener 监听刷新控件的事件
     */
    private void observeRefreshLayout(OnRefreshListener onRefreshListener, OnLoadMoreListener onLoadMoreListener) {
        if (onRefreshListener != null) {
            defaultRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
        if (onLoadMoreListener != null) {
            defaultRefreshLayout.setOnLoadMoreListener(onLoadMoreListener);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }}
