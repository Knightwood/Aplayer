package com.crystal.module_base.common.view.textview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.crystal.module_base.R
import com.crystal.module_base.common.util.typefaces.TextKinds
import com.crystal.module_base.tools.textview.ExpandTextView
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


class CollapseTextView : ExpandTextView {
   constructor(@NotNull context: Context) :this(context, null)

    constructor(@NotNull context: Context, @Nullable attrs: AttributeSet?) :this(context, attrs, 0)

    constructor(@NotNull context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {

        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CollapseTextView)
        setTypeface(TextKinds.getInstance().parseTypeface(a.getInt(R.styleable.NTextView_typeface, 0)))
        a.recycle()
    }

}