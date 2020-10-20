package com.crystal.module_base.common.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/20 13:07
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
public class JustTextFooter extends SimpleComponent implements RefreshFooter {
    private static final String tag="JustTextFooter";
    public static String REFRESH_FOOTER_NOTHING = null;

    public JustTextFooter(Context context) {
        this(context,null);
    }
    public JustTextFooter(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }
    public JustTextFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public JustTextFooter setAccentColorId(int colorTextPrimary) {
        return this;
    }

    public RefreshFooter setTextTitleSize(float v) {
        return null;
    }
}
