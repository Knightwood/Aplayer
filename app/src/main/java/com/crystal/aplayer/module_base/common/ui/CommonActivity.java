package com.crystal.aplayer.module_base.common.ui;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

import com.crystal.aplayer.R;
import com.crystal.aplayer.module_base.base.ui.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

/**
 * 创建者 kiylx
 * 创建时间 2020/12/21 20:14
 * packageName：com.crystal.aplayer.module_base.common.ui
 * 描述：
 */
public class CommonActivity extends BaseActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setContentViewAfter();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentViewAfter();
    }

    protected void setContentViewAfter() {
    }

    /**
     * 设置状态栏颜色
     */
    protected void changeStatesBarColor(@ColorRes int colorInt) {
        ImmersionBar.with(this)
                .autoStatusBarDarkModeEnable(true, 0.2F)
                .statusBarColor(colorInt)
                .fitsSystemWindows(true)
                .init();
    }
}
