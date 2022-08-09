package com.thuanpx.mvvm_architecture.feature.viewpage

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thuanpx.mvvm_architecture.feature.home.HomeFragment
import com.thuanpx.mvvm_architecture.feature.home.bottomsheet.BottomSheetFragment


class PagerAdapter(@NonNull fragmentManager: FragmentManager?, @NonNull lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    @NonNull
    override fun createFragment(position: Int): Fragment = BottomSheetFragment()

    override fun getItemCount(): Int = 10
}