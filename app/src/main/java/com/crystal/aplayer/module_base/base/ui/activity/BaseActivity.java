package com.crystal.aplayer.module_base.base.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.crystal.aplayer.R;
import com.gyf.immersionbar.ImmersionBar;


/**
 * 创建者 kiylx
 * 创建时间 2020/9/19 23:06
 * packageName：com.crystal.aplayer.base.activity
 * 描述：
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBarBackground(R.color.colorPrimaryDark);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBarBackground(R.color.colorPrimaryDark);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setStatusBarBackground(R.color.colorPrimaryDark);
    }

    /**
     * 设置状态栏背景色
     */
   protected void setStatusBarBackground(@ColorRes int color){
       ImmersionBar.with(this).autoStatusBarDarkModeEnable(true,0.2f)
       .statusBarColor(color)
       .fitsSystemWindows(true)
       .init();
   }

}
