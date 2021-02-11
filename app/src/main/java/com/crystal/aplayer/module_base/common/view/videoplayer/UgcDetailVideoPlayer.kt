package com.crystal.aplayer.module_base.common.view.videoplayer
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.crystal.aplayer.R
import com.crystal.aplayer.module_base.common.util.gone
import com.crystal.aplayer.module_base.common.util.visible

import com.crystal.aplayer.module_base.tools.LogUtil
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * 社区-推荐详情页面对应的视频播放器。
 */
class UgcDetailVideoPlayer : StandardGSYVideoPlayer {

    /**
     *  是否第一次加载视频。用于隐藏进度条、播放按钮等UI。播放完成后，重新加载视频，会重置为true。
     */
    private var initFirstLoad = true

    constructor(context: Context) : super(context)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.layout_ugc_detail_video_player

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> imageView.setImageResource(R.drawable.ic_refresh_white_24dp)
                else -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
            }

        } else {
            super.updateStartImage()
        }
    }

    //正常
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        LogUtil.d(javaClass.simpleName, "changeUiToNormal")
        initFirstLoad = true
    }

    //准备中
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        LogUtil.d(javaClass.simpleName, "changeUiToPreparingShow")
        mBottomContainer.gone()
    }

    //播放中
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        LogUtil.d(javaClass.simpleName, "changeUiToPlayingShow")
        if (initFirstLoad) mBottomContainer.gone()
        initFirstLoad = false
    }

    //开始缓冲
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        LogUtil.d(javaClass.simpleName, "changeUiToPlayingBufferingShow")
        if (initFirstLoad) {
            mStartButton.gone()
            initFirstLoad = false
        } else {
            mStartButton.visible()
        }
    }

    //暂停
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
        LogUtil.d(javaClass.simpleName, "changeUiToPauseShow")
    }

    //自动播放结束
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        LogUtil.d(javaClass.simpleName, "changeUiToCompleteShow")
        mBottomContainer.gone()
    }

    //错误状态
    override fun changeUiToError() {
        super.changeUiToError()
        LogUtil.d(javaClass.simpleName, "changeUiToError")
    }

    fun getBottomContainer() = mBottomContainer
}