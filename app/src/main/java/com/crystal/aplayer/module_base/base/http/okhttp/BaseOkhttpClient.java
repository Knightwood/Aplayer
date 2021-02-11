package com.crystal.aplayer.module_base.base.http.okhttp;

import okhttp3.OkHttpClient;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 16:18
 * packageName：com.crystal.aplayer.module_base.base.http
 * 描述：
 */
public class BaseOkhttpClient {
    private volatile static BaseOkhttpClient baseOkhttpClient;
    private OkHttpClient client;

    public static BaseOkhttpClient getInstance() {
        if (baseOkhttpClient==null){
            synchronized (BaseOkhttpClient.class){
                if (baseOkhttpClient==null){
                    baseOkhttpClient=new BaseOkhttpClient();
                }
            }
        }
        return baseOkhttpClient;
    }
    private BaseOkhttpClient(){
        if (client==null)
            client=new OkHttpClient();
        init();
    }

    private void init() {
    }

    public OkHttpClient getClient(){
        return client;
    }
}
