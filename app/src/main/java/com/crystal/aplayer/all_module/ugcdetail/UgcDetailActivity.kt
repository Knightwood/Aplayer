package com.crystal.aplayer.all_module.ugcdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.crystal.aplayer.AppApplication
import com.crystal.aplayer.R
import com.crystal.aplayer.module_base.common.util.callback.ViewPagerChangeListener
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend
import com.crystal.aplayer.module_base.common.http.bean2.VideoList
import com.crystal.aplayer.module_base.common.ui.CommonActivity
import com.crystal.aplayer.module_base.tools.LogUtil
import com.crystal.aplayer.module_base.tools.live_data_bus.DataBus
import com.crystal.aplayer.module_base.tools.live_data_bus.core.OstensibleObserver

/**
 * 社区-推荐详情页。
 */
class UgcDetailActivity : CommonActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(UgcViewModel::class.java) }
    private lateinit var rootView: ViewPager2
    private var detailAdapter: UgcDetailAdapter? = null
    private var dataObserver: Observer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ugc_detail)
        rootView = findViewById(R.id.viewpager2)
        lifecycle.addObserver(UgcLifeObserver())
        dataObserver = Observer()
        DataBus.with<VideoList>(VIDEO_LIST).observeSticky(this, dataObserver!!)
    }

    override fun setContentViewAfter() {
        setStatusBarBackground(R.color.black)
    }

    private fun setAdapter() {
        if (viewModel.dataList == null) {
            finish()
            return
        } else {
            if (detailAdapter==null){
                detailAdapter=UgcDetailAdapter(viewModel.dataList!!, this)
            }
            rootView.run {
                adapter = detailAdapter
                orientation = ViewPager2.ORIENTATION_VERTICAL
                offscreenPageLimit = 1
                registerOnPageChangeCallback(ViewPagerChangeListener(this, viewModel.itemPosition, R.id.videoPlayer))
                setCurrentItem(viewModel.getCurrentIemPos(), false)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(UgcLifeObserver())
        dataObserver = null
    }

    inner class Observer : OstensibleObserver<VideoList>() {
        override fun onChanged(t: VideoList?) {
            LogUtil.d(TAG,t?.currentItem?.type + "  bus:数据发送成功")
            viewModel.setData(t)
            setAdapter();
        }

    }

    companion object {
        const val TAG = "UgcDetailActivity"
        const val VIDEO_LIST = "VIDEO_LIST"

        fun start(context: Activity, dataList: List<CommunityRecommend.Item>, currentItem: CommunityRecommend.Item) {
            if (dataList.isEmpty()){
            LogUtil.d(TAG,"传入数据是空的")
                return
            }
            val intent = Intent(context, UgcDetailActivity::class.java)
            context.startActivity(intent)
            DataBus.with<VideoList>(VIDEO_LIST).post(VideoList(currentItem, dataList))
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }

}
