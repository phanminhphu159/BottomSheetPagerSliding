package com.thuanpx.mvvm_architecture.feature.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle
import com.thuanpx.ktext.recyclerView.initRecyclerViewAdapter
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.base.fragment.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import com.thuanpx.mvvm_architecture.databinding.ItemBottomSheetBinding
import com.thuanpx.mvvm_architecture.feature.viewpage.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class) {

    private var homeAdapter: HomeAdapter? = null
    private var pagerAdapter: PagerAdapter? = null
    private var itemBottomSheetBinding: ItemBottomSheetBinding? = null
    private val tabTitles =
        arrayListOf(
            R.string.All,
            R.string.FoodAndDrinks,
            R.string.BeautyAndSpas,
            R.string.ThingsToDo,
            R.string.Services,
            R.string.Shopping,
            R.string.Education,
            R.string.PlacesToStay,
            R.string.Repairs,
            R.string.HomeAndGarden,
            R.string.Pets,
            R.string.Jobs,
            R.string.RealEstate,
            R.string.HealthCare
        )
    private val tabIcons =
        arrayListOf(
            R.drawable.ic_all,
            R.drawable.ic_food_and_drinks,
            R.drawable.ic_beauty_and_spas,
            R.drawable.ic_things_to_do,
            R.drawable.ic_services,
            R.drawable.ic_shoppping,
            R.drawable.ic_education,
            R.drawable.ic_places_to_stay,
            R.drawable.ic_repairs,
            R.drawable.ic_home_and_garden,
            R.drawable.ic_pets,
            R.drawable.ic_jobs,
            R.drawable.ic_real_estate,
            R.drawable.ic_health_care
        )

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
//        initRecyclerView()
        initBottomSheet()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        with(viewModel) {
            launchAndRepeatWithViewLifecycle {
                launch {
                    pagingPokemonFlow.collectLatest {
                        homeAdapter?.submitData(it)
                    }
                }
                launch {
                    homeAdapter?.loadStateFlow?.collectLatest { loadStates ->
                        if (loadStates.refresh is LoadState.Error) {
                            (activity as? BaseActivity<*, *>)?.handleApiError(
                                (loadStates.refresh as? LoadState.Error)?.error
                                    ?: return@collectLatest
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter()
        viewBinding.rvHome.initRecyclerViewAdapter(
            homeAdapter,
            GridLayoutManager(requireContext(), 2),
            true
        )
    }

    private fun initBottomSheet() {
        // BottomSheetBehavior setting
        BottomSheetBehavior.from(viewBinding.bottomSheetParent.root).peekHeight = 200
//        layout.setOnClickListener(object : OnClickListener() {
//            fun onClick(v: View?) {
//                if (i === 0) {
//                    layout.setVisibility(4)
//                    i = 1
//                } else {
//                    layout.setVisibility(0)
//                    i = 0
//                }
//            }
//        }
//                viewBinding.bottomSheetParent.setOnClickListener {
//            misShowPass = !misShowPass
//            showPass(misShowPass)
//        }

        // data
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        pagerAdapter = PagerAdapter(activity?.supportFragmentManager, lifecycle)
        viewBinding.bottomSheetParent.vp2Content.adapter = pagerAdapter
        with(viewBinding.bottomSheetParent.tlHeader) {
            for (i in 0 until 10) {
                addTab(viewBinding.bottomSheetParent.tlHeader.newTab())
                itemBottomSheetBinding = ItemBottomSheetBinding.inflate(inflater)
                if (i == 0) {
                    itemBottomSheetBinding!!.tvTitle.setTextColor(resources.getColor(R.color.C_007AFF))
                }
                itemBottomSheetBinding!!.tvTitle.text = resources.getString(tabTitles[i])
                itemBottomSheetBinding!!.ivImage.setImageResource(tabIcons[i])
                getTabAt(i)?.customView = itemBottomSheetBinding!!.llItemHeader
            }
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewBinding.bottomSheetParent.vp2Content.currentItem = tab.position
                    val tvTitle = tab.customView?.findViewById<View>(R.id.tvTitle) as TextView
                    tvTitle.setTextColor(resources.getColor(R.color.C_007AFF))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val tvTitle = tab?.customView?.findViewById<View>(R.id.tvTitle) as TextView
                    tvTitle.setTextColor(resources.getColor(R.color.C_BF000000))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}
