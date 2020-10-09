package com.crystal.module_base.base.mvvm.contract;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 17:25
 * packageName：com.crystal.module_base.base.mvvm.model
 * 描述：描述数据载入状态，view层根据状态加载不同view。
 *
 * 加载过程描述为：1.准备特定环境加载数据
 *              2.准备加载数据环境已经初始化完成，这时进入到开始和加载数据阶段
 *
 * 数据的载入描述为：
 * 开始准备载入，准备中，准备完成等待载入数据，准备过程失败
 * 开始载入，正在载入，载入成功，载入失败，
 *
 * 几种网络错误的描述
 * 失去网络连接，网络已连接，网络错误，
 * 未知错误
 */
public enum LoadState {
    START_PREPARING, PREPARING,READY_LOAD,PREPARING_FAILED,
    START_LOAD, LOADING, LOAD_FINISHED, LOAD_FAILED,
    NETWORK_LOST, NETWORK_READY, NETWORK_ERROR, UNKNOW_ERROR
}
