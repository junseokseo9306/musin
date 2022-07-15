package com.example.musinsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.databinding.*
import com.example.musinsa.model.Item
import java.lang.IllegalArgumentException

class CustomRecyclerViewAdapter :
    ListAdapter<Item.ItemType, RecyclerView.ViewHolder>(ItemDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                ItemRecyclerHeaderBinding.inflate(inflater, parent, false)
            )
            CONTENTS_GOODS -> ItemViewHolder(
                ItemRecyclerGoodsBinding.inflate(inflater, parent, false)
            )
            CONTENTS_STYLE -> ItemStyleHolder(
                ItemRecyclerStyleBinding.inflate(inflater, parent, false)
            )
            CONTENTS_BANNER -> BannerViewHolder(
                ItemViewpagerBinding.inflate(inflater, parent, false)
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
            is ItemStyleHolder -> holder.bind(item as Item.ItemType.Contents.Style)
            is BannerViewHolder -> holder.bind(item as Item.ItemType.Contents.Banner)
            is FooterViewHolder -> holder.bind(item as Item.ItemType.Footer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Item.ItemType.Header -> HEADER
            is Item.ItemType.Contents.Goods -> CONTENTS_GOODS
            is Item.ItemType.Contents.Style -> CONTENTS_STYLE
            is Item.ItemType.Contents.Banner -> CONTENTS_BANNER
            is Item.ItemType.Footer -> FOOTER
        }
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

    class ItemStyleHolder(
        private val binding: ItemRecyclerStyleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Style) {
            binding.style = item
        }
    }

    class BannerViewHolder(
        private val binding: ItemViewpagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Banner) {
            binding.item = item
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
        const val CONTENTS_GOODS = 1
        const val CONTENTS_STYLE = 2
        const val CONTENTS_BANNER = 3
        const val FOOTER = 4
    }
}