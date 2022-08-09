package com.thuanpx.mvvm_architecture.feature.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thuanpx.ktext.recyclerView.initRecyclerViewAdapter
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.databinding.ItemRvBottomSheetBinding
import com.thuanpx.mvvm_architecture.feature.home.bottomsheet.BottomSheetAdapter.BottomSheetViewHolder
import com.thuanpx.mvvm_architecture.model.entity.Item
import com.thuanpx.mvvm_architecture.model.entity.ItemDiffCallback


class BottomSheetAdapter : PagingDataAdapter<Item, BottomSheetViewHolder>(ItemDiffCallback) {

    class BottomSheetViewHolder(
        private val viewBinding: ItemRvBottomSheetBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun onBindData(item: Item?) {
            with(viewBinding) {
                val mListPost = ArrayList<Int>()
                for (i in 0 until 10) {
                    mListPost.add(R.drawable.img_user)
                }
                tvBusinessName.text = item?.name
                initImageRecyclerView(mListPost,viewBinding)
            }
        }

        private fun initImageRecyclerView(intList : ArrayList<Int>, viewBinding : ItemRvBottomSheetBinding) {
            val bottomSheetItemImageAdapter = BottomSheetItemImageAdapter(intList)
            viewBinding.rvBottomSheet.initRecyclerViewAdapter(
                bottomSheetItemImageAdapter,
                LinearLayoutManager.HORIZONTAL,
                true
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        return BottomSheetViewHolder(
            ItemRvBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.onBindData(getItem(position))
    }
}
