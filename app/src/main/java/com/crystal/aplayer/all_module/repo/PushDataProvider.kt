package com.crystal.aplayer.all_module.repo

import com.crystal.aplayer.AppApplication
import com.crystal.module_base.base.http.retrofit.RetrofitConfig
import com.crystal.module_base.base.mvvm.repo.BaseDataProvider
import com.crystal.module_base.base.mvvm.viewmodel.base.BaseViewModel
import com.crystal.module_base.common.http.AllApiConfig
import com.crystal.module_base.common.http.api.NotifyMesService
import com.crystal.module_base.common.http.bean2.PushMessage
import retrofit2.http.Url

/**
 *创建者 kiylx
 *创建时间 2020/12/5 20:03
 *packageName：com.crystal.aplayer.all_module.repo
 *描述：
 */
    class PushDataProvider private constructor() : BaseDataProvider<NotifyMesService, BaseViewModel<*>>() {
    init {
        //实例化网络api
        retrofitConfig = RetrofitConfig.getInstance((AppApplication.getInstance() as AppApplication).threadPool.executorService)
        apiService = retrofitConfig.getApiInstance<NotifyMesService>(NotifyMesService::class.java, AllApiConfig.BASE_URL)
    }

    override fun getData(): MutableList<NotifyMesService> {
        TODO("Not yet implemented")
    }

    fun getPushMes(@Url url: String) {
        retrofitConfig.parsingCallgetData(apiService.getPushMes(url), PushMessage::class.java, this)
    }

    companion object {
        private var mInstance: PushDataProvider? = null
            get() {
                return field ?: PushDataProvider()
            }

        @JvmStatic
        @Synchronized
        fun getInstance(): PushDataProvider {
            return requireNotNull(mInstance)
        }
    }
}