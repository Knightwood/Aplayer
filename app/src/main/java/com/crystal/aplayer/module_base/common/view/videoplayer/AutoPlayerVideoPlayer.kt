package com.crystal.aplayer.module_base.common.view.videoplayer

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.crystal.aplayer.R
import com.crystal.aplayer.module_base.common.util.load
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

/**
 * 创建者 kiylx
 * 创建时间 2020/11/7 9:54
 * packageName：com.crystal.aplayer.module_base.common.view
 * 描述：
 */
class AutoPlayerVideoPlayer : StandardGSYVideoPlayer {
    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.layout_auto_play_video_player
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun updateStartImage() {
        super.updateStartImage()
    }

    override fun touchDoubleUp() {
        //super.touchDoubleUp();
        //不需要双击暂停
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
    }

    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
    }

    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
    }

    override fun changeUiToError() {
        super.changeUiToError()
    }
}

fun startAutoPlay(activity: Activity,
                  player: GSYVideoPlayer,
                  position: Int,
                  playUrl: String,
                  coverUrl: String,
                  playTag: String,
                  callBack: GSYSampleCallBack? = null) {
    player.run {
        setPlayTag(playTag)//tag和position是为了，防止播放错位，与播放过程无关
        playPosition = position
        isReleaseWhenLossAudio = false//音频焦点冲突时是否释放
        isLooping = true //设置循环播放
        val cover = ImageView(activity)//增加封面
        cover.scaleType = ImageView.ScaleType.CENTER_CROP
        cover.load(coverUrl, 4f)
        cover.parent?.run { removeView(cover) }
        thumbImageView = cover
        setVideoAllCallBack(callBack)//设置播放过程中的回调
        setUp(playUrl, false, null)//设置播放URL
    }
}