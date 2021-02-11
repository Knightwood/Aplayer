package com.crystal.aplayer.all_module.video_detail

import com.crystal.aplayer.module_base.base.BaseLifeCycleObserver
import com.shuyu.gsyvideoplayer.GSYVideoManager


class VideoLifeObserver: BaseLifeCycleObserver() {

    override fun onActivityResume() {
        super.onActivityResume()
        GSYVideoManager.onResume()
    }

    override fun onActivityPause() {
        super.onActivityPause()
        GSYVideoManager.onPause()
    }

    override fun onActivityDestroy() {
        super.onActivityDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}