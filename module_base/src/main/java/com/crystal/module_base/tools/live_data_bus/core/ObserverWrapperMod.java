package com.crystal.module_base.tools.live_data_bus.core;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.crystal.module_base.tools.live_data_bus.core.LiveDataMod.START_VERSION;

/**
 * 创建者 kiylx
 * 创建时间 2020/12/31 20:21
 * packageName：com.crystal.module_base.tools.live_data_bus
 * 描述：
 */
//观察者的包装
public class ObserverWrapperMod<T> {
    int lastVersion = START_VERSION;
    Observer<? super T> observer = null;
    //此UUID与ObserverMod中的保持一致，且用于标识ObserverWrapperMod
    @NotNull
    private UUID uuid;
    @NotNull
    private ObserverMod<? super T> observerMod;


    public ObserverWrapperMod(@NonNull ObserverMod<? super T> observerMod) {
        this.observerMod = observerMod;
        this.uuid = observerMod.uuid;
    }
    /**
     * @param observer
     * @return observer是否与已有的ObserverWrapperMod建立联系
     */
    public boolean isAttachedTo(Observer<? super T> observer) {
        return observer.equals(this.observer);
    }

    /**
     * 生成LiveData的observer
     *
     * @return
     */
    public ObserverWrapperMod<T> generateObserver(int mVersion) {
        if (observer == null) {
            observer = new Observer<T>() {
                @Override
                public void onChanged(T t) {
                    if (observerMod.isWantAcceptMessage()) {
                        if (observerMod.isSticky() && lastVersion == START_VERSION)
                            observerMod.onChanged(t);
                        else {
                            //非粘性下，拦截推送给新创建的observer的消息
                            if (lastVersion != START_VERSION)
                                observerMod.onChanged(t);
                        }
                        lastVersion = mVersion;
                    }
                }
            };

        }
        return this;
    }

}
