package com.example.musinsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.databinding.*
import com.example.musinsa.model.Item
import com.example.musinsa.ui.HomeFragment

class CustomRecyclerViewAdapter(
    private val expandUiCount: (String, Int) -> Unit,
    private val launchBrowser: (String) -> Unit,
) : ListAdapter<Item.ItemType, RecyclerView.ViewHolder>(ItemDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                ItemRecyclerHeaderBinding.inflate(inflater, parent, false)
            )
            CONTENTS_GOODS -> ItemGoodsViewHolder(
                ItemRecyclerGoodsBinding.inflate(inflater, parent, false)
            )
            CONTENTS_STYLE -> ItemStyleHolder(
                ItemRecyclerStyleBinding.inflate(inflater, parent, false)
            )
            CONTENTS_BANNER -> ItemBannerViewHolder(
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
            is ItemGoodsViewHolder -> holder.bind(item as Item.ItemType.Contents.Goods)
            is ItemStyleHolder -> holder.bind(item as Item.ItemType.Contents.Style)
            is ItemBannerViewHolder -> holder.bind(item as Item.ItemType.Contents.Banner)
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

    inner class ItemBannerViewHolder(
        private val binding: ItemViewpagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Banner) {
            binding.item = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class ItemGoodsViewHolder(
        private val binding: ItemRecyclerGoodsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Goods) {
            binding.item = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class ItemStyleHolder(
        private val binding: ItemRecyclerStyleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Contents.Style) {
            binding.style = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemRecyclerHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Header) {
            binding.header = item
        }
    }

    inner class FooterViewHolder(
        private val binding: ItemRecyclerFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item.ItemType.Footer) {
            val spanCount = if (item.contentType == Item.ItemType.Contents.TYPE_GOODS_GRID) {
                HomeFragment.GRID_COUNT
            } else {
                HomeFragment.STYLE_COUNT
            }
            if (item.type != TYPE_REFRESH) {
                binding.isRefresh = false
                binding.btnMore.setOnClickListener {
                    expandUiCount(item.contentType, spanCount)
                }
            }
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
        const val TYPE_REFRESH = "REFRESH"
    }
}