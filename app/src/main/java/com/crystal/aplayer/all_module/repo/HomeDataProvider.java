package com.crystal.aplayer.all_module.repo;

import com.crystal.aplayer.AppApplication;
import com.crystal.aplayer.module_base.base.http.retrofit.ParseIntercept;
import com.crystal.aplayer.module_base.common.http.api.MainPageApiService;
import com.crystal.aplayer.module_base.base.http.retrofit.RetrofitConfig;
import com.crystal.aplayer.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.base.BaseViewModel;
import com.crystal.aplayer.module_base.common.http.AllApiConfig;
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend;
import com.crystal.aplayer.module_base.common.http.bean2.Daily;
import com.crystal.aplayer.module_base.common.http.bean2.Discovery;
import com.crystal.aplayer.module_base.common.http.bean2.Follow;
import com.crystal.aplayer.module_base.common.http.bean2.HomePageRecommend;
import com.crystal.aplayer.module_base.tools.LogUtil;

import java.util.List;

import retrofit2.http.Url;

/**
 * 创建者 kiylx
 * 创建时间 9/25/2020 10:42 PM
 * packageName：com.crystal.aplayer.all_module.home.repo
 * 描述：
 */
public class HomeDataProvider extends BaseDataProvider<MainPageApiService, BaseViewModel> {
    private static final String tag = "HomeDataProvider";

    public static HomeDataProvider getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;
        private HomeDataProvider instance;

        Singleton() {
            instance = new HomeDataProvider();
        }

        public HomeDataProvider getInstance() {
            return instance;
        }
    }

    /**
     * 实例化网络api
     */
    private HomeDataProvider() {
        super();
        retrofitConfig = RetrofitConfig.getInstance(((AppApplication) AppApplication.getInstance()).getThreadPool().getExecutorService());
        apiService = retrofitConfig.getApiInstance(MainPageApiService.class, AllApiConfig.BASE_URL);
    }

    @Override
    public List<MainPageApiService> getData() {
        return null;
    }

    //发现
    public void getDiscovery(@Url String url, ParseIntercept intercept) {
        LogUtil.d(tag, "获取Discovery方法被调用");
        retrofitConfig.parsingCallgetData(apiService.getDiscovery(url), Discovery.class, intercept);
    }

    //推荐
    public void getRecommend(@Url String url, ParseIntercept intercept) {
        retrofitConfig.parsingCallgetData(apiService.getRecommend(url), HomePageRecommend.class, intercept);
    }

    //日常
    public void getDailyFeed(@Url String url, ParseIntercept intercept) {
        retrofitConfig.parsingCallgetData(apiService.getDailyFeed(url), Daily.class, intercept);
    }

    //社区推荐
    public void getCommunityRec(@Url String url, ParseIntercept intercept) {
        retrofitConfig.parsingCallgetData(apiService.getCommunityRec(url), CommunityRecommend.class, intercept);
    }

    //社区关注
    public void getCommunityFollow(@Url String url, ParseIntercept intercept) {
        retrofitConfig.parsingCallgetData(apiService.getCommunityFollow(url), Follow.class, intercept);
    }
}
