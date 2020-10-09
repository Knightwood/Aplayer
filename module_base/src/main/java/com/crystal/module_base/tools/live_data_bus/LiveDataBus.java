package com.crystal.module_base.tools.live_data_bus;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/5 19:29
 * packageName：com.crystal.module_base.tools.databus
 * 描述：
 */
public class LiveDataBus {
    private final Map<String, MutableLiveData<Object>> mutableLiveDataMap;

    private LiveDataBus() {
        this.mutableLiveDataMap = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus dataBus = new LiveDataBus();
    }

    public static LiveDataBus getInstance() {
        return SingletonHolder.dataBus;
    }

    /**
     * @param target 消息通道名称
     * @param type 消息通道的泛型类型
     * @param <T> 消息通道的泛型类
     * @return 返回消息通道
     */
    public <T> MutableLiveData<T> getChannel(String target,Class<T> type){
        if (!mutableLiveDataMap.containsKey(target)){
            mutableLiveDataMap.put(target,new MutableLiveData<>());
        }
        return (MutableLiveData<T>)mutableLiveDataMap.get(target);
    }
    public MutableLiveData<Object> getChannel(String target){
        return getChannel(target,Object.class);
    }













}
