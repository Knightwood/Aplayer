package com.crystal.aplayer.all_module.util;

import android.app.Activity;
import android.content.Context;

import com.crystal.aplayer.all_module.login.LoginActivity;
import com.crystal.module_base.tools.SomeTools;

import org.jetbrains.annotations.NotNull;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/19 22:21
 * packageName：com.crystal.aplayer.all_module.util
 * 描述：
 */
public class CommonActionUrlUtil {

    public static void process(Context context, String actionUrl) {
       process(context, actionUrl, null);
    }

    public static void process(Context context, String actionUrl, String s) {
        SomeTools.INSTANCES.showToast(context, s);
    }

    /**通用的分享处理
     * @param activity
     * @param shareContent
     */
    public static void processShare(@NotNull Activity activity, @NotNull String shareContent) {
        ToolsKt.openShareDialog(activity,shareContent);
    }

    /**
     * 通用的收藏处理，当前未实现具体功能
     */
    public static void processFavorites() {

    }

    /**
     * 通用的回复处理，当前未实现具体功能
     */
    public static void processReply() {

    }

    /**
     * 通用的“喜欢”处理，当前未实现具体功能
     * @param context
     * @param url
     */
    public static void processCollection(Context context,String url) {
        ToolsKt.start(context, LoginActivity.class);
    }
}
