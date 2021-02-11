package com.crystal.aplayer.module_base.common.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.crystal.aplayer.module_base.common.util.viewholder.ViewHolderHelper;
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/24 19:30
 * packageName：com.crystal.aplayer.module_base.common.util
 * 描述：
 */
public abstract class CommonAdapter<T> extends BaseAdapter3<T, BaseHolder2> {
    public CommonAdapter(List<T> list) {
        super(list);
    }
   /* @Override
    public int getItemViewType(int position) {
        T data = list.get(position);
        return AllViewHolder.parseViewHolderType(data.getType(), data.getData().getDataType(), data.getData().getType());
    }*/

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderHelper.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent);
    }
}
