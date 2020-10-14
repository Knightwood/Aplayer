package com.crystal.aplayer.all_module.home.recommend;

import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.all_module.home.repo.HomeDataProvider;
import com.crystal.module_base.base.http.retrofit.ResponseMes;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.contract.LoadState;
import com.crystal.module_base.base.mvvm.viewmodel.BaseViewModel;
import com.crystal.module_base.common.http.AllApiConfig;
import com.crystal.module_base.common.http.bean.mainpage.RecommendBean;
import com.crystal.module_base.tools.LogUtil;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/9 13:22
 * packageName：com.crystal.aplayer.all_module.home.nominate
 * 描述：首页-推荐
 */
public class RecommendViewModel extends BaseViewModel<HomeDataProvider> {
    private static final String tag="RecommendFragment";

    public MutableLiveData<RecommendBean> recommendBeanMutableLiveData;
    public MutableLiveData<List<RecommendBean.ItemListBeanX>> dataList;
    public MutableLiveData<String> nextPage;

    public RecommendViewModel() {
        super();
        recommendBeanMutableLiveData=new MutableLiveData<>();
        dataList=new MutableLiveData<>();
        nextPage=new MutableLiveData<>();
    }

    @Override
    protected HomeDataProvider setDataProvider() {
        return HomeDataProvider.getInstance();
    }

    @Override
    protected void whenUpdate(Object[] arg) {
        LogUtil.d(tag,"viewmodel中whenUpdate方法被调用");
        if (arg[0] instanceof RecommendBean) {
            recommendBeanMutableLiveData.postValue((RecommendBean) arg[0]);
        }
    }

    /**
     * 获取网络数据失败
     * @param mes
     */
    @Override
    protected void loadFailed(ResponseMes mes) {
        LogUtil.d(tag,"viewmodel中loadFailed方法被调用");
        stateModel.loadDataState.postValue(LoadDataState.LOAD_FAILED);
    }


    public void getNextPage() {
        dataProvider.getRecommend(nextPage.getValue());
    }

    public void freshData(){
        LogUtil.d(tag,"viewmodel获取数据更新");
        dataProvider.getRecommend(AllApiConfig.BASE_URL+AllApiConfig.DAILY_RECOMMEND);
    }
}
