package com.crystal.aplayer.all_module.home.main_page;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/18 16:08
 * packageName：com.crystal.module_home
 * 描述：
 */
public class HomeFragmnetAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public HomeFragmnetAdapter(@NonNull Fragment fragment, @NonNull List<Fragment> fragments) {
        super(fragment);
        this.fragmentList = fragments;
    }

    public HomeFragmnetAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position >= 0 && position < fragmentList.size()) {
               return fragmentList.get(position);
        }else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
