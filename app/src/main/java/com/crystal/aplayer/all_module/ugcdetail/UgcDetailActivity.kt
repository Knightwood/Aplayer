package com.crystal.aplayer.all_module.ugcdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.crystal.aplayer.AppApplication
import com.crystal.aplayer.R
import com.crystal.module_base.common.http.bean2.CommunityRecommend
import com.crystal.module_base.common.ui.CommonActivity
import com.crystal.module_base.tools.live_data_bus.DataBus
import com.crystal.module_base.tools.live_data_bus.core.ObserverMod

/**
 * 社区-推荐详情页。
 */
class UgcDetailActivity : CommonActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(UgcViewModel::class.java) }
    private lateinit var rootView: ViewPager2
    private var adapter: UgcDetalAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = ViewPager2(this)
        setContentView(rootView)
        lifecycle.addObserver(UgcLifeObserver())
        DataBus.with<VideoList>(VIDEO_LIST).observeSticky(this, Observer())
    }

    override fun setContentViewAfter() {
        super.setContentViewAfter()
        setAdapter()
    }

    private fun setAdapter() {
        if (viewModel.dataList == null) {
            return
        } else {
            adapter = adapter ?: UgcDetalAdapter(viewModel.dataList!!, this)
            rootView.let {
                it.adapter = adapter
                it.orientation = ViewPager2.ORIENTATION_VERTICAL
                it.offscreenPageLimit = 1
                it.registerOnPageChangeCallback(AutoPlayPageChangeListener(it, viewModel.itemPosition, R.id.videoPlayer))
                it.setCurrentItem(viewModel.itemPosition, false)
            }

        }
    }

    private fun getCurrentIemPos(): Int {
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(UgcLifeObserver())
    }

    class Observer : ObserverMod<VideoList>() {
        override fun onChanged(t: VideoList?) {
            val toast = Toast.makeText(AppApplication.getContext(), t?.currentItem?.type+"databus", Toast.LENGTH_LONG)
            toast.show()
        }

    }

    companion object {
        const val TAG = "UgcDetailActivity"
        const val VIDEO_LIST = "VIDEO_LIST"

        fun start(context: Activity, dataList: List<CommunityRecommend.Item>, currentItem: CommunityRecommend.Item) {
            DataBus.with<VideoList>(VIDEO_LIST).post(VideoList(currentItem, dataList))
            val intent = Intent(context, UgcDetailActivity::class.java)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }

}
