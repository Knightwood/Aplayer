package com.crystal.module_base.base.mvvm.state;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/14 17:56
 * packageName：com.crystal.module_base.base.mvvm.contract
 * 描述：
 * NOTHINF: viewmodel初始化时使用。
 * WAIT_LOAD_DATA: 待加载数据，
 * START_LOAD: 开始尝试加载数据，
 * LOADING: 加载中，
 * LOAD_FINISHED: 加载完成，
 * LOAD_FAILED: 加载失败，
 * DONE: 无定义（可以用于初始化状态）
 */
public enum LoadDataState {
    NOTHING, WAIT_LOAD_DATA, START_LOAD, LOADING, LOAD_FINISHED, LOAD_FAILED,DONE,

    NETWORK_LOST, NETWORK_READY, NETWORK_ERROR, UNKNOW_ERROR
}
