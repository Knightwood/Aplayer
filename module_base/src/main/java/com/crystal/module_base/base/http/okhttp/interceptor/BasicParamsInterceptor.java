package com.crystal.module_base.base.http.okhttp.interceptor;

import android.os.Build;

import com.crystal.module_base.common.util.GlobalUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 16:43
 * packageName：com.crystal.module_base.base.http.okhttp
 * 描述：
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
