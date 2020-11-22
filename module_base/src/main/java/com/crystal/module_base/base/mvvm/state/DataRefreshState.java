package com.crystal.module_base.base.mvvm.state;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/14 13:26
 * packageName：com.crystal.module_base.base.mvvm.model
 * 描述：
 * NOTHINF: viewmodel初始化时使用。
 * START_REFRESH: 开始刷新数据，
 * REFRESHING: 刷新中，
 * LOAD_FINISHED: 载入新数据成功，
 * LOAD_FAILED: 载入新数据失败,
 * DONE:无定义(可以用于初始化状态）
 */
public enum DataRefreshState {
    NOTHING, START_REFRESH, REFRESHING, LOAD_FINISHED, LOAD_FAILED, DONE
}
