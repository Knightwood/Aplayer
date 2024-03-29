package com.crystal.aplayer.all_module.video_detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.video_detail.VideoDetailActivity
import com.crystal.aplayer.module_base.common.http.bean2.VideoReplies
import com.crystal.aplayer.module_base.common.util.*
import com.crystal.aplayer.module_base.common.util.GlobalUtil.dp2px
import com.crystal.aplayer.module_base.common.util.viewholder.ViewHolderTypes
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2
import com.crystal.aplayer.module_base.tools.Weak


class RepliesListAdapter(context: VideoDetailActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList: MutableList<VideoReplies.Item> = mutableListOf()
    var activity by Weak {
        context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == REPLY_BEAN_FOR_CLIENT_TYPE) {
            val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_new_detail_reply_type, parent, false)
            ReplyViewHolder(inflate)
        } else if (viewType == TEXTCARDVIEWHEADER_TYPE) {
            val inflate2: View = LayoutInflater.from(parent.context).inflate(R.layout.item_text_card_type_header_four, parent, false)
            TextCardViewHeader4ViewHolder(inflate2)
        } else {
            BaseHolder2(LayoutInflater.from(parent.context).inflate(ViewHolderTypes.UNKNOWN.layoutId, parent, false))
        }

    }

    override fun getItemViewType(position: Int): Int {
            val item: VideoReplies.Item = dataList[position] ?: return -1
            if (item.type == "reply" && item.data.dataType == "ReplyBeanForClient")
                return 1
            if (item.type == "textCard" && item.data.type == "header4")
                return 2
        return -1
    }

    override fun getItemCount(): Int {
        return dataList?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is TextCardViewHeader4ViewHolder -> {
                holder.tvTitle4.text = item.data.text
                if (item.data.actionUrl != null && item.data.actionUrl!!.startsWith(Const.ActionUrl.REPLIES_HOT)) {
                    //热门评论
                    holder.ivInto4.visible()
                    holder.tvTitle4.layoutParams = (holder.tvTitle4.layoutParams as LinearLayout.LayoutParams).apply { setMargins(0, dp2px(30f), 0, dp2px(6f)) }
                    setOnClickListener1(holder.tvTitle4, holder.ivInto4) { R.string.currently_not_supported.showToast() }
                } else {
                    //相关推荐、最新评论
                    holder.tvTitle4.layoutParams = (holder.tvTitle4.layoutParams as LinearLayout.LayoutParams).apply { setMargins(0, dp2px(24f), 0, dp2px(6f)) }
                    holder.ivInto4.gone()
                }
            }
            is ReplyViewHolder -> {
                holder.groupReply.gone()
                holder.ivAvatar.load(item.data.user?.avatar ?: "")
                holder.tvNickName.text = item.data.user?.nickname
                holder.tvLikeCount.text = if (item.data.likeCount == 0) "" else item.data.likeCount.toString()
                holder.tvMessage.text = item.data.message
                holder.tvTime.text = getTimeReply(item.data.createTime)
                holder.ivLike.setOnClickListener { R.string.currently_not_supported.showToast() }

                item.data.parentReply?.run {
                    holder.groupReply.visible()
                    holder.tvReplyUser.text = String.format(GlobalUtil.getString(R.string.reply_target), user?.nickname)
                    holder.ivReplyAvatar.load(user?.avatar ?: "")
                    holder.tvReplyNickName.text = user?.nickname
                    holder.tvReplyMessage.text = message
                    holder.tvShowConversation.setOnClickListener { R.string.currently_not_supported.showToast() }
                }
            }
            else -> {
                holder.itemView.gone()

            }
        }
    }

    private fun getTimeReply(dateMillis: Long): String {
        val currentMillis = System.currentTimeMillis()
        val timePast = currentMillis - dateMillis
        if (timePast > -DateUtil.MINUTE) { // 采用误差一分钟以内的算法，防止客户端和服务器时间不同步导致的显示问题
            when {
                timePast < DateUtil.DAY -> {
                    var pastHours = timePast / DateUtil.HOUR
                    if (pastHours <= 0) {
                        pastHours = 1
                    }
                    return DateUtil.getDate(dateMillis, "HH:mm")
                }
                else -> return DateUtil.getDate(dateMillis, "yyyy/MM/dd")
            }
        } else {
            return DateUtil.getDate(dateMillis, "yyyy/MM/dd HH:mm")
        }
    }

    inner class TextCardViewHeader4ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivInto4: ImageView = view.findViewById<ImageView>(R.id.ivInto4)
        val tvTitle4: TextView = view.findViewById<TextView>(R.id.tvTitle4)
    }

    inner class ReplyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvNickName = view.findViewById<TextView>(R.id.tvNickName)
        val ivLike = view.findViewById<ImageView>(R.id.ivLike)
        val tvLikeCount = view.findViewById<TextView>(R.id.tvLikeCount)
        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        val tvTime = view.findViewById<TextView>(R.id.tvTime)

        val groupReply = view.findViewById<Group>(R.id.groupReply)
        val tvReplyUser = view.findViewById<TextView>(R.id.tvReplyUser)
        val ivReplyAvatar = view.findViewById<ImageView>(R.id.ivReplyAvatar)
        val tvReplyNickName = view.findViewById<TextView>(R.id.tvReplyNickName)
        val tvReplyMessage = view.findViewById<TextView>(R.id.tvReplyMessage)
        val tvShowConversation = view.findViewById<TextView>(R.id.tvShowConversation)

    }

    fun replace(list: List<VideoReplies.Item>?) {
        if (list != null) {
            dataList.clear()
            dataList.addAll(list)
        }
        notifyDataSetChanged()
    }


    fun update(list: List<VideoReplies.Item>?) {
        if (list != null) {
            if (dataList.isEmpty()) {
                dataList.addAll(list)
                notifyDataSetChanged()
                return
            }
            val i = dataList.size - 1
            dataList.addAll(list)
            notifyItemRangeInserted(i, dataList.size)
        }
    }

    companion object {
        const val TAG = "NewDetailReplyAdapter"
        const val REPLY_BEAN_FOR_CLIENT_TYPE = 1
        const val TEXTCARDVIEWHEADER_TYPE = 2
    }
}
