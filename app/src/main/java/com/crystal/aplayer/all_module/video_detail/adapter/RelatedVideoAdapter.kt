package com.crystal.aplayer.all_module.video_detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.home.daily.DailyAdapter
import com.crystal.aplayer.all_module.video_detail.VideoDetailActivity
import com.crystal.module_base.common.http.bean2.VideoInfo
import com.crystal.module_base.common.http.bean2.VideoRelated
import com.crystal.module_base.common.util.conversionVideoDuration
import com.crystal.module_base.common.util.gone
import com.crystal.module_base.common.util.load
import com.crystal.module_base.common.util.viewholder.ViewHolderHelper
import com.crystal.module_base.common.util.viewholder.ViewHolderTypes
import com.crystal.module_base.tools.LogUtil
import com.crystal.module_base.tools.Weak
import com.crystal.module_base.tools.baseadapter2.BaseHolder2
import com.crystal.module_base.tools.baseadapter2.HolderType


class RelatedVideoAdapter(context: VideoDetailActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: MutableList<VideoRelated.Item> = mutableListOf()
    private val activity by Weak {
        context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val types = ViewHolderHelper.getViewHolderType(viewType)
        if (types != ViewHolderTypes.VIDEO_SMALL_CARD) {
            return BaseHolder2(LayoutInflater.from(parent.context).inflate(ViewHolderTypes.UNKNOWN.layoutId, parent, false), ViewHolderTypes.UNKNOWN)
        }
        val inflate: View = LayoutInflater.from(parent.context).inflate(types.layoutId, parent, false)
        return VideoSmallCardViewHolder(inflate, types)
    }

    override fun getItemCount(): Int {
        if (dataList.isNotEmpty()) {
            return dataList.size;
        }
        return 0;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderTypeEnum = (holder as BaseHolder2).holderTypeEnum
        val item :VideoRelated.Item = dataList[position]
        val data=item.data
        when (holderTypeEnum as ViewHolderTypes) {
            ViewHolderTypes.TEXT_CARD_HEADER4 -> {
                holder.setText(R.id.tvTitle4, data.text)
            }
            ViewHolderTypes.VIDEO_SMALL_CARD -> {
               ( holder as VideoSmallCardViewHolder).run {
                   ivPicture.load(data.cover.feed, 4f)
                   tvDescription.text = if (data.library == DailyAdapter.DAILY_LIBRARY_TYPE) "#${data.category} / 开眼精选" else "#${data.category}"
                   tvDescription.setTextColor(ContextCompat.getColor(activity!!, R.color.whiteAlpha35))
                   tvTitle.text = data.title
                   tvTitle.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                   tvVideoDuration.text = data.duration.conversionVideoDuration()
                   ivShare.setOnClickListener {
                       //showDialogShare(activity, "${item.data.title}：${item.data.webUrl.raw}")
                   }
                   itemView.setOnClickListener {
                       data.run {
                           activity?.let { it1 -> VideoDetailActivity.start(it1, VideoInfo(id, playUrl, title, description, category, library, consumption, cover, author, webUrl)) }
                           activity?.scrollTop()
                       }
                   }
               }

            }
            else -> {
                holder.itemView.gone()
            }
        }
    }


    // androidx.recyclerview.widget.RecyclerView.Adapter
    override fun getItemViewType(position: Int): Int {
        val list = dataList
        val (data, type) = list[position]
        return ViewHolderHelper.parseViewHolderType(type, data.dataType, data.type)
    }

    fun update(list: List<VideoRelated.Item>?) {
        if (list != null) {
            LogUtil.d(tag, "更新推荐视频：" + list.size);
        } else {
            LogUtil.d(tag, "推荐视频结果list为空");
        }
        this.dataList = list as MutableList<VideoRelated.Item>;
        notifyDataSetChanged();
    }

    /**
     * 相关推荐，数据集合里附带的热门评论，UI展示上不做处理。
     * 不做任何处理，忽略此ViewHolder。
     */
    inner class SimpleHotReplyCardViewHolder(view: View) : BaseHolder2(view) {

    }
    inner class VideoSmallCardViewHolder(itemView: View, typeEnum: HolderType?) : BaseHolder2(itemView, typeEnum) {
         val ivPicture: ImageView= itemView.findViewById(R.id.ivPicture);
         val ivShare: ImageView=itemView.findViewById(R.id.ivShare);
         val tvDescription: TextView=itemView.findViewById(R.id.tvDescription);
         val tvTitle: TextView=itemView.findViewById(R.id.tvTitle);
         val tvVideoDuration: TextView=itemView.findViewById(R.id.tvVideoDuration);
    }

    companion object {
        const val tag = "VideoDetailRelatedAdapter"
        const val SIMPLE_HOT_REPLY_CARD_TYPE = -12
    }


}

