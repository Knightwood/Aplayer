package com.crystal.aplayer.module_base.base.http.okhttp.interceptor;

import android.os.Build;

import com.crystal.aplayer.module_base.common.util.GlobalUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 16:43
 * packageName：com.crystal.aplayer.module_base.base.http.okhttp
 * 描述：
 *
 * 拼接参数：
 * - `udid`：用户唯一标识。该参数可为空也可去除
 * - `vc`：???，固定值`168`。该参数可为空也可去除
 * - `vn`：客户端版本。该参数可为空也可去除
 * - `deviceModel`：手机信息。该参数可为空也可去除
 * - `first_channel`：???，固定值 `eyepetizer_baidu_market`。该参数可为空也可去除
 * - `last_channel`：???，固定值 `eyepetizer_baidu_market`。该参数可为空也可去除
 * - `system_version_code`：手机系统版本。该参数可为空也可去除
 */
public class BasicParamsInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl.Builder url = originalHttpUrl.newBuilder();
        url.addQueryParameter("udid", GlobalUtil.INSTANCE.getDeviceSerial())
                .addQueryParameter("vc", String.valueOf(GlobalUtil.INSTANCE.getEyepetizerVersionCode()))
                .addQueryParameter("vn", GlobalUtil.INSTANCE.getEyepetizerVersionName())
                .addQueryParameter("size", GlobalUtil.INSTANCE.screenPixel())
                .addQueryParameter("deviceModel", GlobalUtil.INSTANCE.getDeviceModel())
                .addQueryParameter("first_channel", GlobalUtil.INSTANCE.getDeviceBrand())
                .addQueryParameter("last_channel", GlobalUtil.INSTANCE.getDeviceBrand())
                .addQueryParameter("system_version_code", String.valueOf(Build.VERSION.SDK_INT));
        Request request = originalRequest.newBuilder().url(url.build()).method(originalRequest.method(), originalRequest.body()).build();
        return chain.proceed(request);
    }
}
