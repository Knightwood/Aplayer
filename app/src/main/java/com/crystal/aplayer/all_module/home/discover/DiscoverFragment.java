package com.crystal.aplayer.all_module.home.discover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;

import com.crystal.aplayer.R;
import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.ui.fragments.BaseFragment;
import com.crystal.module_base.base.ui.fragments.RefreshFragment;
import com.crystal.module_base.tools.LogUtil;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class DiscoverFragment extends BaseFragment<DiscoverViewModel> {
    private static final String tag="DiscoverFragment";

    public DiscoverFragment() {

    }

    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void observeData(DiscoverViewModel viewModel) {
        //监听网络数据
        viewModel.discoveryBeanMutableLiveData.observe(this, discoveryBean -> {
            if (discoveryBean != null) {
                viewModel.dataList.setValue(discoveryBean.getItemList());
                viewModel.nextPage.setValue((String) discoveryBean.getNextPageUrl());
                LogUtil.d(tag, "下一页地址："+(String) discoveryBean.getNextPageUrl());
            }else {
                LogUtil.d(tag, "response是null");
            }
        });
        //监听数据获取状态
        viewModel.stateModel.getDataFreshState().observe(this, dataRefreshState -> {

        });
    }


    @Override
    protected DiscoverViewModel setViewModel() {
        return new ViewModelProvider(this).get(DiscoverViewModel.class);
    }

    @Override
    protected View setChildLayout() {
        return ModuleHomeFragmentDiscoverBinding.inflate(getLayoutInflater()).getRoot();
    }

    @Override
    protected OnRefreshListener setOnRefreshListener() {
        return refreshLayout -> {
            viewModel.stateModel.setLoadDataState(LoadDataState.LOADING,false);
        };
    }

    @Override
    protected OnLoadMoreListener setOnLoadMoreListener() {
        return refreshLayout -> {
            viewModel.stateModel.setLoadDataState(LoadDataState.LOAD_FINISHED,false);
        };
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button= rootViewBinding.getRoot().findViewById(R.id.app_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.stateModel.setLoadDataState(LoadDataState.LOAD_FAILED,false);
            }
        });
    }
}
