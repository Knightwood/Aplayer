package com.crystal.aplayer.all_module.home.discover;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.common.http.bean2.Discovery;
import com.crystal.aplayer.module_base.common.http.bean2.Label;
import com.crystal.aplayer.module_base.tools.SomeTools;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * 创建者 kiylx
 * 创建时间 2020/11/24 14:40
 * packageName：com.crystal.aplayer.all_module.home.discover
 * 描述：
 */
public class HorizontalScrollCardAdapter extends BaseBannerAdapter<Discovery.ItemX, HorizontalScrollCardAdapter.HorizonViewHolder> {

    @Override
    protected void onBind(HorizonViewHolder holder, Discovery.ItemX data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public HorizonViewHolder createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
        return new HorizonViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner_item_type;
    }

    public static class HorizonViewHolder extends BaseViewHolder<Discovery.ItemX> {

        public HorizonViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Discovery.ItemX item, int position, int pageSize) {
            ImageView ivPicture = findView(R.id.ivPicture);
            TextView tvLabel = findView(R.id.tvLabel);
            Label label = item.getData().getLabel();
            if (label == null || label.getText() == null || label.getText().isEmpty()) {
                tvLabel.setVisibility(View.INVISIBLE);
                tvLabel.setText("");
            } else {
                tvLabel.setVisibility(View.VISIBLE);
                tvLabel.setText(label.getText());
            }
            SomeTools.INSTANCES.loadImg(ivPicture, item.getData().getImage(), null);
        }
    }
}
