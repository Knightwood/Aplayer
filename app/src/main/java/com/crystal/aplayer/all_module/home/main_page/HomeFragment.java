package com.crystal.aplayer.all_module.home.main_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.aplayer.all_module.home.daily.DailyFragment;
import com.crystal.aplayer.all_module.home.discover.DiscoverFragment;
import com.crystal.aplayer.all_module.home.recommend.RecommendFragment;
import com.crystal.aplayer.databinding.ModuleHomeFragmentHomeBinding;
import com.crystal.module_base.base.ui.fragments.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private ModuleHomeFragmentHomeBinding homeBinding;
    private HomeFragmnetAdapter homeFragmnetAdapter;
    TabLayout tablayout;
    ViewPager2 fragmentViewPager;
    private List<Fragment> fragmentList;

    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragmnetAdapter = new HomeFragmnetAdapter(this, createFragments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = ModuleHomeFragmentHomeBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablayout = homeBinding.moduleHomeTablayout;
        fragmentViewPager = homeBinding.moduleHomeFragmentViewpager;
        fragmentViewPager.setAdapter(homeFragmnetAdapter);
        fragmentViewPager.setCurrentItem(1);//默认选中第二个
        fragmentViewPager.setOffscreenPageLimit(1);
        linkedTabLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeBinding = null;
    }

    private List<Fragment> createFragments() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new DailyFragment());

        return fragmentList;
    }

    private void linkedTabLayout() {
        new TabLayoutMediator(tablayout, fragmentViewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("发现");
                    break;
                case 1:
                    tab.setText("推荐");
                    break;
                case 2:
                    tab.setText("日报");
                    break;
            }
        }).attach();

    }
}
