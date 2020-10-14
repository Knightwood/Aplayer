package com.crystal.module_base.base.mvvm.contract;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/14 17:56
 * packageName：com.crystal.module_base.base.mvvm.contract
 * 描述：
 */
public enum LoadDataState {
    WAIT_LOAD_DATA, START_LOAD, LOADING, LOAD_FINISHED, LOAD_FAILED,
    NETWORK_LOST, NETWORK_READY, NETWORK_ERROR, UNKNOW_ERROR,
    NOTHING,DONE
}
