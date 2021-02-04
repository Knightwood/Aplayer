package com.crystal.module_base.common.http.api;

import com.crystal.module_base.base.http.retrofit.Api;
import com.crystal.module_base.common.http.bean2.VideoBeanForClient;
import com.crystal.module_base.common.http.bean2.VideoDetail;
import com.crystal.module_base.common.http.bean2.VideoRelated;
import com.crystal.module_base.common.http.bean2.VideoReplies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/30 17:19
 * packageName：com.crystal.module_base.base.http
 * 描述：
 */
public interface VideoApiService extends Api {
    //获取视频详情
    @GET("v2/video/{id}")
    Call<VideoBeanForClient> getVideoBeanForClient(@Path("id") Long l);

    //相关推荐请求地址 ：http://baobab.kaiyanapp.com/api/v4/video/related?id=186856
    @GET("v4/video/related")
    Call<VideoRelated> getVideoRelated(@Query("id") Long l);

    // 评论 请求地址 ：http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=186856
    //@GET("v2/replies/video?videoId=186856")
    @GET
    Call<VideoReplies> getVideoReplies(@Url String str);

    public static final String VIDEO_REPLIES_URL = "http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=";

}
