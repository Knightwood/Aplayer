package com.crystal.module_base.common.util;

import java.util.Locale;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/19 23:18
 * packageName：com.crystal.module_base.common.util
 * 描述：
 */
public class Process {
    /**
     * 把得到的视频时间转换成string类型
     * @param time 用秒表示的时间
     * @return
     */
    public static String getTimeFromInt(int time) {
        //60秒每分
        //360秒每时
        //8640秒每天
        if (time > 8640) {
            return String.format(Locale.getDefault(), "%02d:%02d", time / 360, time % 60);
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", time / 8640, (time % 8640) / 360, time % 60);

    }
}
