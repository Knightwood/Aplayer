package com.crystal.aplayer.all_module.home.recommend;

import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.all_module.repo.HomeDataProvider;
import com.crystal.aplayer.module_base.base.http.retrofit.ParseIntercept;
import com.crystal.aplayer.module_base.base.http.retrofit.ResponseMes;
import com.crystal.aplayer.module_base.base.mvvm.model.StateModel;
import com.crystal.aplayer.module_base.base.mvvm.repo.BaseLocateDB;
import com.crystal.aplayer.module_base.base.mvvm.state.LoadDataState;
import com.crystal.aplayer.module_base.common.http.AllApiConfig;
import com.crystal.aplayer.module_base.common.http.bean2.HomePageRecommend;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.CommonStateViewModel;
import com.crystal.aplayer.module_base.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/9 13:22
 * packageName：com.crystal.aplayer.all_module.home.nominate
 * 描述：首页-推荐
 */
public class RecommendViewModel extends CommonStateViewModel<HomeDataProvider> {
    private static final String tag="RecommendFragment";

    public MutableLiveData<HomePageRecommend> recommendBeanMutableLiveData;
    public MutableLiveData<List<HomePageRecommend.Item>> dataLists;
    private final ParseIntercept intercept=new ParseIntercept() {
        @Override
        public void intercept(Object... args) {
            HomePageRecommend recommend= (HomePageRecommend) args[0];//拿到数据
            ResponseMes responseMes= (ResponseMes) args[1];

            if (!responseMes.hasError()) {
                recommendBeanMutableLiveData.postValue(recommend);
                // 获取数据没问题，根据不同的动作（nowBehavior）执行不同的数据解析
                if (stateModel.nowBehavior == StateModel.NowBehavior.Refreshing) {//下拉刷新
                    dataLists.postValue(recommend.getItemList());
                } else {//第一次加载或上拉加载更多
                    List<HomePageRecommend.Item> tmp = dataLists.getValue();//旧数据
                    if (tmp != null && tmp.size() != 0)
                        tmp.addAll(recommend.getItemList());
                    else
                        tmp = new ArrayList<>(recommend.getItemList());
                    dataLists.postValue(tmp);
                }
                nextPage.postValue(recommend.getNextPageUrl());
                LogUtil.d(tag, "下一页地址： " + recommend.getNextPageUrl() + "数量： " + recommend.getCount());
            } else {
                // 获取数据不成功，出错
                if (stateModel.nowBehavior == StateModel.NowBehavior.InitLoad) {
                    // TODO: 2020/11/16 从数据库拿数据，注意此方法本来就在子线程。
                    //  获取本地数据得阻塞。
                    //  要还有可能修改responseMes
                    if (BaseLocateDB.getData()) {
                        // TODO: 2020/11/16 从数据库拿数据成功
                    } else {
                        // TODO: 2020/11/16 从数据库获取数据失败，标记加载过程失败
                        stateModel.setLoadDataState(LoadDataState.LOAD_FAILED, true);
                    }
                }
            }
            nowResponseMes.postValue(responseMes);//发送数据后的情况消息

        }
    };

    public RecommendViewModel() {
        super();
        recommendBeanMutableLiveData=new MutableLiveData<>();
        dataLists =new MutableLiveData<>();
        nextPage=new MutableLiveData<>();
    }

    @Override
    protected HomeDataProvider setDataProvider() {
        return HomeDataProvider.getInstance();
    }

    @Override
    public void firstLoadData() {
        super.firstLoadData();
        dataProvider.getRecommend(AllApiConfig.BASE_URL+AllApiConfig.DAILY_RECOMMEND,intercept);
    }

    @Override
    public void freshData() {
        super.freshData();
        LogUtil.d(tag, "viewmodel获取数据更新");
        dataProvider.getRecommend(AllApiConfig.BASE_URL+AllApiConfig.DAILY_RECOMMEND,intercept);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        dataProvider.getRecommend(nextPage.getValue(),intercept);
    }


}
