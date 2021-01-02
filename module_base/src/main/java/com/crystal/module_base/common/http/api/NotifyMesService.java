package com.crystal.module_base.common.http.api;

import com.crystal.module_base.base.http.retrofit.Api;
import com.crystal.module_base.common.http.bean2.PushMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/30 19:46
 * packageName：com.crystal.module_base.base.http.api
 * 描述：
 */
public interface NotifyMesService extends Api {
    //2.通知请求地址 ：  http://baobab.kaiyanapp.com/api/v3/messages
    @GET
    Call<PushMessage> getPushMes(@Url String url);

    //通知
// 1.主题请求地址： http://baobab.kaiyanapp.com/api/v7/tag/tabList

    //3.互动请求地址 ：  http://baobab.kaiyanapp.com/api/v7/topic/list
}
