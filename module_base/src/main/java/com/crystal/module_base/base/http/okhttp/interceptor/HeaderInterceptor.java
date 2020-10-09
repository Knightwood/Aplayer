package com.crystal.module_base.base.http.okhttp.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 16:43
 * packageName：com.crystal.module_base.base.http.okhttp
 * 描述：
 */
public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original=chain.request();

        return null;
    }
}
