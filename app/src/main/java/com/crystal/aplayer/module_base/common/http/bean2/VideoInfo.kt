package com.crystal.aplayer.module_base.common.http.bean2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *创建者 kiylx
 *创建时间 2020/11/20 17:19
 *packageName：com.crystal.aplayer.all_module.newdetail
 *描述：
 */
@Parcelize
data class VideoInfo(val videoId: Long,
                     val playUrl: String,
                     val title: String,
                     val description: String,
                     val category: String,
                     val library: String,
                     val consumption: Consumption,
                     val cover: Cover,
                     val author: Author?,
                     val webUrl: WebUrl) : Parcelable
fun newVideoInfo(videoBeanForClient: VideoBeanForClient): VideoInfo {
    return VideoInfo(
            videoBeanForClient.id,
            videoBeanForClient.playUrl,
            videoBeanForClient.title,
            videoBeanForClient.description,
            videoBeanForClient.category,
            videoBeanForClient.library,
            videoBeanForClient.consumption,
            videoBeanForClient.cover,
            videoBeanForClient.author,
            videoBeanForClient.webUrl
    )
}