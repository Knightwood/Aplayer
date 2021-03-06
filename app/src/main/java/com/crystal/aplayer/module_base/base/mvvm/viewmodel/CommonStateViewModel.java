package com.crystal.aplayer.module_base.base.mvvm.viewmodel;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.crystal.aplayer.module_base.base.http.retrofit.ResponseMes;
import com.crystal.aplayer.module_base.base.mvvm.model.StateModel;
import com.crystal.aplayer.module_base.base.mvvm.state.LoadDataState;
import com.crystal.aplayer.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.aplayer.module_base.base.mvvm.state.LoadMethod;
import com.crystal.aplayer.module_base.tools.LogUtil;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/16 17:43
 * packageName：com.crystal.aplayer.module_base.common.mvvm
 * 描述：相比baseViewModel，此类进一步提供更详细的控制流程和方法
 * <p>
 * 统一各state流程
 * 1.初始化界面时获取数据
 * LoadDataState:初始化界面时获取数据的过程
 * 初始化时state：NOTHING->开始准备数据显示在界面上wait_load_data->成功：LOAD_FINISHED->数据显示在界面上显示完成加载，重置为done;
 * 失败：LOAD_FAILED;
 */
public abstract class CommonStateViewModel<D extends BaseDataProvider> extends StateViewModel<D> implements LoadMethod {
    private static final String tag = "CommonViewModel";
    public MutableLiveData<ResponseMes> nowResponseMes;//记录当前执行动作完成后（刷新，加载更多）的结果
    public MutableLiveData<String> nextPage;//下一页的数据地址
    public boolean firstLoad = true;
    private long lastLoadMoreTime = 0;//上次上拉刷新时间

    public CommonStateViewModel() {
        super();
        nowResponseMes = new MutableLiveData<>();
        nextPage = new MutableLiveData<>();
        lastLoadMoreTime=System.currentTimeMillis();
    }
    @Override
    @CallSuper
    public void firstLoadData() {
        stateModel.nowBehavior = StateModel.NowBehavior.InitLoad;//标记当前执行的动作
        stateModel.setLoadDataState(LoadDataState.LOADING, false);//置为loading状态，显示圆圈进度条
    }
    @Override
    @CallSuper
    public void freshData() {
   /*     if (stateModel.nowBehavior != StateModel.NowBehavior.AllDone) {//其他刷新动作都已经完成
            return;
        }
        stateModel.nowBehavior = StateModel.NowBehavior.Refreshing;
   */

        if (this.stateModel.nowBehavior == StateModel.NowBehavior.AllDone) {
            this.stateModel.nowBehavior = StateModel.NowBehavior.Refreshing;
        }
    }

@Override
    public void loadMore() {
        if (stateModel.nowBehavior != StateModel.NowBehavior.AllDone) {
            return;
        }
        if (!canLoadMore())
            return;
        String url = nextPage.getValue();
        if (url == null) {
            // TODO: 2020/11/17 发送responsemes，声明没有更多数据
            LogUtil.d(tag, "没有下一页数据了");
            return;
        }
        stateModel.nowBehavior = StateModel.NowBehavior.LoadMoreData;
    }
    @Override
    public boolean canLoadMore() {
        long now = System.currentTimeMillis();
        if (lastLoadMoreTime - now > 1000) {
            lastLoadMoreTime = now;
            return true;
        } else {
            lastLoadMoreTime = now;
            return false;
        }
    }
}
