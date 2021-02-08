package com.crystal.aplayer.all_module.video_detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.video_detail.VideoDetailViewModel.InitParamsBundle
import com.crystal.aplayer.all_module.video_detail.adapter.HeaderAdapter
import com.crystal.aplayer.all_module.video_detail.adapter.RelatedVideoAdapter
import com.crystal.aplayer.all_module.video_detail.adapter.RepliesListAdapter
import com.crystal.aplayer.databinding.ActivityVideoDetailBinding
import com.crystal.module_base.common.http.bean2.VideoInfo
import com.crystal.module_base.common.ui.CommonActivityKt
import com.crystal.module_base.common.util.*
import com.crystal.module_base.common.view.JustTextFooter
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.jvm.internal.Intrinsics


class VideoDetailActivity : CommonActivityKt() {
    private lateinit var binding: ActivityVideoDetailBinding
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var relatedListAdapter: RelatedVideoAdapter
    private lateinit var repliesListAdapter: RepliesListAdapter

    private val lifeCycleObserver: VideoLifeObserver = VideoLifeObserver()
    private lateinit var orientationUtils: OrientationUtils

    private val viewModel: VideoDetailViewModel by lazy { ViewModelProvider(this).get(VideoDetailViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(lifeCycleObserver)
    }

    override fun setContentViewAfter() {
        if (initArguments()) {
            setStatusBarBackground(R.color.black);
            prepareData();
        }
    }

    //初始化界面
    private fun prepareData() {
        //播放器旋转工具
        orientationUtils = OrientationUtils(this, binding.videoPlayer)
        //recyclerview
        headerAdapter = HeaderAdapter(this)
        relatedListAdapter = RelatedVideoAdapter(this)
        repliesListAdapter = RepliesListAdapter(this)
        concatAdapter = ConcatAdapter(headerAdapter, relatedListAdapter, repliesListAdapter)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@VideoDetailActivity)
            adapter = concatAdapter
            setHasFixedSize(true)
            itemAnimator = null
        }
        //刷新控件
        binding.refreshLayout.run {
            setDragRate(0.7F)
            setHeaderTriggerRate(0.6F)
            setFooterTriggerRate(0.6F)
            setEnableLoadMoreWhenContentNotFull(true)
            setEnableFooterFollowWhenNoMoreData(true)
            setEnableFooterTranslationContent(true)
            setEnableScrollContentWhenLoaded(true)
            setFooterHeight(153.0f)
            setEnableNestedScroll(true)
            setFooterHeight(153f)
            setRefreshFooter(JustTextFooter(this@VideoDetailActivity).apply {
                setAccentColorId(R.color.white)
                setTextTitleSize(16f)
            })
            setOnRefreshListener {
                it.finishRefresh()
                finish()
            }
            setOnLoadMoreListener { viewModel.loadMore() }
        }
        binding.run {
            setOnClickListener2(
                    ivPullDown, ivMore, ivShare, ivCollection, ivToWechatFriends, ivShareToWechatMemories,
                    ivShareToWeibo, ivShareToQQ, ivShareToQQzone, ivAvatar, etComment, ivReply, tvReplyCount, listener = ClickListener()
            )
        }
        observe()
        viewModel.initData(false)
    }

    private fun observe() {
        viewModel.videoInfo.observe(this, Observer {
            headerAdapter.update(it)
            startVideoPlayer()
        })
        viewModel.relatedDataList.observe(this, Observer {
            relatedListAdapter.update(it)
        })
        viewModel.repliesDataList.observe(this, Observer {
            if (viewModel.replace) {
                repliesListAdapter.replace(it)
            } else {
                repliesListAdapter.update(it)
            }
            viewModel.replace = false
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (initArguments()) {
            viewModel.initData(true)
        }
    }

    private fun startVideoPlayer() {
        viewModel.videoInfo.value?.run {
            binding.ivBlurredBg.load(cover.blurred)
            binding.tvReplyCount.text = consumption.replyCount.toString()
            binding.videoPlayer.startPlay()
        }
    }

    private fun GSYVideoPlayer.startPlay() {
        viewModel.videoInfo.value?.let {
            fullscreenButton.setOnClickListener {
                showFull() //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            }
            playTag = TAG//防止错位设置
            isReleaseWhenLossAudio = false//音频焦点冲突时是否释放
            val imageView = ImageView(this@VideoDetailActivity)//增加封面
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setOnClickListener { switchTitleBarVisible() }
            isRotateViewAuto = false//是否开启自动旋转
            isNeedLockFull = true//是否需要全屏锁定屏幕功能
            setIsTouchWiget(true)//是否可以滑动调整
            dismissControlTime = 5000//设置触摸显示控制ui的消失时间
            setVideoAllCallBack(VideoCallPlayBack())//设置播放过程中的回调
            setUp(it.playUrl, false, it.title)//设置播放URL
            startPlayLogic()//开始播放
        }
    }

    private fun initArguments(): Boolean {
        val info = intent.getParcelableExtra<Parcelable?>(EXTRA_VIDEO_INFO)
        val id = intent.getLongExtra(EXTRA_VIDEO_ID, 0)
        if (info == null && id == 0L) {
            //ToolsKt.`showToast$default`(GlobalUtil.getString(R.string.jump_page_unknown_error), 0, 1, null as Any?)
            finish()
            return false
        }
        viewModel.setInitParams(InitParamsBundle(id, (info as VideoInfo)))
        return true
    }
    override fun onBackPressed() {
        orientationUtils.backToProtVideo()
        if (!GSYVideoManager.backFromWindowFull(this)) {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }
    override fun onDestroy() {
        super.onDestroy()
        GSYVideoADManager.releaseAllVideos()
        orientationUtils.releaseListener()
        binding.videoPlayer.release()
        binding.videoPlayer.setVideoAllCallBack(null)
    }

    //切换顶部工具栏显隐
    private fun switchTitleBarVisible() {
        if (binding.videoPlayer.currentPlayer.currentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) return
        if (binding.headerGroup.visibility == View.VISIBLE) {
            hideTitleBar()
        } else {
            binding.headerGroup.visibleAlphaAnimation(1000)
            delayHideTitleBar()
        }
    }

    private fun hideTitleBar() {
        binding.headerGroup.invisibleAlphaAnimation(1000)
    }

    private fun delayHideTitleBar() {
        CoroutineScope(coroutineContext).launch {
            delay(binding.videoPlayer.dismissControlTime.toLong())
            hideTitleBar()
        }
    }

    private fun delayHideBottomContainer() {
        CoroutineScope(coroutineContext).launch {
            delay(binding.videoPlayer.dismissControlTime.toLong())
            binding.videoPlayer.getBottomContainer().gone()
            binding.videoPlayer.startButton.gone()
        }
    }

    private fun showFull() {
        orientationUtils.run { if (isLand != 1) resolveByClick() }
        binding.videoPlayer.startWindowFullscreen(this, true, false)
    }

    fun scrollTop() {
        if (relatedListAdapter.itemCount != 0) {
            binding.recyclerView.scrollToPosition(0)
            binding.refreshLayout.invisibleAlphaAnimation(2500)
            binding.refreshLayout.visibleAlphaAnimation(1500)
        }
    }

    private fun scrollRepliesTop() {
        val targetPostion = (relatedListAdapter.itemCount - 1) + 2  //+相关推荐最后一项，+1评论标题，+1条评论
        if (targetPostion < concatAdapter.itemCount - 1) {
            binding.recyclerView.smoothScrollToPosition(targetPostion)
        }
    }



    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            binding.headerGroup.gone()
            binding.sharesGroup.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            delayHideBottomContainer()
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            binding.headerGroup.visible()
            binding.ivCollection.gone()
            binding.ivShare.gone()
            binding.ivMore.gone()
            binding.sharesGroup.visible()
        }
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            viewModel.videoInfo.value?.let {
                when (v) {
                    binding.ivPullDown -> finish()
                    binding.ivMore -> {
                    }
                    /*binding.ivShare -> showDialogShare(it.webUrl.raw)
                    binding.ivCollection -> LoginActivity.start(this@NewDetailActivity)
                    binding.ivToWechatFriends -> share(it.webUrl.raw, SHARE_WECHAT)
                    binding.ivShareToWechatMemories -> share(it.webUrl.raw, SHARE_WECHAT_MEMORIES)
                    binding.ivShareToWeibo -> share(it.webUrl.forWeibo, SHARE_WEIBO)
                    binding.ivShareToQQ -> share(it.webUrl.raw, SHARE_QQ)
                    binding.ivShareToQQzone -> share(it.webUrl.raw, SHARE_QQZONE)
                    binding.ivAvatar, binding.etComment -> LoginActivity.start(this@NewDetailActivity)
                    binding.ivReply, binding.tvReplyCount -> scrollRepliesTop()*/
                    else -> {
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_VIDEO_ID = "videoId"
        const val EXTRA_VIDEO_INFO = "videoInfo"
        const val TAG = "VideoDetailActivity"

        fun start(activity: Context, videoId: Long?) {
            val starter = Intent(activity, VideoDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            (activity as Activity).startActivity(starter)
            (activity as Activity).overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }

        fun start(activity: Context, videoInfo: VideoInfo?) {
            Intrinsics.checkParameterIsNotNull(activity, "activity")
            val starter = Intent(activity, VideoDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_INFO, videoInfo)
            (activity as Activity).startActivity(starter)
            (activity as Activity).overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}