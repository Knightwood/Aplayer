package com.crystal.aplayer.all_module.community.follow

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.login.LoginActivity
import com.crystal.aplayer.module_base.common.http.bean2.VideoInfo
import com.crystal.aplayer.all_module.video_detail.VideoDetailActivity
import com.crystal.aplayer.module_base.common.http.bean2.Follow
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil
import com.crystal.aplayer.module_base.common.util.*
import com.crystal.aplayer.module_base.common.util.DateUtil.getDate
import com.crystal.aplayer.module_base.common.util.viewholder.ViewHolderHelper
import com.crystal.aplayer.module_base.common.util.viewholder.CommunityViewHolderTypes
import com.crystal.aplayer.module_base.common.view.videoplayer.startAutoPlay
import com.crystal.aplayer.module_base.tools.SomeTools
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseAdapter3
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

/**
 *创建者 kiylx
 *创建时间 2020/11/30 18:02
 *packageName：com.crystal.aplayer.all_module.community.follow
 *描述：社区-关注
 */
class FollowAdapter(list: List<Follow.Item>, context: Context) : BaseAdapter3<Follow.Item, BaseHolder2>(list, context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder2 {
        return ViewHolderHelper.getCommunityViewHolder(viewType, LayoutInflater.from(parent.context), parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) CommunityViewHolderTypes.LOGINHEADER.typeInt else {
            val data = dataList[position - 1]
            ViewHolderHelper.parseCommunityViewHolderType(data.type, data.data.dataType)
        }
    }

    override fun onBindViewHolder(holder: BaseHolder2, position: Int) {
        when (holder.holderTypeEnum as CommunityViewHolderTypes) {
            CommunityViewHolderTypes.AUTOPLAYFOLLOWCARD -> {
                val beanItem = dataList[position - 1]
                beanItem.data.content.data.run {
                    SomeTools.INSTANCES.loadImg(holder.getView(R.id.ivAvatar), beanItem.data.header.icon
                            ?: author?.icon ?: "", null)
                    holder.setText(R.id.tvReleaseTime, getDate(releaseTime
                            ?: author?.latestReleaseTime ?: System.currentTimeMillis(), "HH:mm"))
                    holder.setText(R.id.tvTitle, title)
                    holder.setText(R.id.tvNickname, author?.name ?: "")
                    holder.setText(R.id.tvContent, description)
                    holder.setText(R.id.tvCollectionCount, consumption.collectionCount.toString())
                    holder.setText(R.id.tvReplyCount, consumption.replyCount.toString())
                    holder.setText(R.id.tvVideoDuration, duration.transformToVideoDuration())
                    holder.setViewVisibility(R.id.tvVideoDuration, View.VISIBLE)//视频播放后，复用tvVideoDuration直接隐藏了

                    startAutoPlay(contextWeakReference.get() as Activity, holder.getView(R.id.videoPlayer), position, playUrl, cover.feed, tag, object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            super.onPrepared(url, *objects)
                            holder.getView<TextView>(R.id.tvVideoDuration).gone()
                            GSYVideoManager.instance().isNeedMute = true
                        }

                        override fun onClickResume(url: String?, vararg objects: Any?) {
                            super.onClickResume(url, *objects)
                            holder.getView<TextView>(R.id.tvVideoDuration).gone()
                        }

                        override fun onClickBlank(url: String?, vararg objects: Any?) {
                            super.onClickBlank(url, *objects)
                            holder.getView<TextView>(R.id.tvVideoDuration).visible()
                            VideoDetailActivity.start(contextWeakReference.get() as Activity, VideoInfo(id, playUrl, title, description, category, library, consumption, cover, author!!, webUrl))
                        }
                    })
                    holder.let {
                        setOnClickListener1(it.getView<GSYVideoPlayer>(R.id.videoPlayer).thumbImageView, it.itemView, it.getView<ImageView>(R.id.ivCollectionCount), it.getView<TextView>(R.id.tvCollectionCount), it.getView<ImageView>(R.id.ivFavorites), it.getView<TextView>(R.id.tvFavorites), it.getView<ImageView>(R.id.ivShare))
                        {
                            when (this) {
                                it.getView<GSYVideoPlayer>(R.id.videoPlayer).thumbImageView, it.itemView -> {
                                    VideoDetailActivity.start(
                                            contextWeakReference.get() as Activity, VideoInfo(
                                            beanItem.data.content.data.id, playUrl, title, description, category, library, consumption, cover, author!!, webUrl
                                    ))
                                }
                                it.getView<ImageView>(R.id.ivCollectionCount), it.getView<TextView>(R.id.tvCollectionCount) -> {
                                    //喜欢
                                    CommonActionUrlUtil.processCollection(contextWeakReference.get(),null)
                                }
                                it.getView<ImageView>(R.id.ivFavorites), it.getView<TextView>(R.id.tvFavorites)->{
                                   //收藏
                                    CommonActionUrlUtil.processFavorites()
                                }
                                it.getView<ImageView>(R.id.ivShare) -> {
                                    //分享
                                    CommonActionUrlUtil.processShare(contextWeakReference.get() as Activity, getShareContent(beanItem))
                                }
                                it.getView<ImageView>(R.id.ivReply),it.getView<TextView>(R.id.tvReplyCount)->{
                                    //回复
                                    CommonActionUrlUtil.processReply()
                                }
                            }
                        }
                    }
                }
            }
            CommunityViewHolderTypes.LOGINHEADER -> {
                if (checkLogin())
                    holder.itemView.gone()
                else
                    holder.itemView.setOnClickListener {
                        contextWeakReference.get()?.let { it1 -> start(it1, LoginActivity::class.java) }
                    }
            }
            else -> {
                holder.itemView.gone()
            }
        }
    }
fun getShareContent(item: Follow.Item):String {
    item.data.content.data.run {
        val linkUrl = "${item.data.content.data.webUrl.raw}&utm_campaign=routine&utm_medium=share&utm_source=others&uid=0&resourceType=${resourceType}"
        return "${title}|${GlobalUtil.appName}：\n${linkUrl}"
    }
}

    companion object {
        const val tag = "FollowAdapter"
    }
}