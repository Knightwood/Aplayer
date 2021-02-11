package com.crystal.aplayer.all_module.video_detail

import androidx.lifecycle.MutableLiveData
import com.crystal.aplayer.all_module.repo.VideoDetailProvider
import com.crystal.aplayer.module_base.base.http.retrofit.ParseIntercept
import com.crystal.aplayer.module_base.base.http.retrofit.ResponseMes
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.StateViewModel
import com.crystal.aplayer.module_base.common.http.api.VideoApiService
import com.crystal.aplayer.module_base.common.http.bean2.*
import com.crystal.aplayer.module_base.tools.LogUtil
import kotlin.jvm.internal.Intrinsics


class VideoDetailViewModel : StateViewModel<VideoDetailProvider>() {
    private val firstLoad = true
    private var initParams: InitParamsBundle? = null
    private var lastLoadMoreTime: Long = 0
    var nowResponseMes = MutableLiveData<ResponseMes>()
    var relatedDataList = MutableLiveData<List<VideoRelated.Item>>()
    var repliesDataList = MutableLiveData<List<VideoReplies.Item>>()
    var videoInfo = MutableLiveData<VideoInfo>()
     var replace = false//是否替换视频,用于onnewintent刷新界面
    private var relatedIntercept: RelatedIntercept = RelatedIntercept()
    private var replyIntercept: ReplyIntercept = ReplyIntercept()
    private var videoBeanIntercept: VideoBeanIntercept = VideoBeanIntercept()


    fun getReply(videoId: Long) {
        dataProvider.getVideoReplies(VideoApiService.VIDEO_REPLIES_URL + videoId, replyIntercept)
    }

    fun getVideoDetail(videoId: Long) {
        LogUtil.d(tag, "获取视频详情，推荐和回复")
        dataProvider.getVideoBeanForClient(videoId, videoBeanIntercept)
        dataProvider.getVideoRelated(videoId, relatedIntercept)
        dataProvider.getVideoReplies(VideoApiService.VIDEO_REPLIES_URL + videoId, replyIntercept)
    }

    fun getVideoRelatedAndReply(videoId: Long) {
        LogUtil.d(tag, "获取视频推荐和回复")
        dataProvider.getVideoRelated(videoId, relatedIntercept)
        dataProvider.getVideoReplies(VideoApiService.VIDEO_REPLIES_URL + videoId, replyIntercept)
    }

    fun loadMore() {
        val initParamsBundle = initParams
        if (initParamsBundle == null) {
            Intrinsics.throwNpe()
        }
        if (initParamsBundle != null) {
            getReply(initParamsBundle.videoId)
        }
    }

    fun initData(isReplace: Boolean) {
        if (isReplace) {
            replace = true
        }
        initData(initParams)
    }

    private fun initData(value: InitParamsBundle?) {
        if (value == null) {
            LogUtil.d(tag, "initParams是null")
        } else if (value.videoInfoData != null) {
            videoInfo.setValue(value.videoInfoData)
            getVideoRelatedAndReply(value.videoInfoData.videoId)
        } else {
            getVideoDetail(value.videoId)
        }
    }

    fun canLoadMore(): Boolean {
        val now = System.currentTimeMillis()
        if (lastLoadMoreTime - now > 1000.toLong()) {
            lastLoadMoreTime = now
            return true
        }
        lastLoadMoreTime = now
        return false
    }

    inner class RelatedIntercept : ParseIntercept {
        override fun intercept(vararg args: Any?) {
            val data = args[0]
            val mes = args[1]
            if ((data !is VideoRelated) || mes !is ResponseMes) {
                LogUtil.d(tag, "VideoRelatedIntercept:数据不正确")
                return
            }
            LogUtil.d(tag, "VideoRelatedIntercept:成功")
            if (!mes.hasError()) {
                val relatedDataList: MutableLiveData<List<VideoRelated.Item>> = relatedDataList
                relatedDataList.postValue(data.itemList)
                return
            }
            nowResponseMes.postValue(mes)
            return
        }

    }

    inner class ReplyIntercept : ParseIntercept {
        override fun intercept(vararg args: Any?) {
            val data = args[0]
            val mes = args[1]
            if ((data !is VideoReplies) || mes !is ResponseMes) {
                LogUtil.d(tag, "ReplyIntercept:数据不正确")
                return
            }
            LogUtil.d(tag, "ReplyIntercept:成功")
            if (!mes.hasError()) {
                val repliesDataList: MutableLiveData<List<VideoReplies.Item>> = repliesDataList
                repliesDataList.postValue(data.itemList)
                return
            }
            nowResponseMes.postValue(mes)
            return
        }

    }

    inner class VideoBeanIntercept : ParseIntercept {
        override fun intercept(vararg args: Any?) {
            val data = args[0]
            val mes = args[1]
            if ((data !is VideoBeanForClient) || mes !is ResponseMes) {
                LogUtil.d(tag, "VideoBeanIntercept:数据不正确")
                return
            }
            LogUtil.d(tag, "VideoBeanIntercept:成功")
            if (!mes.hasError()) {
                val video: MutableLiveData<VideoInfo> = videoInfo
                video.postValue(newVideoInfo(data))
                return
            }
            nowResponseMes.postValue(mes)
            return
        }

    }

    //打包启动videoDetailactivity时传入的参数
    data class InitParamsBundle(val videoId: Long, val videoInfoData: VideoInfo?)

    companion object {
        const val tag = "VideoDetailViewModel"
    }

    override fun setDataProvider(): VideoDetailProvider {
        return VideoDetailProvider.getInstance()
    }

    fun setInitParams(initParamsBundle: InitParamsBundle) {
        this.initParams=initParamsBundle
    }
}