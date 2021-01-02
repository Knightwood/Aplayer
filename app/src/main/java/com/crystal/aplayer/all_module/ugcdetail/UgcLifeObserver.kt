package com.crystal.aplayer.all_module.ugcdetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.shuyu.gsyvideoplayer.GSYVideoManager


/**
 *创建者 kiylx
 *创建时间 2020/12/22 10:50
 *packageName：com.crystal.aplayer.all_module.ugcfetail
 *描述：
 */
class UgcLifeObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateActivity() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onActivityStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onActivityResume() {
        GSYVideoManager.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onActivityPause() {
        GSYVideoManager.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onActivityStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onActivityDestroy() {
        GSYVideoManager.releaseAllVideos()
    }
}