package com.crystal.module_base.tools.baseadapter2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.module_base.base.BaseApplication;
import com.crystal.module_base.tools.LogUtil;
import com.crystal.module_base.tools.SomeTools;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/22 22:16
 * packageName：com.crystal.module_base.tools.baseadapter2
 * 描述：
 */
public abstract class BaseAdapter3<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    private static final  String tag="BaseAdapter3";
    protected List<T> list = null;//T类型的bean的list集合
    protected WeakReference<Context> contextWeakReference;
    private int lastListSize = 0;//记录list的size

    public BaseAdapter3(@NonNull List<T> list) {
        this(list,null);
    }

    public BaseAdapter3( @NonNull List<T> list, Context context) {
        this.list =list;
        this.contextWeakReference = new WeakReference<>(context==null? BaseApplication.getContext():context);
        recordListSize(list);
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    public void removeOldData(@NonNull List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        recordListSize(list);
        notifyDataSetChanged();
    }

    /**
     * @param newList 不仅包含旧数据还包含新数据
     */
    public void addMoreData(@NonNull List<T> newList) {
        if (this.list == null) {
            this.list = newList;
            recordListSize(newList);
            notifyDataSetChanged();
        } else {
            int newListSize = newList.size();
            notifyItemRangeInserted(lastListSize,newListSize);
            LogUtil.d(tag,"更新范围："+this.lastListSize+"//"+newListSize);
            recordListSize(newListSize);
        }

    }

    /**
     * @param newList 只包含新数据但不包含旧数据
     */
    public void addMoreData2(@NonNull List<T> newList) {
        if (this.list == null) {
            this.list = new ArrayList<>(newList);
            notifyDataSetChanged();
        } else {
            this.list.addAll(newList);
            notifyItemRangeInserted(this.lastListSize , newList.size());
        }
        recordListSize(newList);
    }

    private void recordListSize(@NonNull List<T> list) {
        this.lastListSize = this.list == null ? 0 : list.size();
    }

    private void recordListSize(int size) {
        this.lastListSize = size;
    }
}
