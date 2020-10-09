package com.crystal.module_base.base.mvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.crystal.module_base.base.mvvm.repo.BaseDataProvider;
import com.crystal.module_base.tools.observable.Observable;
import com.crystal.module_base.tools.observable.Observer;

/**
 * 创建者 kiylx
 * 创建时间 9/26/2020 10:24 PM
 * packageName：com.crystal.module_base.base.mvvm.viewmodel
 * 描述：另一个版本；持有dataprovider，但不会让dataprovider持有此viewmodel
 */
public abstract class BaseViewModel2<D extends BaseDataProvider> extends ViewModel  {
    protected D dataProvider = null;

    public BaseViewModel2() {
        this.dataProvider = setDataProvider();
    }

    /**
     * 子类提供DataProvider实例
     */
    protected abstract D setDataProvider();

    @Override
    protected void onCleared() {
        super.onCleared();
        dataProvider = null;
    }}
