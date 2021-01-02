package com.crystal.aplayer.all_module.notification.push

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystal.aplayer.databinding.ModuleHomeFragmentDiscoverBinding
import com.crystal.module_base.base.http.retrofit.ResponseMes
import com.crystal.module_base.base.ui.fragments.LoadingRefreshFragment
import com.crystal.module_base.common.http.bean2.PushMessage
import com.crystal.module_base.tools.LogUtil


class PushFragment : LoadingRefreshFragment<PushViewModel>() {
    
    private var adapter: PushMesAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var binding: ModuleHomeFragmentDiscoverBinding? = null

    override fun onResume() {
        super.onResume()
        if (viewModel.firstLoad) {
            viewModel.firstLoad = false
            viewModel.firstLoadData()
        }
    }

    override fun setViewModel(): PushViewModel? {
        return ViewModelProvider(this).get(PushViewModel::class.java)
    }

    override fun setContentLayout(): View? {
        binding = ModuleHomeFragmentDiscoverBinding.inflate(layoutInflater)
        return binding?.root
    }

   override fun observeLiveData(viewModel: PushViewModel) {
//监听网络数据
        viewModel.dataLists.observe(viewLifecycleOwner, Observer(this@PushFragment::initAdapter))
        viewModel.nowResponseMes.observe(viewLifecycleOwner, Observer { responseMes: ResponseMes ->
            // TODO: 2020/11/16 有错误的话展示错误消息，没有错误的话，不进行处理
            if (responseMes.hasError()) showToast("获取数据失败")
            reSetPageState(false, viewModel.stateModel.nowBehavior) //重置所有视图和状态
        })
    }

    private fun initAdapter(list: List<PushMessage.Message>) {
        if (adapter == null) {
            adapter = PushMesAdapter(list, requireActivity())
            recyclerView = binding?.recyclerviewRoot
            recyclerView!!.layoutManager = LinearLayoutManager(context)
            recyclerView!!.adapter = adapter
            LogUtil.d(tag, "获取数据成功-initadapter")
        } else {
            //if (adapter!!.itemCount == list.size) return
            if (defaultRefreshLayout.state.isHeader) {
                adapter!!.removeOldData(list)
            } else {
                adapter!!.addMoreData(list)
            }
            LogUtil.d(tag, "获取数据成功-addmoredata")
        }
        reSetPageState(true, viewModel.stateModel.nowBehavior)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    companion object{
        const val  tag = "PushMesFragment"
    }

}
