package com.crystal.aplayer.all_module.home.discover;

import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.all_module.home.repo.HomeDataProvider;
import com.crystal.module_base.base.http.retrofit.ResponseMes;
import com.crystal.module_base.common.http.AllApiConfig;
import com.crystal.module_base.common.http.bean.mainpage.DiscoveryBean;
import com.crystal.module_base.common.mvvm.CommonViewModel;
import com.crystal.module_base.tools.LogUtil;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 9/25/2020 10:42 PM
 * packageName：com.crystal.aplayer.all_module.home.discover.mvvm
 * 描述：
 */
public class DiscoverViewModel extends CommonViewModel<HomeDataProvider> {
    private static final String tag="DiscoverFragment";

    public MutableLiveData<DiscoveryBean> discoveryBeanMutableLiveData;
    public MutableLiveData<List<DiscoveryBean.ItemListBeanX>> dataList;
    public MutableLiveData<String> nextPage;

    public DiscoverViewModel() {
        super();
        discoveryBeanMutableLiveData=new MutableLiveData<>();
        dataList=new MutableLiveData<>();
        nextPage=new MutableLiveData<>();
    }

    @Override
    protected HomeDataProvider setDataProvider() {
        return HomeDataProvider.getInstance();
    }

    @Override
    protected void getRemoteDataSuccess(Object[] arg) {
        super.getRemoteDataSuccess(arg);
        LogUtil.d(tag, "viewmodel中whenUpdate方法被调用");
        if (arg[0] instanceof DiscoveryBean) {
            discoveryBeanMutableLiveData.postValue((DiscoveryBean) arg[0]);
        }
    }

    /**
     * 获取网络数据失败
     * @param mes
     */
    @Override
    protected void getRemoteDataFailed(ResponseMes mes) {
        super.getRemoteDataFailed(mes);
        LogUtil.d(tag, "viewmodel中loadFailed方法被调用");
    }


    public void getNextPage() {
        dataProvider.getDiscovery(nextPage.getValue());
    }

    public void freshData(){
        LogUtil.d(tag,"viewmodel获取数据更新");
        dataProvider.getDiscovery(AllApiConfig.BASE_URL+AllApiConfig.DISCOVERY);
    }
}
