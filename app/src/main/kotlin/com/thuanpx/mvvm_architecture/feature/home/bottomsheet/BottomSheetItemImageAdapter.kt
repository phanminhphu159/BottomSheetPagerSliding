package com.thuanpx.mvvm_architecture.feature.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thuanpx.mvvm_architecture.databinding.ItemRvImageListBottomSheetBinding
import com.thuanpx.mvvm_architecture.feature.home.bottomsheet.BottomSheetItemImageAdapter.BottomSheetItemImageViewHolder


class BottomSheetItemImageAdapter(private val intList: ArrayList<Int>) :
    RecyclerView.Adapter<BottomSheetItemImageViewHolder>() {
    inner class BottomSheetItemImageViewHolder(private val itemRvImageListBottomSheetBinding: ItemRvImageListBottomSheetBinding) :
        RecyclerView.ViewHolder(itemRvImageListBottomSheetBinding.root) {
        fun bindItem(url: Int) {
            itemRvImageListBottomSheetBinding.ivBusinessImage.setImageResource(url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetItemImageViewHolder {
        return BottomSheetItemImageViewHolder(
            ItemRvImageListBottomSheetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BottomSheetItemImageViewHolder, position: Int) {
        val int = intList[position]
        holder.bindItem(int)
    }

    override fun getItemCount(): Int {
        return intList.size
    }
}