package com.crystal.module_base.common.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import com.crystal.module_base.base.ui.activity.BaseActivity
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * 创建者 kiylx
 * 创建时间 2020/12/21 20:14
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
open class CommonActivityKt : BaseActivity(), CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setContentViewAfter()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        setContentViewAfter()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected open fun setContentViewAfter() {}

    /**
     * 设置状态栏颜色
     */
    protected fun changeStatesBarColor(@ColorRes colorInt: Int) {
        ImmersionBar.with(this)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .statusBarColor(colorInt)
                .fitsSystemWindows(true)
                .init()
    }
}