package com.example.musinsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.databinding.ItemViewpagerBinding
import com.example.musinsa.model.Item

class ItemListViewPagerAdapter() : ListAdapter<Item.ItemType, RecyclerView.ViewHolder>(ItemDiffUtil) {

    class ViewPagerViewHolder(
        private val binding: ItemViewpagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Banner) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewPagerViewHolder(
            ItemViewpagerBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        (holder as ViewPagerViewHolder).bind(item as Item.ItemType.Contents.Banner)
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<Item.ItemType>() {

        override fun areItemsTheSame(
            oldItem: Item.ItemType,
            newItem: Item.ItemType,
        ) = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: Item.ItemType,
            newItem: Item.ItemType,
        ) = oldItem == newItem
    }
}