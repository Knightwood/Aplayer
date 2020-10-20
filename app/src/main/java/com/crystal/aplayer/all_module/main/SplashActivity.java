package com.crystal.aplayer.all_module.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.crystal.aplayer.databinding.ModuleMainSplashActivityBinding;
import com.crystal.module_base.base.mvvm.contract.LoadDataState;
import com.crystal.module_base.base.mvvm.contract.LoadState;
import com.crystal.module_base.base.ui.activity.BaseActivity;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private ModuleMainSplashActivityBinding rootView;
    private SplashViewModel splashVM;
    //权限
    String[] allperm = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = ModuleMainSplashActivityBinding.inflate(getLayoutInflater());
        setContentView(rootView.getRoot());
        init();
    }

    private void init() {
        splashVM = new ViewModelProvider(this).get(SplashViewModel.class);
        splashVM.getStateModel().getLoadDataState().observe(this, loadState -> {
            switch (loadState) {
                case WAIT_LOAD_DATA:
                   anim();
                   checkPermission();
                    break;
                case LOAD_FINISHED:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
            }
        });
    }

    @Override
    protected void setStatusBarBackground(int color) {
        ImmersionBar.with(this).autoStatusBarDarkModeEnable(true,0.2f)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();
    }

    private void anim(){
        scaleAnim();
        alphaAnim();
    }

    private void scaleAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1F, 1.05F, 1F, 1.05F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(splashVM.animateDuration);
        rootView.ivSplashPicture.startAnimation(scaleAnimation);
    }

    private void alphaAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5F, 1.0F);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(splashVM.animateDuration);
        rootView.ivSlogan.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashVM.setStateModel(LoadDataState.LOAD_FINISHED);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void checkPermission() {
        if (!EasyPermissions.hasPermissions(this, allperm)) {
            EasyPermissions.requestPermissions(this, "没有这些权限可能无法正常运行", 20033, allperm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions进行处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getApplicationContext(), "用户授权失败", Toast.LENGTH_LONG).show();
        //(可选的)这里检查用户是否拒绝授权权限，以及点击了“不再询问”，这时，将展示一个对话框指导用户在应用设置里授权权限
        //如果没有点击不再询问，这里是不会被调用的，如果点击了不再询问，则展示对话框提示设置权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有这些权限，此应用程序可能无法正常工作。打开应用设置以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }/* else {
            //没有点击不再询问，则再一次申请权限
            EasyPermissions.requestPermissions(this, "这些权限是必须的", 20033, allperm);
        }*/
    }
}
