package com.crystal.aplayer.all_module.ugcdetail

import androidx.lifecycle.ViewModel
import com.crystal.module_base.common.http.bean2.CommunityRecommend

/**
 *创建者 kiylx
 *创建时间 2020/12/22 11:06
 *packageName：com.crystal.aplayer.all_module.ugcfetail
 *描述：
 */
class UgcViewModel : ViewModel() {
    var dataList: List<CommunityRecommend.Item>? = null

    var itemPosition: Int = -1
}