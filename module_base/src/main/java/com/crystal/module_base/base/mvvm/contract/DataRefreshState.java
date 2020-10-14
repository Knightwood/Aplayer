package com.crystal.module_base.base.mvvm.contract;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/14 13:26
 * packageName：com.crystal.module_base.base.mvvm.model
 * 描述：开始刷新数据，刷新中，载入新数据成功，载入新数据失败,剩余两个无定义
 */
public enum DataRefreshState {
    START_REFRESH, REFRESHING, LOAD_FINISHED, LOAD_FAILED,NOTHING,DONE
}
