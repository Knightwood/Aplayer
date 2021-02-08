package com.crystal.module_base.common.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import androidx.annotation.Nullable;

import com.crystal.module_base.R;
import com.crystal.module_base.common.util.typefaces.TextKinds;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/24 19:28
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
public class NTextView extends  AppCompatTextView{

    public NTextView(@NonNull Context context) {
        this(context,null);
    }

    public NTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a =context.obtainStyledAttributes(attrs, R.styleable.NTextView);
        int TypeFaceCode=a.getInt(R.styleable.NTextView_typeface,0);
        setTypeface(TextKinds.getInstance().parseTypeface(TypeFaceCode));
        a.recycle();
    }

}
