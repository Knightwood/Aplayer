package com.crystal.aplayer.all_module.community.commend;

import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.all_module.repo.HomeDataProvider;
import com.crystal.aplayer.module_base.base.http.retrofit.ResponseMes;
import com.crystal.aplayer.module_base.base.mvvm.model.StateModel;
import com.crystal.aplayer.module_base.base.mvvm.repo.BaseLocateDB;
import com.crystal.aplayer.module_base.base.mvvm.state.LoadDataState;
import com.crystal.aplayer.module_base.common.http.AllApiConfig;
import com.crystal.aplayer.module_base.common.http.bean2.CommunityRecommend;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.CommonStateViewModel;
import com.crystal.aplayer.module_base.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/25 21:37
 * packageName：com.crystal.aplayer.all_module.commend
 * 描述：
 */
public class CommendViewModel extends CommonStateViewModel<HomeDataProvider> {
    private static final String tag = "CommendViewModel";

    public MutableLiveData<CommunityRecommend> commendBeanMutableLiveData;
    public MutableLiveData<List<CommunityRecommend.Item>> dataLists;

    public CommendViewModel() {
        super();
        commendBeanMutableLiveData = new MutableLiveData<>();
        dataLists = new MutableLiveData<>();
    }

    @Override
    protected HomeDataProvider setDataProvider() {
        return HomeDataProvider.getInstance();
    }

    /**
     * @param arg
     */
    @Override
    protected void whenUpdate(Object[] arg) {
        CommunityRecommend commend;
        ResponseMes responseMes;
        if (arg[0] != null && arg[1] != null) {
            if (arg[0] instanceof CommunityRecommend && arg[1] instanceof ResponseMes) {
                commend = (CommunityRecommend) arg[0];//拿到数据
                responseMes = (ResponseMes) arg[1];
                commendBeanMutableLiveData.postValue(commend);
            } else {
                //todo 获得的数据类型不对，可能是其他viewmodel所需的数据，中止处理
                return;
            }
            LogUtil.d(tag, "执行的获取数据的动作" + stateModel.nowBehavior.name());
            if (!responseMes.hasError()) {
                // 获取数据没问题，根据不同的动作（nowBehavior）执行不同的数据解析
                if (stateModel.nowBehavior == StateModel.NowBehavior.Refreshing) {//下拉刷新
                    dataLists.postValue(commend.getItemList());
                } else {//第一次加载或上拉加载更多
                    List<CommunityRecommend.Item> tmp = dataLists.getValue();//旧数据
                    if (tmp != null && tmp.size() != 0)
                        tmp.addAll(commend.getItemList());
                    else
                    tmp=new ArrayList<>(commend.getItemList());
                    dataLists.postValue(tmp);
                }
                nextPage.postValue(commend.getNextPageUrl());
                LogUtil.d(tag, "下一页地址： " + commend.getNextPageUrl() + "数量： " + commend.getCount());
                nowResponseMes.postValue(responseMes);//发送数据后的情况消息
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
                        return;
                    }
                }
                nowResponseMes.postValue(responseMes);
            }

        }
    }

    @Override
    public void firstLoadData() {
        super.firstLoadData();
        dataProvider.getCommunityRec(AllApiConfig.BASE_URL + AllApiConfig.COMMUNITY_RECOMMEND);
    }

    @Override
    public void freshData() {
        super.freshData();
        LogUtil.d(tag, "viewmodel获取数据更新");
        dataProvider.getCommunityRec(AllApiConfig.BASE_URL + AllApiConfig.COMMUNITY_RECOMMEND);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        dataProvider.getCommunityRec(nextPage.getValue());
    }
}
