package com.crystal.aplayer.module_base.base.mvvm.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crystal.aplayer.module_base.base.http.retrofit.Api;
import com.crystal.aplayer.module_base.base.http.retrofit.RetrofitConfig;
import com.crystal.aplayer.module_base.base.mvvm.viewmodel.base.BaseViewModel;
import com.crystal.aplayer.module_base.tools.observable.Observable;

import java.util.List;



/**
 * 创建者 kiylx
 * 创建时间 9/24/2020 7:24 AM
 * packageName：com.crystal.aplayer.base.mvvm.repo
 * 描述：为viewodel提供数据，DataProvider将会持有DataSource，DataSource可以是本地数据库或是网络
 */
public abstract class BaseDataProvider<T extends Api, VM extends BaseViewModel> extends Observable {
    protected T apiService;//网络接口
//    protected CompositeDisposable compositeDisposable;//管理rxjava订阅，防止内存泄漏
    protected RetrofitConfig retrofitConfig;

    public BaseDataProvider() {
        /*if (compositeDisposable==null){
            compositeDisposable=new CompositeDisposable();
        }*/
    }

    /**
     * 默认方法，提供数据
     *
     * @return
     */
    public abstract List<T> getData();

    /**
     * 将ViewModel注册到此数据提供者，在得到数据或更新后通知viewmodel
     *
     * @param vm
     */
    public void register(@NonNull VM vm) {
        addObserver(vm);
    }

    /**
     * 取消注册ViewModel
     *
     * @param vm null时取消注册所有ViewModel
     */
    public void unRegister(@Nullable VM vm) {
        if (null == vm) {
            deleteObservers();
        } else {
            deleteObserver((vm));
        }
    }

  /*  public void cleanRxObservable(Disposable disposable) {
        if (compositeDisposable != null) {
            if (disposable != null) {
                compositeDisposable.remove(disposable);
            } else {
                compositeDisposable.clear();
            }
        }
    }*/

}
