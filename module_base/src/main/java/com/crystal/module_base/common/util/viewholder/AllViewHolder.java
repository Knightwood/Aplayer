package com.crystal.module_base.common.util.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.crystal.module_base.tools.baseadapter2.BaseHolder2;


/**
 * 创建者 kiylx
 * 创建时间 2020/10/20 22:53
 * packageName：com.crystal.module_base.common.util
 * 描述：
 */
public class AllViewHolder {

    /**
     * 根据不同的viewtype返回不同的itemview的id
     *
     * @param typeInt viewholder的int型的值：type
     * @return
     */
    public static int getItemViewLayoutId(int typeInt) {
        ViewHolderTypes typeEnum = ViewHolderTypes.getTypeEnum(typeInt);
        return typeEnum.getLayoutId();
    }
    /**
     *
     * @param dataType
     * @param type
     * @return 根据datatype和type解析出正确的viewholder的int型的值：type
     */
    public static int parseViewHolderType(String type,String dataType ){
        return ViewHolderTypes.getTypeEnum(type,dataType).getTypeInt();
    }

    public static ViewHolderTypes getTypeEnum(int typeInt){
        return ViewHolderTypes.getTypeEnum(typeInt);
    }

    /**
     * @param typeInt 枚举类定义的每个viewholder不同的int型的值：type
     * @param inflater
     * @param parent
     * @param <T>
     * @return 返回baseholder2
     */
    public static <T extends RecyclerView.ViewHolder> T getViewHolder(int typeInt, LayoutInflater inflater, ViewGroup parent) {
        ViewHolderTypes types = ViewHolderTypes.getTypeEnum(typeInt);
        return (T) new BaseHolder2(inflater.inflate(types.getLayoutId(), parent, false),types);
    }

}