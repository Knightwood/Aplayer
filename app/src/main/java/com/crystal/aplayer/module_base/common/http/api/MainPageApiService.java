package com.crystal.aplayer.module_base.common.http.api;

import com.crystal.aplayer.module_base.base.http.retrofit.Api;
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend;
import com.crystal.aplayer.module_base.common.http.bean2.Daily;
import com.crystal.aplayer.module_base.common.http.bean2.Discovery;
import com.crystal.aplayer.module_base.common.http.bean2.Follow;
import com.crystal.aplayer.module_base.common.http.bean2.HomePageRecommend;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 创建者 kiylx
 * 创建时间 9/26/2020 10:27 PM
 * packageName：com.crystal.aplayer.module_base.base.http
 * 描述：
 */
public interface MainPageApiService extends Api {
    //home
//1.发现 请求地址： http://baobab.kaiyanapp.com/api/v7/index/tab/discovery
    @GET
    Call<Discovery> getDiscovery(@Url String url);

    //2.每日推荐 请求地址： http://baobab.kaiyanapp.com/api/v5/index/tab/allRec
    @GET
    Call<HomePageRecommend> getRecommend(@Url String url);

    //3.日报精选 请求地址 ： http://baobab.kaiyanapp.com/api/v5/index/tab/feed
    @GET
    Call<Daily> getDailyFeed(@Url String url);

    //社区
// 1.推荐 请求地址： http://baobab.kaiyanapp.com/api/v7/community/tab/rec
    @GET
    Call<CommunityRecommend> getCommunityRec(@Url String url);

    //2.关注 请求地址： http://baobab.kaiyanapp.com/api/v6/community/tab/follow
    @GET
    Call<Follow> getCommunityFollow(@Url String url);
}
