package com.crystal.aplayer.all_module.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.aplayer.all_module.community.commend.CommendFragment;
import com.crystal.aplayer.all_module.community.follow.FollowFragment;
import com.crystal.aplayer.databinding.SimpleFragmentContinerBinding;
import com.crystal.aplayer.module_base.common.ui.BaseViewPagerFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends BaseViewPagerFragment {
    private SimpleFragmentContinerBinding communityBind;
    private List<Fragment> fragmentList;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        communityBind = SimpleFragmentContinerBinding.inflate(inflater, container, false);
        communityBind.moduleHomeIvNetCandle.setVisibility(View.INVISIBLE);
        return communityBind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        communityBind = null;
    }
    @Override
    protected List<Fragment> createFragments() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new CommendFragment());
        fragmentList.add(new FollowFragment());
        return fragmentList;
    }

    @Override
    protected TabLayout setTabLayout() {
        return communityBind.moduleHomeTablayout;
    }

    @Override
    protected ViewPager2 setViewPager2() {
        return communityBind.moduleHomeFragmentViewpager;
    }

    @Override
    protected void tabMediator(TabLayout.Tab tab, int position) {
        switch (position) {
            case 0:
                tab.setText("推荐");
                break;
            case 1:
                tab.setText("关注");
                break;
        }
    }

}
