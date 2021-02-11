package com.crystal.aplayer.module_base.base.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.CommonStateViewModel;
import com.crystal.aplayer.databinding.RefreshRvBinding;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/19 22:44
 * packageName：com.crystal.aplayer.module_base.base.ui.fragments
 * 描述：
 */
public abstract class RefreshFragment<VM1 extends CommonStateViewModel> extends LoadingRefreshFragment<VM1> {
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected View setContentLayout() {
        return setSmartRefreshLayout();
    }

    /**
     * @return 子类提供内容view, view被放在smartRefreshLayout中
     */
    public abstract View setContentView();

    @Override
    protected boolean enableDefaultRefreshLayout() {
        return false;
    }

    /**
     * @return 返回smartRefreshLayout
     */
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    protected SmartRefreshLayout setSmartRefreshLayout() {
        if (smartRefreshLayout!=null){
            return smartRefreshLayout;
        }
        /*smartRefreshLayout = new SmartRefreshLayout(getContext());
        smartRefreshLayout.setEnableLoadMore(true);// 设置是否启用上拉加载更多（默认启用）
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);// 设置在内容不满一页的时候，是否可以上拉加载更多

        smartRefreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容

        smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);// 设置是否在没有更多数据之后 Footer 跟随内容
        smartRefreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        smartRefreshLayout.setFooterHeight(153f);
        smartRefreshLayout.setFooterTriggerRate(0.6f);// 设置 触发加载距离 与 FooterHeight 的比率*/
        smartRefreshLayout= RefreshRvBinding.inflate(getLayoutInflater()).refreshLayout;
        return smartRefreshLayout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        addHeaderAndFooterAndContent(setHeader(), setContentView(), setFooter());
        observeRefreshLayout(setOnRefreshListener(), setOnLoadMoreListener());
        return v;
    }

    protected abstract OnRefreshListener setOnRefreshListener();

    protected abstract OnLoadMoreListener setOnLoadMoreListener();

    /**
     * @param onRefreshListener 监听刷新控件的事件
     */
    private void observeRefreshLayout(OnRefreshListener onRefreshListener, OnLoadMoreListener onLoadMoreListener) {
        if (onRefreshListener != null) {
            smartRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
        if (onLoadMoreListener != null) {
            smartRefreshLayout.setOnLoadMoreListener(onLoadMoreListener);
        }
        /*viewModel.stateModel.setLoadDataState(LoadDataState.LOAD_FAILED, false);
        Toast.makeText(getContext(), "上拉完成", Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "刷新控件获取失败", Toast.LENGTH_LONG).show();*/
    }

    protected <T extends RefreshHeader> T setHeader() {
        MaterialHeader header = new MaterialHeader(getContext());
        header.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue));
        return (T) header;
    }

    protected <T extends RefreshFooter> T setFooter() {
        ClassicsFooter footer = new ClassicsFooter(getContext());
        return (T) footer;
    }

    private <T1 extends RefreshHeader, T2 extends RefreshFooter> void addHeaderAndFooterAndContent(T1 header, View content, T2 footer) {
        int count = 0;

        if (header instanceof View) {
            smartRefreshLayout.addView((View) header, count);
            count += 1;
        }
        if (content != null) {
            smartRefreshLayout.addView(setContentView(), smartRefreshLayout.getChildCount()-1);
            count += 1;
        }
        if (footer instanceof View) {
            smartRefreshLayout.addView((View) footer, count);
            count += 1;
        }
    }
}
