package com.crystal.aplayer.all_module.home.discover;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.common.http.bean2.Discovery;
import com.crystal.aplayer.module_base.tools.SomeTools;
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.aplayer.module_base.tools.baseadapter2.BaseHolder2;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/21 14:19
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
public class ColumnCardListAdapter extends BaseAdapter3<Discovery.ItemX,BaseHolder2> {

    public ColumnCardListAdapter(List<Discovery.ItemX> list) {
        super(list);
    }
    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column_card_list_type_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder, int position) {
        Discovery.ItemX item = dataList.get(position);
        SomeTools.INSTANCES.loadImg(holder.getView(R.id.ivPicture), item.getData().getImage(), 4f, null);
        holder.setText(R.id.tvTitle, item.getData().getTitle());
        holder.itemView.setOnClickListener(v->{
            //"${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
        });
    }
}
