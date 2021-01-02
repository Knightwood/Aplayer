package com.crystal.aplayer.all_module.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.SparseArray;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.community.CommunityFragment;
import com.crystal.aplayer.all_module.util.KeyRes;
import com.crystal.aplayer.all_module.community.commend.CommendFragment;
import com.crystal.aplayer.all_module.home.HomeFragment;
import com.crystal.aplayer.all_module.mine.MineFragment;
import com.crystal.aplayer.all_module.notification.NotificationFragment;
import com.crystal.aplayer.databinding.ModuleMainActivityMainBinding;
import com.crystal.module_base.base.ui.activity.BaseActivity;
import com.crystal.module_base.common.ui.CommonActivity;
import com.crystal.module_base.tools.LogUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends CommonActivity {
    private static final String tag = "mainActivity";

    private ModuleMainActivityMainBinding mainBinding;
    private BottomNavigationView bottomNavigationView;

    private  SparseArray<Fragment> fragments;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ModuleMainActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        //存放fragment
        if (fragments == null) {
            fragments = new SparseArray<>();
        }
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        //底部导航
        bottomNavigationView = mainBinding.mainBottomNavigationView;
        bottomNavigationView.setItemIconTintList(null);//禁止自动变色
        setClickEvent(bottomNavigationView);
        addDefaultFragment();
        /*navController= Navigation.findNavController(ModuleMain_MainActivity.this,R.id.navs_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);*/
    }

    /**
     * @param bottomNavigationView 底部导航栏，设置点击事件
     */
    private void setClickEvent(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideAllFragment(transaction);
            switch (item.getItemId()) {
                case R.id.app_homefragment:
                    if (fragments.get(KeyRes.home_fragment) == null) {
                        addFragment(KeyRes.home_fragment, HomeFragment.newInstance(),transaction);
                    } else {
                        transaction.show(fragments.get(KeyRes.home_fragment));
                        LogUtil.d(tag, "替换fragment");
                    }
                    break;
                case R.id.app_communityfragment:
                    if (fragments.get(KeyRes.community_fragment) == null) {
                        addFragment(KeyRes.community_fragment, CommunityFragment.newInstance(),transaction);
                    } else {
                        transaction.show(fragments.get(KeyRes.community_fragment));
                    }
                    break;
                case R.id.app_messagefragment:
                    if (fragments.get(KeyRes.notification_fragment) == null) {
                        addFragment(KeyRes.notification_fragment, NotificationFragment.newInstance(),transaction);
                    } else {
                        transaction.show(fragments.get(KeyRes.notification_fragment));
                    }
                    break;
                case R.id.app_minefragment:
                    if (fragments.get(KeyRes.mine_fragment) == null) {
                        addFragment(KeyRes.mine_fragment, MineFragment.newInstance(),transaction);
                    } else {
                        transaction.show(fragments.get(KeyRes.mine_fragment));
                    }
                    break;
                case R.id.app_addbutton:
                    return false;
            }
            transaction.commit();
            LogUtil.d(tag, "被点击的底部菜单： " + item.getItemId());
            return true;
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getItemId() == R.id.app_addbutton) {

            }
            LogUtil.d(tag, "重复点击的底部选项： " + item.getItemId());
        });
    }

    private void addDefaultFragment() {
        addFragment(KeyRes.home_fragment, HomeFragment.newInstance(),fragmentManager.beginTransaction()).commit();
    }

    /**
     * 将<key,fragment>加入sparseArray，并添加到transaction。
     * @param key fragment对应的key
     * @param fragment 要添加到布局的fragment
     * @param transaction
     * @return 返回传入的transaction
     */
    private <F extends Fragment> FragmentTransaction addFragment(int key, F fragment, FragmentTransaction transaction) {
        fragments.put(key, fragment);
        transaction.add(R.id.fragment_container_view, fragment);
        return transaction;
    }

    private FragmentTransaction hideAllFragment(FragmentTransaction transaction) {
        if (fragments != null) {
            for (int i = 0; i < KeyRes.fragmentsKeys.length; i++) {
                Fragment f = fragments.get(KeyRes.fragmentsKeys[i]);
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }
        return transaction;
    }


}
