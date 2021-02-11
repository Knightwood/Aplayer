package com.crystal.aplayer.module_base.base.http.retrofit;

/**
 * viewmodel实现此接口,从dataprovider中拦截数据,而不是等待dataprovider遍历所有观察者推送数据
 */
public interface ParseIntercept {
    void intercept(Object... args);
}
