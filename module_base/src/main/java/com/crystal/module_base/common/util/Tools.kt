package com.crystal.module_base.common.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crystal.module_base.R
import com.crystal.module_base.common.ui.share.ShareDialogFragment
import com.crystal.module_base.common.util.GlobalUtil.dp2px
import com.crystal.module_base.tools.LogUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.*

/**
 *创建者 kiylx
 *创建时间 2020/11/28 10:17
 *packageName：com.crystal.aplayer.all_module.community.commend
 *描述：
 */

/**
 * 通过获取屏幕宽度来计算出每张图片最大的宽度。
 *
 * @return 计算后得出的每张图片最大的宽度。
 */
val maxImageWidth: Int
    get() {
        val columnWidth = GlobalUtil.screenWidth
        return (columnWidth - ((bothSideSpace * 2) + (middleSpace * 2))) / 2
    }

/**
 * 列表左or右间距
 */
val bothSideSpace = GlobalUtil.getDimension(R.dimen.listSpaceSize)

/**
 * 列表中间内间距，左or右。
 */
val middleSpace = dp2px(3f)

/**
 * 根据屏幕比例计算图片高
 *
 * @param originalWidth   服务器图片原始尺寸：宽
 * @param originalHeight  服务器图片原始尺寸：高
 * @return 根据比例缩放后的图片高
 */
fun calculateImageHeight(originalWidth: Int, originalHeight: Int): Int {
    //服务器数据异常处理
    if (originalWidth == 0 || originalHeight == 0) {
        LogUtil.w(TAG, GlobalUtil.getString(R.string.image_size_error))
        return maxImageWidth
    }
    return maxImageWidth * originalHeight / originalWidth
}

/**
 * 获取转换后的时间样式。
 *
 * @return 处理后的时间样式，示例：06:50
 */
fun Int.transformToVideoDuration(): String {
    //60秒每分
    //3600秒每时
    //86400秒每天
    return if (this < 3600) {
        //小于一小时
        String.format(Locale.getDefault(), "%02d:%02d", this / 60, this % 60)
    } else String.format(Locale.getDefault(), "%02d:%02d:%02d", this / 3600, (this % 3600) / 60, this % 60)
}

/**
 * 根据传入的Unix时间戳，获取转换过后更加易读的时间格式。
 * @param dateMillis
 * Unix时间戳
 * @return 转换过后的时间格式，如2分钟前，1小时前。
 */
fun getConvertedDate(dateMillis: Long): String {
    val currentMillis = System.currentTimeMillis()
    val timePast = currentMillis - dateMillis
    if (timePast > -MINUTE) { // 采用误差一分钟以内的算法，防止客户端和服务器时间不同步导致的显示问题
        when {
            timePast < DAY -> {
                var pastHours = timePast / HOUR
                if (pastHours <= 0) {
                    pastHours = 1
                }
                return getDateAndHourMinuteTime(dateMillis)
            }
            timePast < WEEK -> {
                var pastDays = timePast / DAY
                if (pastDays <= 0) {
                    pastDays = 1
                }
                return "$pastDays ${GlobalUtil.getString(R.string.days_ago)}"
            }
            timePast < MONTH -> {
                var pastDays = timePast / WEEK
                if (pastDays <= 0) {
                    pastDays = 1
                }
                return "$pastDays ${GlobalUtil.getString(R.string.weeks_ago)}"
            }
            else -> return getDate(dateMillis)
        }
    } else {
        return getDateAndTime(dateMillis)
    }
}

fun isBlockedForever(timeLeft: Long) = timeLeft > 5 * YEAR

/**
 * 提供时间yyyy-MM-dd
 */
fun getDate(dateMillis: Long, pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(dateMillis))
}

/**
 * 提供时间yyyy-MM-dd HH:mm
 */
private fun getDateAndTime(dateMillis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(dateMillis))
}

private fun getDateAndHourMinuteTime(dateMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(dateMillis))
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.inVisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * 显示view
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */
fun setOnClickListener1(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener(fun(it: View) {
        it.block()
    })
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param listener 处理点击事件监听器
 */
fun setOnClickListener2(vararg v: View?, listener: View.OnClickListener) {
    v.forEach { it?.setOnClickListener(listener) }
}


/**
 * Glide加载图片，可以指定圆角弧度。
 *
 * @param url 图片地址
 * @param round 圆角，单位dp
 * @param cornerType 圆角角度
 */
fun ImageView.load(url: String, round: Float = 0f, cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL) {
    if (round == 0f) {
        Glide.with(this.context).load(url).into(this)
    } else {
        val option = RequestOptions.bitmapTransform(RoundedCornersTransformation(dp2px(round), 0, cornerType)).placeholder(R.drawable.shape_album_loading_bg)
        Glide.with(this.context).load(url).apply(option).into(this)
    }
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param url 图片地址
 * @param options 配置参数
 */
fun ImageView.load(url: String, options: RequestOptions.() -> RequestOptions) {
    Glide.with(this.context).load(url).apply(RequestOptions().options()).into(this)
}

/**
 * 启动activity
 */
fun <T : AppCompatActivity?> start(context: Context, clazz: Class<T>) {
    context.startActivity(Intent(context, clazz))
}

fun openShareDialog(context: Context, content: String): Unit {
    if (context is AppCompatActivity)
        ShareDialogFragment.newInstance(content).show(context)
}

/**
 *验证是否登录
 * 未完成功能
 */
fun checkLogin(): Boolean {
    return false
}

const val TAG = "CommendAdapterTools"

const val MINUTE = (60 * 1000).toLong()

const val HOUR = 60 * MINUTE

const val DAY = 24 * HOUR

const val WEEK = 7 * DAY

const val MONTH = 4 * WEEK

const val YEAR = 365 * DAY