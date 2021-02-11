package com.crystal.aplayer.all_module.notification.push

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.crystal.aplayer.R
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil
import com.crystal.aplayer.module_base.common.http.bean2.PushMessage
import com.crystal.aplayer.module_base.common.util.DateUtil.getConvertedDate
import com.crystal.aplayer.module_base.common.util.load
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseAdapter3
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2

/**
 *创建者 kiylx
 *创建时间 2020/12/8 21:10
 *packageName：com.crystal.aplayer.all_module.notification.push
 *描述：
 */
class PushMesAdapter(list: List<PushMessage.Message>, context: Context) : BaseAdapter3<PushMessage.Message, BaseHolder2>(list, context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder2 {
        val holder = BaseHolder2(LayoutInflater.from(parent.context).inflate(R.layout.item_notification_push, parent, false))
        holder.itemView.setOnClickListener {
            val item = dataList[holder.adapterPosition]
            CommonActionUrlUtil.process(contextWeakReference.get(), item.actionUrl, item.title)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseHolder2, position: Int) {
        dataList[position].run {
            holder.getView<ImageView>(R.id.ivAvatar).load(icon) { error(R.mipmap.ic_launcher) }
            holder.setText(R.id.tvTitle, title)
            holder.setText(R.id.tvTime,getConvertedDate(date))
            holder.setText(R.id.tvContent, content)
        }
    }
}