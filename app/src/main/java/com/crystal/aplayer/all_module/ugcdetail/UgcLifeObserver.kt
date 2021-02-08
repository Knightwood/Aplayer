package com.crystal.aplayer.all_module.ugcdetail

import com.crystal.module_base.base.BaseLifeCycleObserver
import com.shuyu.gsyvideoplayer.GSYVideoManager


/**
 *创建者 kiylx
 *创建时间 2020/12/22 10:50
 *packageName：com.crystal.aplayer.all_module.ugcfetail
 *描述：
 */
class UgcLifeObserver : BaseLifeCycleObserver() {
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