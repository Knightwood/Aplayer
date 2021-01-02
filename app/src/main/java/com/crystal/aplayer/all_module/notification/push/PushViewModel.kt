package com.crystal.aplayer.all_module.notification.push

import androidx.lifecycle.MutableLiveData
import com.crystal.aplayer.all_module.repo.PushDataProvider
import com.crystal.module_base.base.http.retrofit.ResponseMes
import com.crystal.module_base.base.mvvm.model.StateModel
import com.crystal.module_base.base.mvvm.repo.BaseLocateDB
import com.crystal.module_base.base.mvvm.state.LoadDataState
import com.crystal.module_base.common.http.AllApiConfig

import com.crystal.module_base.common.http.bean2.PushMessage
import com.crystal.module_base.common.vm.CommonViewModel
import com.crystal.module_base.tools.LogUtil


/**
 *创建者 kiylx
 *创建时间 2020/12/4 21:36
 *packageName：com.crystal.aplayer.all_module.notification.push
 *描述：
 */
class PushViewModel() : CommonViewModel<PushDataProvider>() {

    private var pushBeanMutableLiveData: MutableLiveData<PushMessage> = MutableLiveData()
    var dataLists: MutableLiveData<List<PushMessage.Message>> = MutableLiveData()

    override fun setDataProvider(): PushDataProvider {
        return PushDataProvider.getInstance()
    }

    override fun whenUpdate(arg: Array<out Any>?) {
        val pushBean: PushMessage? = arg?.get(0) as? PushMessage
        val responseMes: ResponseMes? = arg?.get(1) as? ResponseMes

        if (pushBean != null && responseMes != null) {
            pushBeanMutableLiveData.postValue(pushBean)
        } else {
            //todo 获得的数据类型不对，可能是其他viewmodel所需的数据，中止处理
            return
        }
        LogUtil.d(tag, "执行的获取数据的动作" + stateModel.nowBehavior.name)
        if (!responseMes.hasError()) {
            // 获取数据没问题，根据不同的动作（nowBehavior）执行不同的数据解析
            if (stateModel.nowBehavior == StateModel.NowBehavior.Refreshing) { //下拉刷新
                dataLists.postValue(pushBean.itemList)
            } else { //第一次加载或上拉加载更多
                var tmp: java.util.ArrayList<PushMessage.Message>? = dataLists.getValue() as java.util.ArrayList<PushMessage.Message>? //旧数据
                if (tmp != null && tmp.size != 0) tmp.addAll(pushBean.itemList) else tmp = ArrayList(pushBean.itemList)
                dataLists.postValue(tmp)
            }
            nextPage.postValue(pushBean.nextPageUrl)
            LogUtil.d(tag, "下一页地址： " + pushBean.nextPageUrl)
            nowResponseMes.postValue(responseMes) //发送数据后的情况消息
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
                    stateModel.setLoadDataState(LoadDataState.LOAD_FAILED, true)
                    return
                }
            }
            nowResponseMes.postValue(responseMes)
        }

    }

    override fun firstLoadData() {
        super.firstLoadData()
        dataProvider.getPushMes(AllApiConfig.BASE_URL + AllApiConfig.PUSHMES)
    }

    override fun freshData() {
        super.freshData()
        LogUtil.d(tag, "viewmodel获取数据更新")
        dataProvider.getPushMes(AllApiConfig.BASE_URL + AllApiConfig.PUSHMES)
    }

    override fun loadMore() {
        super.loadMore()
        dataProvider.getPushMes(nextPage?.value.toString())
    }

    companion object {
        const val tag: String = "推送通知viewmodel"
    }
}