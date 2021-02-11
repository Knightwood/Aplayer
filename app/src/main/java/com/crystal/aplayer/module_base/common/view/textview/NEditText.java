package com.crystal.aplayer.module_base.common.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.common.util.typefaces.TextKinds;

public class NEditText extends AppCompatTextView {

    public NEditText(@NonNull Context context) {
        this(context,null);
    }

    public NEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a =context.obtainStyledAttributes(attrs, R.styleable.NEditText);
        int TypeFaceCode=a.getInt(R.styleable.NTextView_typeface,0);
        setTypeface(TextKinds.getInstance().parseTypeface(TypeFaceCode));
        a.recycle();
    }

}