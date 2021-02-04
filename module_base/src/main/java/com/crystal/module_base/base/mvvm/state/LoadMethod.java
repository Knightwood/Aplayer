package com.crystal.module_base.base.mvvm.state;

/**
 * viewmodel使用这些方法,以此统一加载数据的流程
 */
public interface LoadMethod {

    boolean canLoadMore();

    void firstLoadData();

    void freshData();

    void loadMore();
}
