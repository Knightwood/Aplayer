package com.crystal.aplayer.all_module.ugcdetail

import com.crystal.module_base.common.http.bean2.CommunityRecommend

/**
 *创建者 kiylx
 *创建时间 2021/1/2 17:43
 *packageName：com.crystal.aplayer.all_module.ugcdetail
 *描述：
 */
data class VideoList(val currentItem: CommunityRecommend.Item, val dataList: List<CommunityRecommend.Item>)