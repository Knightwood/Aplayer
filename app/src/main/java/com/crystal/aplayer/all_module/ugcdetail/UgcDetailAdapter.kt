package com.crystal.aplayer.all_module.ugcdetail

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.crystal.aplayer.R
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend
import com.crystal.aplayer.module_base.common.util.*
import com.crystal.aplayer.module_base.common.util.viewholder.UgcHolderTypes
import com.crystal.aplayer.module_base.common.util.viewholder.ViewHolderHelper
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseAdapter3
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2
import com.crystal.aplayer.module_base.tools.baseadapter2.HolderType
import com.crystal.aplayer.module_base.tools.circleiv.CircleImageView2
import com.github.chrisbanes.photoview.PhotoView
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer


class UgcDetailAdapter(list: List<CommunityRecommend.Item>, context: Context) : BaseAdapter3<CommunityRecommend.Item, BaseHolder2>(list, context) {
    override fun onBindViewHolder(holder: BaseHolder2, position: Int) {
        val item:CommunityRecommend.Item=dataList[position]

        when (holder) {
            is FollowCardViewHolder -> {
                val item = dataList[position]
                holder.run {
                    videoPlayer.gone()
                    viewPagerPhotos.gone()
                    flHeader.visible()
                    llUgcInfo.visible()

                    ivPullDown.setOnClickListener { (contextWeakReference.get() as Activity).finish() }
                    ivAvatar.load(item.data.content.data.owner.avatar)
                    if (item.data.content.data.owner.expert) ivAvatarStar.visible() else ivAvatarStar.gone()
                    tvNickName.text = item.data.content.data.owner.nickname
                    tvDescription.text = item.data.content.data.description
                    if (item.data.content.data.description.isBlank()) tvDescription.gone() else tvDescription.visible()
                    tvTagName.text = item.data.content.data.tags?.first()?.name
                    if (item.data.content.data.tags.isNullOrEmpty()) tvTagName.gone() else tvTagName.visible()
                    tvCollectionCount.text = item.data.content.data.consumption.collectionCount.toString()
                    tvReplyCount.text = item.data.content.data.consumption.replyCount.toString()
                    setOnClickListener1(tvPrivateLetter, tvFollow, ivCollectionCount, tvCollectionCount, ivReply, tvReplyCount, ivFavorites, tvFavorites, ivShare) {
                       /* when (this) {
                            tvPrivateLetter, tvFollow, ivCollectionCount, tvCollectionCount, ivFavorites, tvFavorites -> //LoginActivity.start(activity)
                            ivShare -> //showDialogShare(activity, getShareContent(item))
                            ivReply, tvReplyCount -> R.string.currently_not_supported.showToast()
                            else -> {
                            }
                        }*/
                    }
                    itemView.setOnClickListener { switchHeaderAndUgcInfoVisibility() }
                }
                when (item.data.content.type) {
                    "video" -> {
                        holder.videoPlayer.visible()
                        holder.videoPlayer.run {
                            val data = item.data.content.data
                            val cover = ImageView(contextWeakReference.get())
                            cover.scaleType = ImageView.ScaleType.CENTER_CROP
                            cover.load(data.cover.detail)
                            cover.parent?.run { removeView(cover) }
                            thumbImageView = cover
                            setThumbPlay(true)
                            setIsTouchWiget(false)
                            isLooping = true
                            playTag = TAG
                            playPosition = position
                            setVideoAllCallBack(object : GSYSampleCallBack() {
                                override fun onClickBlank(url: String?, vararg objects: Any?) {
                                    super.onClickBlank(url, *objects)
                                    holder.switchHeaderAndUgcInfoVisibility()
                                }
                            })
                            setUp(data.playUrl, false, null)
                        }
                    }
                    "ugcPicture" -> {
                        holder.viewPagerPhotos.visible()
                        item.data.content.data.urls?.run {
                            holder.viewPagerPhotos.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            holder.viewPagerPhotos.offscreenPageLimit = 1
                            holder.viewPagerPhotos.adapter = PhotosAdapter(item.data.content.data.urls!!, holder)
                            if (item.data.content.data.urls!!.size > 1) {
                                holder.tvPhotoCount.visible()
                                holder.tvPhotoCount.text = String.format(GlobalUtil.getString(R.string.photo_count), 1, item.data.content.data.urls!!.size)
                            } else {
                                holder.tvPhotoCount.gone()
                            }
                            holder.viewPagerPhotos.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    holder.tvPhotoCount.text = String.format(GlobalUtil.getString(R.string.photo_count), position + 1, item.data.content.data.urls!!.size)
                                }
                            })
                        }
                    }
                    else -> {

                    }
                }
            }
            else -> {
                holder.itemView.gone()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder2 {
        val types:UgcHolderTypes=ViewHolderHelper.getUgcType(viewType)
        val from = LayoutInflater.from(parent.context)
        val inflate: View = from.inflate(types.layoutId, parent, false)
        return FollowCardViewHolder(inflate, types)
    }

    override fun getItemViewType(position: Int): Int {
       val data:CommunityRecommend.Item= dataList[position]
        return ViewHolderHelper.parseUgcViewHolderType(data.type, data.data.dataType);
    }

    class FollowCardViewHolder(view: View,type:HolderType) : BaseHolder2(view,type) {
        val videoPlayer = view.findViewById<GSYVideoPlayer>(R.id.videoPlayer)
        val viewPagerPhotos = view.findViewById<ViewPager2>(R.id.viewPagerPhotos)
        val ivPullDown = view.findViewById<ImageView>(R.id.ivPullDown)
        val tvPhotoCount = view.findViewById<TextView>(R.id.tvPhotoCount)
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val ivAvatarStar = view.findViewById<CircleImageView2>(R.id.ivAvatarStar)
        val tvNickName = view.findViewById<TextView>(R.id.tvNickName)
        val tvPrivateLetter = view.findViewById<TextView>(R.id.tvPrivateLetter)
        val tvFollow = view.findViewById<TextView>(R.id.tvFollow)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvTagName = view.findViewById<TextView>(R.id.tvTagName)
        val ivCollectionCount = view.findViewById<ImageView>(R.id.ivCollectionCount)
        val tvCollectionCount = view.findViewById<TextView>(R.id.tvCollectionCount)
        val ivReply = view.findViewById<ImageView>(R.id.ivReply)
        val tvReplyCount = view.findViewById<TextView>(R.id.tvReplyCount)
        val ivFavorites = view.findViewById<ImageView>(R.id.ivFavorites)
        val tvFavorites = view.findViewById<TextView>(R.id.tvFavorites)
        val ivShare = view.findViewById<ImageView>(R.id.ivShare)
        val flHeader = view.findViewById<FrameLayout>(R.id.headerGroup)
        val llUgcInfo = view.findViewById<LinearLayout>(R.id.llUgcInfo)

        fun switchHeaderAndUgcInfoVisibility() {
            if (ivPullDown.visibility == View.VISIBLE) {
                ivPullDown.invisibleAlphaAnimation()
                llUgcInfo.invisibleAlphaAnimation()
            } else {
                ivPullDown.visibleAlphaAnimation()
                llUgcInfo.visibleAlphaAnimation()
            }
        }
    }
    inner class PhotosAdapter(val dataList: List<String>, val ugcHolder: FollowCardViewHolder) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val photoView = PhotoView(parent.context)
            photoView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return ViewHolder(photoView)
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.photoView.load(dataList[position])
            holder.photoView.setOnClickListener { ugcHolder.switchHeaderAndUgcInfoVisibility() }
        }

        inner class ViewHolder(view: PhotoView) : RecyclerView.ViewHolder(view) {
            val photoView = view
        }
    }
    companion object {
        const val NORMAL_USER_TYPE = "NORMAL"
        const val PGC_USER_TYPE = "PGC"
        const val TAG = "UgcDetailAdapter"
    }
}
