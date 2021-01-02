package com.crystal.module_base.tools.live_data_bus.core;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.crystal.module_base.tools.live_data_bus.utils.PostTask;
import com.crystal.module_base.tools.live_data_bus.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 创建者 kiylx
 * 创建时间 2020/12/28 21:40
 * packageName：com.crystal.module_base.tools.live_data_bus
 * 描述：推送事件由通道实现
 */

public class Channel<T> implements Action<T> {
    @NotNull
    private final String key;
    @NotNull
    private String messageClassName;
    //通道标识符
    private final UUID channelUUID = UUID.randomUUID();
    //存放消息的信箱
    private final LiveDataMod<T> inBox = new LiveDataMod<>();
    //通道是否可以发送消息
    private boolean isCanPush = true;
    private Config config;
    //postmethod
    private final PostMethod mPostMethod = new PostMethod();

    //存放观察者包装类实例的map
    private final Map<UUID, ObserverWrapperMod<? super T>> mObservers = new HashMap<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public Channel(@NonNull String key) {
        this.key = key;
    }

    public Channel(@NotNull String key, @NotNull String messageClassName) {
        this.key = key;
        this.messageClassName = messageClassName;
    }

    public UUID getUuid() {
        return channelUUID;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    public Config config() {
        if (config == null)
            config = new Config();
        return config;
    }

    @Override
    public void post(T value) {
        post(value, 0L, null);
    }


    @Override
    public void postDelay(T value, long delay) {
        post(value, delay, null);
    }

    @Override
    public void postDelay(LifecycleOwner sender, T value, long delay) {
        post(value, delay, sender);
    }

    private void post(T value, long delay, LifecycleOwner sender) {
        if (delay < 1L) {
            if (Utils.isMainThread()) {
                inBox.setValue(value);
            } else {
                mainHandler.post(new PostTask(new Object[]{value, sender}, mPostMethod));
            }
        } else {
            mainHandler.postDelayed(new PostTask(new Object[]{value, sender}, mPostMethod), delay);
        }

    }

    private class PostMethod implements PostTask.Method {

        @Override
        public void method(Object[] args) {
            T value = (T) args[0];
            LifecycleOwner owner = (LifecycleOwner) args[1];

            if (value != null && owner != null) {
                //带生命周期的发送消息的时候sender处于非激活状态时，消息取消发送
                if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    inBox.setValue(value);
                }
            }
            if (value != null && owner == null) {
                //不带有生命周期
                inBox.setValue(value);
            }

        }
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull ObserverMod<T> observerMod) {
        observerMod.config().setSticky(false);
        observerInternal(owner, observerMod);
    }

    @Override
    public void observeSticky(@NonNull LifecycleOwner owner, @NonNull ObserverMod<T> observerMod) {
        observerMod.config().setSticky(true);
        ObserverWrapperMod existing = observerInternal(owner, observerMod);
    }

    @Override
    public void observeForever(@NonNull ObserverMod<T> observerMod) {
        observerMod.config().setSticky(false);
        observerForeverInternal(observerMod);
    }

    @Override
    public void observeStickyForever(@NonNull ObserverMod<T> observerMod) {
        observerMod.config().setSticky(true);
        ObserverWrapperMod existing = observerForeverInternal(observerMod);
    }

    @Override
    public void removeObserver(@NonNull ObserverMod<T> observerMod) {
        ObserverWrapperMod<? super T> existing = mObservers.get(observerMod.uuid);
        if (existing != null) {
            inBox.removeObserver(existing.observer);
        }
    }

    /**
     * @param owner
     * @param observerMod 在observerInternal方法被调用之前，observer与observerMod之间没有产生联系，ObserverWrapperMod也不存在。
     *                    因此，当调用observerInternal时，若从observers这个map中找得到，则说明已经new出来了observerWrapperMod，
     *                    并与lifecycleOwner建立了关系。
     */
    private ObserverWrapperMod observerInternal(LifecycleOwner owner, ObserverMod<T> observerMod) {
        ObserverWrapperMod<? super T> existing = putIfAbsent(observerMod);
        if (existing != null) {
            //刚刚new出来ObserverWrapperMod，还没有与LifecycleOwner建立联系。
            if (Utils.isMainThread())
                inBox.observe(owner, existing.observer);
            else
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        inBox.observe(owner, existing.observer);
                    }
                });
            return existing;
        }
        return null;
    }

    private ObserverWrapperMod observerForeverInternal(ObserverMod<T> observerMod) {
        ObserverWrapperMod<? super T> existing = putIfAbsent(observerMod);
        if (existing != null) {
            //刚刚new出来ObserverWrapperMod，还没有与LifecycleOwner建立联系。
            if (Utils.isMainThread())
                inBox.observeForever(existing.observer);
            else
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        inBox.observeForever(existing.observer);
                    }
                });
            return existing;
        }
        return null;
    }

    /**
     * @param observerMod
     * @return 在map中能找到observer对应的值，返回null。
     * 在map中找不到observer对应的值，放入new出来的值，并返回new出来的值。
     */
    private ObserverWrapperMod<? super T> putIfAbsent(ObserverMod<? super T> observerMod) {
        ObserverWrapperMod<? super T> v = mObservers.get(observerMod.uuid);
        if (v == null) {
            v = new ObserverWrapperMod<>(observerMod);
            mObservers.put(observerMod.uuid, v);
            return v.generateObserver(inBox.getVersion());
        }
        return null;
    }

    /**
     * 查找mObservers中lastVersion与mVersion不一致的对象，没有查找到，返回null
     */
    public ObserverWrapperMod<? super T> getRenegade() {
        for (Map.Entry<UUID, ObserverWrapperMod<? super T>> entry : mObservers.entrySet()) {
            if (entry.getValue().lastVersion != inBox.getVersion())

                return entry.getValue();
        }
        return null;
    }

    /**
     * 查找mObservers中lastVersion与mVersion不一致的对象，没有查找到，返回false
     */
    public boolean isHasRenegade() {
        for (Map.Entry<UUID, ObserverWrapperMod<? super T>> entry : mObservers.entrySet()) {
            if (entry.getValue().lastVersion != inBox.getVersion())
                return true;
        }
        return false;
    }

    protected class Config {
        public Config setCanPushMes(boolean b) {
            isCanPush = b;
            return this;
        }
    }
}
