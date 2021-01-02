package com.crystal.aplayer.all_module.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.crystal.aplayer.all_module.util.inVisible
import com.crystal.aplayer.all_module.notification.inbox.InboxFragment
import com.crystal.aplayer.all_module.notification.interact.InteractionFragment
import com.crystal.aplayer.all_module.notification.push.PushFragment
import com.crystal.aplayer.databinding.SimpleFragmentContinerBinding
import com.crystal.module_base.common.ui.BaseViewPagerFragment
import com.google.android.material.tabs.TabLayout
import kotlin.collections.ArrayList


class NotificationFragment : BaseViewPagerFragment() {
    private var homeBinding: SimpleFragmentContinerBinding? = null
    private val binding get() = homeBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeBinding = SimpleFragmentContinerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moduleHomeIvNetCandle.inVisible()
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun createFragments(): List<Fragment> {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(PushFragment())
        fragmentList.add(InteractionFragment())
        fragmentList.add(InboxFragment())
        return fragmentList
    }

    override fun setTabLayout(): TabLayout {
        return binding.moduleHomeTablayout
    }

    override fun setViewPager2(): ViewPager2 {
        return binding.moduleHomeFragmentViewpager
    }

    override fun tabMediator(tab: TabLayout.Tab?, position: Int) {
        when (position) {
            0 -> tab?.text = "推送"
            1 -> tab?.text = "互动"
            2 -> tab?.text = "私信"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }

        const val tag: String = "通知fragment"
    }
}