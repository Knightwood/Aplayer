package com.crystal.aplayer.all_module.repo

import com.crystal.aplayer.AppApplication
import com.crystal.module_base.base.http.retrofit.ParseIntercept
import com.crystal.module_base.base.http.retrofit.RetrofitConfig
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider
import com.crystal.module_base.base.mvvm.viewmodel.StateViewModel
import com.crystal.module_base.common.http.AllApiConfig
import com.crystal.module_base.common.http.api.VideoApiService
import com.crystal.module_base.common.http.bean2.VideoBeanForClient
import com.crystal.module_base.common.http.bean2.VideoRelated
import com.crystal.module_base.common.http.bean2.VideoReplies
import retrofit2.http.Url
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import kotlin.jvm.internal.Intrinsics


class VideoDetailProvider : BaseDataProvider<VideoApiService, StateViewModel<*>>() {
    init {
       retrofitConfig= RetrofitConfig.getInstance((AppApplication.getInstance() as AppApplication).threadPool.executorService)
        apiService=retrofitConfig.getApiInstance(VideoApiService::class.java, AllApiConfig.BASE_URL)
    }
    override fun getData(): MutableList<VideoApiService> {
        TODO("Not yet implemented")
    }

    fun getVideoBeanForClient(videoId: Long, intercept: ParseIntercept?) {
        retrofitConfig.parsingCallgetData(apiService.getVideoBeanForClient(videoId), VideoBeanForClient::class.java, intercept)
    }

    fun getVideoRelated(videoId: Long, intercept: ParseIntercept?) {
        retrofitConfig.parsingCallgetData(apiService.getVideoRelated(videoId), VideoRelated::class.java, intercept)
    }

    fun getVideoReplies(@Url url: String?, intercept: ParseIntercept?) {
        retrofitConfig.parsingCallgetData(apiService .getVideoReplies(url), VideoReplies::class.java, intercept)
    }

    companion object{
        private var mInstance: VideoDetailProvider? = null
            get() {
                return field ?: VideoDetailProvider()
            }

        @JvmStatic
        @Synchronized
        fun getInstance(): VideoDetailProvider{
            return requireNotNull(mInstance)
        }
    }
}