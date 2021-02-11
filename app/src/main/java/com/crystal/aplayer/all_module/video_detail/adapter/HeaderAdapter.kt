package com.crystal.aplayer.all_module.video_detail.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.video_detail.VideoDetailActivity
import com.crystal.aplayer.module_base.common.http.bean2.VideoInfo
import com.crystal.aplayer.module_base.common.util.gone
import com.crystal.aplayer.module_base.common.util.load
import com.crystal.aplayer.module_base.common.util.setOnClickListener1
import com.crystal.aplayer.module_base.common.util.visible
import com.crystal.aplayer.module_base.common.view.textview.CollapseTextView
import com.crystal.aplayer.module_base.tools.LogUtil
import com.crystal.aplayer.module_base.tools.circleiv.CircleImageView2
import kotlin.jvm.internal.Intrinsics


class HeaderAdapter(private val activity: VideoDetailActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var animFlags = true
    private var rotationAnim: ObjectAnimator? = null
    private var videoInfoData: VideoInfo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_video_detail_custom_header_type, parent, false)
        return Header(inflate)
    }

    fun update(videoInfo: VideoInfo) {
        Intrinsics.checkParameterIsNotNull(videoInfo, VideoDetailActivity.EXTRA_VIDEO_INFO)
        LogUtil.d(tag, "更新头部视图：" + videoInfo.title, arrayOfNulls<Any>(0))
        videoInfoData = videoInfo
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (videoInfoData == null) 0 else 1

    fun rotate(view: View?) {
        rotationAnim?.cancel()
        rotationAnim = if (animFlags) {
            ObjectAnimator.ofFloat(view, "rotation", 0.0f, 180.0f)
        } else {
            ObjectAnimator.ofFloat(view, "rotation", 180.0f, 0.0f)
        }
        rotationAnim?.start()
        rotationAnim?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                animFlags = !animFlags
            }
        })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        videoInfoData?.let {
            (holder as Header).run {
                groupAuthor.gone()
                tvTitle.text = videoInfoData?.title
                tvCategory.text =
                        if (videoInfoData?.library == "DAILY") "#${videoInfoData?.category} / 开眼精选" else "#${videoInfoData?.category}"
                tvDescription.text = videoInfoData?.description
                tvCollectionCount.text = videoInfoData?.consumption?.collectionCount.toString()
                tvShareCount.text = videoInfoData?.consumption?.shareCount.toString()
                videoInfoData?.author?.run {
                    groupAuthor.visible()
                    ivAvatar.load(videoInfoData?.author?.icon ?: "")
                    tvAuthorDescription.text = videoInfoData?.author?.description
                    tvAuthorName.text = videoInfoData?.author?.name
                }
                setOnClickListener1(ivCollectionCount, tvCollectionCount, ivShare, tvShareCount, ivCache, tvCache, ivFavorites, tvFavorites, tvFollow) {
                    when (this) {
                        ivCollectionCount, tvCollectionCount, ivFavorites, tvFavorites -> {
                            //LoginActivity.start(activity)
                        }
                        ivShare, tvShareCount -> {
                            //showDialogShare(activity, "${videoInfoData?.title}：${videoInfoData?.webUrl?.raw}")
                        }
                        ivCache, tvCache -> {
                            //    R.string.currently_not_supported.showToast()
                        }
                        tvFollow -> {
                            //LoginActivity.start(activity)
                        }
                        ivFoldText->{
                            //文字展开
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    inner class Header(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        val ivFoldText = view.findViewById<ImageView>(R.id.ivFoldText)
        val tvDescription = view.findViewById<CollapseTextView>(R.id.tvDescription)
        val ivCollectionCount = view.findViewById<ImageView>(R.id.ivCollectionCount)
        val tvCollectionCount = view.findViewById<TextView>(R.id.tvCollectionCount)
        val ivShare = view.findViewById<ImageView>(R.id.ivShare)
        val tvShareCount = view.findViewById<TextView>(R.id.tvShareCount)
        val ivCache = view.findViewById<ImageView>(R.id.ivCache)
        val tvCache = view.findViewById<TextView>(R.id.tvCache)
        val ivFavorites = view.findViewById<ImageView>(R.id.ivFavorites)
        val tvFavorites = view.findViewById<TextView>(R.id.tvFavorites)
        val ivAvatar = view.findViewById<CircleImageView2>(R.id.ivAvatar)
        val ivAvatarStar=view.findViewById<CircleImageView2>(R.id.ivAvatarStar)
        val tvAuthorDescription = view.findViewById<TextView>(R.id.tvAuthorDescription)
        val tvAuthorName = view.findViewById<TextView>(R.id.tvAuthorName)
        val tvFollow = view.findViewById<TextView>(R.id.tvFollow)
        val groupAuthor = view.findViewById<Group>(R.id.groupAuthor)
    }

    companion object {
        const val tag = "HeaderAdapter"
        const val DEFAULT_LIBRARY_TYPE = "DEFAULT"
        const val NONE_LIBRARY_TYPE = "NONE"
        const val DAILY_LIBRARY_TYPE = "DAILY"
    }
}
