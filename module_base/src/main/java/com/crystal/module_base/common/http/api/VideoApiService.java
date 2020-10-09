package com.crystal.module_base.common.http.api;

import com.crystal.module_base.base.http.retrofit.Api;
import com.crystal.module_base.common.http.bean.video.VideoRelated;
import com.crystal.module_base.common.http.bean.video.VideoReplies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/30 17:19
 * packageName：com.crystal.module_base.base.http
 * 描述：
 */
public interface VideoApiService extends Api {
    //@GET("api/v2/video/{id}")

    //视频详情页
//1.相关推荐请求地址 ：http://baobab.kaiyanapp.com/api/v4/video/related?id=186856
    @GET("v4/video/related?id=186856")
    Call<Response<VideoRelated>> getVideoRelated();

    /*
    |参数说明 |说明 |是否必须 |默认值 |
    |-|-|-|-|
    |id|当前播放视频的id，从跳转页面视频item中获取|是|无|
    */
//2. 评论 请求地址 ：http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=186856
    @GET("v2/replies/video?videoId=186856")
    Call<Response<VideoReplies>> getVideoReplies();
}
