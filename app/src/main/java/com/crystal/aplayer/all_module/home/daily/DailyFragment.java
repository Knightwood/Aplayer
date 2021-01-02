package com.crystal.aplayer.all_module.home.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.home.discover.DiscoverAdapter;
import com.crystal.aplayer.all_module.home.discover.DiscoverViewModel;
import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding;
import com.crystal.module_base.base.ui.fragments.LoadingRefreshFragment;
import com.crystal.module_base.common.http.bean2.Daily;
import com.crystal.module_base.common.http.bean2.Discovery;
import com.crystal.module_base.tools.LogUtil;

import java.util.List;


public class DailyFragment extends LoadingRefreshFragment<DailyViewModel> {
    private static final String tag = "DailyFragment";
    private DailyAdapter adapter = null;
    private RecyclerView recyclerView;
    private ModuleHomeFragmentDiscoverBinding binding;

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel.firstLoad) {
            viewModel.firstLoad = false;
            viewModel.firstLoadData();
        }
    }
    @Override
    protected DailyViewModel setViewModel() {
        return new ViewModelProvider(this).get(DailyViewModel.class);
    }

    @Override
    protected View setContentLayout() {
        this.binding = ModuleHomeFragmentDiscoverBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void observeLiveData(DailyViewModel viewModel) {
//监听网络数据
        viewModel.dataLists.observe(getViewLifecycleOwner(), this::initAdapter);
        viewModel.nowResponseMes.observe(getViewLifecycleOwner(), responseMes -> {
            // TODO: 2020/11/16 有错误的话展示错误消息，没有错误的话，不进行处理
            if (responseMes.hasError())
                showToast("获取数据失败");
            reSetPageState(false, viewModel.stateModel.nowBehavior);//重置所有视图和状态
        });
    }
    private void initAdapter(List<Daily.Item> list) {
        if (adapter == null) {
            adapter = new DailyAdapter(list, getActivity());
            recyclerView = binding.recyclerviewRoot;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            LogUtil.d(tag, "获取数据成功-initadapter");
        } else {
            if (defaultRefreshLayout.getState().isHeader) {
                adapter.removeOldData(list);
            } else {
                adapter.addMoreData(list);
            }
            LogUtil.d(tag, "获取数据成功-addmoredata");
        }
        reSetPageState(true, viewModel.stateModel.nowBehavior);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
