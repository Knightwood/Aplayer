package com.crystal.aplayer.all_module.community.follow;


import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding;
import com.crystal.module_base.base.ui.fragments.LoadingRefreshFragment;
import com.crystal.module_base.common.http.bean2.Follow;
import com.crystal.module_base.tools.LogUtil;

import java.util.List;


public class FollowFragment extends LoadingRefreshFragment<FollowViewModel> {

    private static final String tag = "CommunityFragment";
    private FollowAdapter adapter = null;
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
    protected FollowViewModel setViewModel() {
        return new ViewModelProvider(this).get(FollowViewModel.class);
    }

    @Override
    protected View setContentLayout() {
        this.binding = ModuleHomeFragmentDiscoverBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void observeLiveData(FollowViewModel viewModel) {
//监听网络数据
        viewModel.dataLists.observe(getViewLifecycleOwner(), this::initAdapter);
        viewModel.nowResponseMes.observe(getViewLifecycleOwner(), responseMes -> {
            // TODO: 2020/11/16 有错误的话展示错误消息，没有错误的话，不进行处理
            if (responseMes.hasError())
                showToast("获取数据失败");
            reSetPageState(false, viewModel.stateModel.nowBehavior);//重置所有视图和状态
        });
    }
    private void initAdapter(List<Follow.Item> list) {
        if (adapter == null) {
            adapter = new FollowAdapter(list, getActivity());
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
