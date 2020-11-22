package com.crystal.module_base.common.view;

import android.content.Context;
import android.util.AttributeSet;

import com.crystal.module_base.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;



/**
 * 创建者 kiylx
 * 创建时间 2020/11/7 9:54
 * packageName：com.crystal.module_base.common.view
 * 描述：
 */
public class AutoPlayerVideoPlayer extends StandardGSYVideoPlayer {
    public AutoPlayerVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public AutoPlayerVideoPlayer(Context context) {
        super(context);
    }

    public AutoPlayerVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_auto_play_video_player;
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;
    }

    @Override
    protected void updateStartImage() {
        super.updateStartImage();
    }

    @Override
    protected void touchDoubleUp() {
        super.touchDoubleUp();
    }

    @Override
    protected void changeUiToPreparingShow() {
        super.changeUiToPreparingShow();
    }

    @Override
    protected void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
    }

    @Override
    protected void changeUiToPauseShow() {
        super.changeUiToPauseShow();
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow();
    }

    @Override
    protected void changeUiToCompleteShow() {
        super.changeUiToCompleteShow();
    }

    @Override
    protected void changeUiToError() {
        super.changeUiToError();
    }
}
