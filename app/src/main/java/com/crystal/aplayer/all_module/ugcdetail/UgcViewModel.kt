package com.crystal.aplayer.all_module.ugcdetail

import androidx.lifecycle.ViewModel
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend
import com.crystal.aplayer.module_base.common.http.bean2.VideoList
import com.crystal.aplayer.module_base.tools.LogUtil


/**
 *创建者 kiylx
 *创建时间 2020/12/22 11:06
 *packageName：com.crystal.aplayer.all_module.ugcfetail
 *描述：
 */
class UgcViewModel : ViewModel() {
    var currentItem: CommunityRecommend.Item? = null
    var dataList: List<CommunityRecommend.Item>? = null
    var itemPosition = -1

    fun getCurrentIemPos(): Int {
        dataList?.forEachIndexed { index, item ->
            if (currentItem == item) {
                itemPosition = index
                return@forEachIndexed
            }
        }
        return itemPosition
    }

    fun setData(list: VideoList?) {
        dataList = list?.dataList
        currentItem = list?.currentItem

        if (list != null) {
            LogUtil.d("UgcDetailActivity", list.currentItem.id.toString());
        } else {
            LogUtil.d("UgcDetailActivity", "数据空的" );
        }
    }

    companion object {
        const val TAG = "UgcDetailActivity"
    }
}