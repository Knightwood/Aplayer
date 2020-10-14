package com.crystal.aplayer.all_module.home.discover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding;
import com.crystal.module_base.databinding.RecyclerviewFreshLayoutBinding;
import com.crystal.module_base.tools.LogUtil;

public class DiscoverFragment extends Fragment {
    private RecyclerviewFreshLayoutBinding discoverBinding;
    private static final String tag="DiscoverFragment";
    private DiscoverViewModel viewModel;

    public DiscoverFragment() {

    }

    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        observeData();
    }

    private void observeData() {
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
        viewModel.stateModel.loadDataState.observe(this, loadState -> {
            LogUtil.d(tag, loadState.toString());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        discoverBinding= RecyclerviewFreshLayoutBinding.inflate(getLayoutInflater());
        return discoverBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* discoverBinding.appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.freshData();
            }
        });*/
    }
}
