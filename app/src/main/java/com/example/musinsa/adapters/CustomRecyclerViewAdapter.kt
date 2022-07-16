package com.example.musinsa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musinsa.databinding.*
import com.example.musinsa.model.Item.ItemType
import com.example.musinsa.ui.HomeFragment

class CustomRecyclerViewAdapter(
    private val expandUiCount: (String, Int) -> Unit,
    private val launchBrowser: (String) -> Unit,
) : ListAdapter<ItemType, RecyclerView.ViewHolder>(ItemDiffUtil) {

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
            is HeaderViewHolder -> holder.bind(item as ItemType.Header)
            is ItemGoodsViewHolder -> holder.bind(item as ItemType.Contents.Goods)
            is ItemStyleHolder -> holder.bind(item as ItemType.Contents.Style)
            is ItemBannerViewHolder -> holder.bind(item as ItemType.Contents.Banner)
            is FooterViewHolder -> holder.bind(item as ItemType.Footer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ItemType.Header -> HEADER
            is ItemType.Contents.Goods -> CONTENTS_GOODS
            is ItemType.Contents.Style -> CONTENTS_STYLE
            is ItemType.Contents.Banner -> CONTENTS_BANNER
            is ItemType.Footer -> FOOTER
        }
    }

    inner class ItemBannerViewHolder(
        private val binding: ItemViewpagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemType.Contents.Banner) {
            binding.item = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class ItemGoodsViewHolder(
        private val binding: ItemRecyclerGoodsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemType.Contents.Goods) {
            binding.item = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class ItemStyleHolder(
        private val binding: ItemRecyclerStyleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemType.Contents.Style) {
            binding.style = item
            itemView.setOnClickListener {
                launchBrowser(item.linkURL)
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemRecyclerHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemType.Header) {
            binding.header = item
        }
    }

    inner class FooterViewHolder(
        private val binding: ItemRecyclerFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemType.Footer) {
            val spanCount = if (item.contentType == ItemType.Contents.TYPE_GOODS_GRID) {
                HomeFragment.GRID_COUNT
            } else {
                HomeFragment.STYLE_COUNT
            }
            binding.isRefresh = item.type == ItemType.Footer.REFRESH
            binding.btnMore.setOnClickListener {
                expandUiCount(item.contentType, spanCount)
            }
        }
    }

    private object ItemDiffUtil : DiffUtil.ItemCallback<ItemType>() {

        override fun areItemsTheSame(
            oldItem: ItemType,
            newItem: ItemType,
        ) = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: ItemType,
            newItem: ItemType,
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