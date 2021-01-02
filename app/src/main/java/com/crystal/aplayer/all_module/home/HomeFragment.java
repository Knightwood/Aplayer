package com.crystal.aplayer.all_module.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.aplayer.all_module.home.daily.DailyFragment;
import com.crystal.aplayer.all_module.home.discover.DiscoverFragment;
import com.crystal.aplayer.all_module.home.recommend.RecommendFragment;
import com.crystal.aplayer.databinding.SimpleFragmentContinerBinding;
import com.crystal.module_base.common.ui.BaseViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseViewPagerFragment {
    private SimpleFragmentContinerBinding homeBinding;
    private List<Fragment> fragmentList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = SimpleFragmentContinerBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();
    }

    @Override
    protected void init() {
        super.init();
        viewPager.setCurrentItem(1);//默认选中第二个
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeBinding = null;
    }

    @Override
    protected List<Fragment> createFragments() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new DailyFragment());

        return fragmentList;
    }

    @Override
    protected TabLayout setTabLayout() {
        return homeBinding.moduleHomeTablayout;
    }

    @Override
    protected ViewPager2 setViewPager2() {
        return homeBinding.moduleHomeFragmentViewpager;
    }

    @Override
    protected void tabMediator(TabLayout.Tab tab, int position) {
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
    }

}
