package com.crystal.module_base.tools.live_data_bus.utils;

import com.crystal.module_base.tools.live_data_bus.core.ObserverWrapperMod;

/**
 * 创建者 kiylx
 * 创建时间 2021/1/1 20:44
 * packageName：com.crystal.module_base.tools.live_data_bus.utils
 * 描述：
 */
public class PostTask implements Runnable {
    private Object[] newValue;
    private Method method;

    public PostTask(Object[] newValue, Method method) {
        this.newValue = newValue;
        this.method = method;
    }

    @Override
    public void run() {
        method.method(newValue);
    }

    public interface Method {
        void method(Object[] args);
    }
}
