package com.crystal.module_base.common.http.api;

import com.crystal.module_base.base.http.retrofit.Api;
import com.crystal.module_base.common.http.bean.communitypage.CommunityFollowBean;
import com.crystal.module_base.common.http.bean.communitypage.CommunityRecBean;
import com.crystal.module_base.common.http.bean.mainpage.DiscoveryBean;
import com.crystal.module_base.common.http.bean.mainpage.DailyFeedBean;
import com.crystal.module_base.common.http.bean.mainpage.RecommendBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 创建者 kiylx
 * 创建时间 9/26/2020 10:27 PM
 * packageName：com.crystal.module_base.base.http
 * 描述：
 */
public interface MainPageApiService extends Api {
    //home
//1.发现 请求地址： http://baobab.kaiyanapp.com/api/v7/index/tab/discovery
    @GET
    Call<DiscoveryBean> getDiscovery(@Url String url);

    //2.每日推荐 请求地址： http://baobab.kaiyanapp.com/api/v5/index/tab/allRec
    @GET
    Call<RecommendBean> getRecommend(@Url String url);

    //3.日报精选 请求地址 ： http://baobab.kaiyanapp.com/api/v5/index/tab/feed
    @GET
    Call<DailyFeedBean> getDailyFeed(@Url String url);

    //社区
// 1.推荐 请求地址： http://baobab.kaiyanapp.com/api/v7/community/tab/rec
    @GET("v7/community/tab/rec")
    Call<CommunityRecBean> getCommunityRec();

    //2.关注 请求地址： http://baobab.kaiyanapp.com/api/v6/community/tab/follow
    @GET("v6/community/tab/follow")
    Call<CommunityFollowBean> getCommunityFollow();
}
