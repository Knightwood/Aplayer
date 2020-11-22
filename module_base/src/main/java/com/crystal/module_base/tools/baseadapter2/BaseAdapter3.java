package com.crystal.module_base.tools.baseadapter2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/22 22:16
 * packageName：com.crystal.module_base.tools.baseadapter2
 * 描述：
 */
public abstract class BaseAdapter3<T,H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    protected List<T> list = null;//T类型的bean的list集合

    public BaseAdapter3(List<T> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }
   public void removeOldData(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

  public void addMoreData(List<T> newList) {
        if (this.list == null) {
            this.list = new ArrayList<>(newList);
            notifyDataSetChanged();
            return;
        }
        int pos = this.list.size() - 1;
        this.list.addAll(newList);
        notifyItemRangeInserted(pos, newList.size());
    }
}
