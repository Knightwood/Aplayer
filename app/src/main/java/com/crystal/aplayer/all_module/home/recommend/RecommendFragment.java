package com.crystal.aplayer.all_module.home.recommend;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.crystal.aplayer.all_module.home.recommend.adapter.RecommendAdapter;
import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding;
import com.crystal.aplayer.module_base.base.ui.fragments.LoadingRefreshFragment;
import com.crystal.aplayer.module_base.common.http.bean2.HomePageRecommend;
import com.crystal.aplayer.module_base.tools.LogUtil;

import java.util.List;


public class RecommendFragment extends LoadingRefreshFragment<RecommendViewModel> {

    private static final String tag = "DiscoverFragment";
    private RecommendAdapter adapter = null;
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
    protected void observeLiveData(RecommendViewModel viewModel) {
        //监听网络数据
        viewModel.dataLists.observe(getViewLifecycleOwner(), this::initAdapter);
        viewModel.nowResponseMes.observe(getViewLifecycleOwner(), responseMes -> {
            // TODO: 2020/11/16 有错误的话展示错误消息，没有错误的话，不进行处理
            if (responseMes.hasError())
                showToast("获取数据失败");
            // TODO: 2020/11/25 没有更多数据，设置上拉刷新控件状态

            reSetPageState(false, viewModel.stateModel.nowBehavior);//重置所有视图和状态
        });
    }
    private void initAdapter(List<HomePageRecommend.Item> list) {
        if (adapter == null) {
            adapter = new RecommendAdapter(list, getActivity());
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
    protected RecommendViewModel setViewModel() {
        return new ViewModelProvider(this).get(RecommendViewModel.class);
    }

    @Override
    protected View setContentLayout() {
        this.binding = ModuleHomeFragmentDiscoverBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
