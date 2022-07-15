package com.example.musinsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.databinding.ItemRecyclerFooterBinding
import com.example.musinsa.databinding.ItemRecyclerGoodsBinding
import com.example.musinsa.databinding.ItemRecyclerHeaderBinding
import com.example.musinsa.model.Item
import java.lang.IllegalArgumentException

class CustomRecyclerViewAdapter : ListAdapter<Item.ItemType, RecyclerView.ViewHolder>(ItemDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                ItemRecyclerHeaderBinding.inflate(inflater, parent, false)
            )
            CONTENTS -> ItemViewHolder(
                ItemRecyclerGoodsBinding.inflate(inflater, parent, false)
            )
            FOOTER -> FooterViewHolder(
                ItemRecyclerFooterBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as Item.ItemType.Header)
            is ItemViewHolder -> holder.bind(item as Item.ItemType.Contents.Goods)
            is FooterViewHolder -> holder.bind(item as Item.ItemType.Footer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Item.ItemType.Header -> HEADER
            is Item.ItemType.Contents.Goods -> CONTENTS
            is Item.ItemType.Contents.Style -> CONTENTS
            is Item.ItemType.Contents.Banner -> CONTENTS
            is Item.ItemType.Footer -> FOOTER
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size < 5) currentList.size else currentList.size
    }

    class ItemViewHolder(
        private val binding: ItemRecyclerGoodsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Goods) {
            binding.item = item
        }
    }

    class HeaderViewHolder(
        private val binding: ItemRecyclerHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Header) {
            binding.header = item
        }
    }

    class FooterViewHolder(
        private val binding: ItemRecyclerFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Footer) {
            binding.isRefresh = true
        }
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

    companion object {
        const val HEADER = 0
        const val CONTENTS = 1
        const val FOOTER = 2
    }
}