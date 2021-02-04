package com.crystal.module_base.base.http.retrofit;


import com.crystal.module_base.base.http.okhttp.interceptor.BasicParamsInterceptor;
import com.crystal.module_base.base.http.okhttp.interceptor.HeaderInterceptor;
import com.crystal.module_base.base.http.okhttp.interceptor.LoggingInterceptor;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.observable.Observable;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 创建者 kiylx
 * 创建时间 9/26/2020 10:43 PM
 * packageName：com.crystal.module_base.base.http
 * 描述：获取retrofit的工具类
 */
public class RetrofitConfig {
    private static final String tag = "RetrofitConfig";

    private ExecutorService executorService;
    private OkHttpClient okHttpClient;
    private HashMap<String, Retrofit> retrofitHashMap;

    /**
     * 非第一次获取此类，可以调用此方法
     *
     * @return 返回此类的实例
     */
    public static RetrofitConfig getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * 第一次获取此类时可以调用此方法，以及传入线程池。
     *
     * @param executorService 线程池
     * @return
     */
    public static RetrofitConfig getInstance(@NotNull ExecutorService executorService) {
        return Singleton.INSTANCE.getInstance(executorService);
    }

    private static enum Singleton {
        INSTANCE;
        private RetrofitConfig retrofitConfig;

        Singleton() {
            retrofitConfig = new RetrofitConfig();
        }

        public RetrofitConfig getInstance(ExecutorService executorService) {
            if (retrofitConfig.getExecutorService() == null)
                retrofitConfig.setExecutorService(executorService);
            return retrofitConfig;
        }

        public RetrofitConfig getInstance() {
            return retrofitConfig;
        }
    }

    //私有构造方法
    private RetrofitConfig() {
        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new BasicParamsInterceptor())
                .build();
        retrofitHashMap = new HashMap<>();
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Retrofit getRetrofit(@NotNull String baseUrl) {
        return initRetrofit(baseUrl);
    }

    /**
     * @param baseUrl
     * @return 根据baseurl初始化一个retrofit并返回
     */
    private Retrofit initRetrofit(String baseUrl) {
        if (!retrofitHashMap.containsKey(baseUrl)) {
            retrofitHashMap.put(baseUrl, new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build());
        }
        return retrofitHashMap.get(baseUrl);
    }

    /**
     * @param apiClazz 定义的api类
     * @param baseUrl  baseurl
     * @param <T>
     * @return 返回api实例
     */
    public <T> T getApiInstance(Class<T> apiClazz, String baseUrl) {
        LogUtil.d(tag, "获得apiservice" + Thread.currentThread());
        try {
            Retrofit retrofit = initRetrofit(baseUrl);
            return retrofit.create(apiClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param call      api实例中网络调用得到的call
     * @param beanClazz 网络返回的数据类型，即定义的网络返回的json对应的bean类
     * @param <T>       网络返回的数据类型
     * @param host      继承自observable的数据提供类，viewmodel会订阅这个数据提供类
     * @return 返回call得到的responsebody
     */
    public <T> void parsingCallgetData(@NotNull Call<T> call, @NotNull Class beanClazz, Observable host) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (executorService == null) {
                    LogUtil.d(tag, "解析response所需线程池不存在");
                    return;
                }
                executorService.execute(() -> {
                    LogUtil.d(tag, "解析response:  ");
                    ResponseMes mes = new ResponseMes(response.code(), response.message());
                    host.setChanged();
                    if (response.isSuccessful()) {
                        LogUtil.d(tag, "解析response: 成功 " + host.countObservers());
                        host.notifyObservers(beanClazz.cast(response.body()), mes);
                    } else {
                        LogUtil.d(tag, "解析response: 失败 ");
                        host.notifyObservers(null, mes);
                    }

                });
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                LogUtil.d("Error", t.getMessage());
            }
        });


    }
    /**
     * @param call      api实例中网络调用得到的call
     * @param beanClazz 网络返回的数据类型，即定义的网络返回的json对应的bean类
     * @param <T>       网络返回的数据类型
     * @param intercept   viewmodel实现此接口, parsingCallgetData会在解析数据后回调此接口
     * @return 返回call得到的responsebody
     */
    public <T> void parsingCallgetData(@NotNull Call<T> call, @NotNull Class beanClazz, final ParseIntercept intercept) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (executorService == null) {
                    LogUtil.d(tag, "解析response所需线程池不存在");
                    return;
                }
                executorService.execute(() -> {
                    LogUtil.d(tag, "解析response:  ");
                    ResponseMes mes = new ResponseMes(response.code(), response.message());
                    if (response.isSuccessful()) {
                        LogUtil.d(RetrofitConfig.tag, "解析response: 成功 ");
                        intercept.intercept(beanClazz.cast(response.body()), mes);
                        return;
                    }
                    LogUtil.d(RetrofitConfig.tag, "解析response: 失败 ");
                    intercept.intercept(null, mes);


                });
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                LogUtil.d("Error", t.getMessage());
            }
        });


    }
}
