package com.crystal.aplayer.all_module.home.daily;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.util.CommonActionUrlUtil;
import com.crystal.module_base.tools.baseadapter2.BaseAdapter3;
import com.crystal.module_base.tools.baseadapter2.BaseHolder2;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.scwang.smart.refresh.layout.util.SmartUtil.dp2px;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/24 19:43
 * packageName：com.crystal.aplayer.all_module.home.daily
 * 描述：
 */
public class InformationCardFollowCardAdapter extends BaseAdapter3<String, BaseHolder2> {
    private WeakReference<Context> context;
    private String actionUrl;
    public InformationCardFollowCardAdapter(Context context, String actionUrl, List<String> titleList) {
        super(titleList);
        this.actionUrl=actionUrl;
        this.context = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public BaseHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_card_type_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder2 holder, int position) {
        String item = dataList.get(position) ;
        holder.setText(R.id.tvNews,item);
        holder.itemView.setOnClickListener(v->{
            CommonActionUrlUtil.process(context.get(), actionUrl,null);
           }) ;
    }
   public static class InformationCardFollowCardItemDecoration extends RecyclerView.ItemDecoration {


       @Override
       public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
           super.getItemOffsets(outRect, view, parent, state);
           int position = parent.getChildAdapterPosition(view); // item position
           RecyclerView.Adapter adapter = parent.getAdapter();
           int count = adapter == null ? 0 : adapter.getItemCount(); //item count
           if (position==0){
               outRect.top = dp2px(18f);
           }else if (position==count-1){
               outRect.top = dp2px(13f);
               outRect.bottom = dp2px(18f);
           }else {
               outRect.top = dp2px(13f);
           }
       }
    }

}
