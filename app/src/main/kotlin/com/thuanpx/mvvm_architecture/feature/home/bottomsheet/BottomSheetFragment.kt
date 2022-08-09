package com.thuanpx.mvvm_architecture.feature.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle
import com.thuanpx.ktext.recyclerView.initRecyclerViewAdapter
import com.thuanpx.mvvm_architecture.base.fragment.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetFragment :
    BaseFragment<BottomSheetViewModel, FragmentBottomSheetBinding>(BottomSheetViewModel::class) {

    private var bottomSheetAdapter: BottomSheetAdapter? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBottomSheetBinding {
        return FragmentBottomSheetBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        initRecyclerView()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        with(viewModel) {
            launchAndRepeatWithViewLifecycle {
                launch {
                    pagingItemFlow.collectLatest {
                        bottomSheetAdapter?.submitData(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        bottomSheetAdapter = BottomSheetAdapter()
        viewBinding.rvHome.initRecyclerViewAdapter(
            bottomSheetAdapter,
            GridLayoutManager(requireContext(), 1),
            true
        )
    }
}

