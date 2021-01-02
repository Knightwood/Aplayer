package com.crystal.module_base.common.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/28 23:47
 * packageName：com.crystal.module_base.common.ui
 * 描述：
 */
public abstract class BaseViewPagerFragment extends Fragment {
    protected int offScreenPageLimit = 1;
    protected TabLayout tabLayout;
    protected ViewPager2 viewPager;
    protected FragmentAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected void init() {
        viewPager = setViewPager2();
        tabLayout = setTabLayout();
        if (adapter == null) {
            adapter = new FragmentAdapter(this, createFragments());
        }
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(offScreenPageLimit);
            viewPager.setAdapter(adapter);
        }
        if (tabLayout != null&&viewPager != null) {
            new TabLayoutMediator(tabLayout, viewPager, this::tabMediator).attach();
        }
    }

    protected abstract List<Fragment> createFragments();

    protected abstract TabLayout setTabLayout();

    protected abstract ViewPager2 setViewPager2();

    protected abstract void tabMediator(TabLayout.Tab tab, int position);

    public static class FragmentAdapter extends FragmentStateAdapter {
        private List<Fragment> fragmentList;

        public FragmentAdapter(@NonNull Fragment fragment, @NonNull List<Fragment> fragments) {
            super(fragment);
            this.fragmentList = fragments;
        }

        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position >= 0 && position < fragmentList.size()) {
                return fragmentList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

    }
}
