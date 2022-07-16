package com.example.musinsa.common

import androidx.recyclerview.widget.GridLayoutManager
import com.example.musinsa.adapters.CustomRecyclerViewAdapter

class CustomSpanCount(
    private val getItemType: (Int) -> Int,
    private val spanCount: Int,
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (getItemType(position)) {
            CustomRecyclerViewAdapter.HEADER -> spanCount
            CustomRecyclerViewAdapter.CONTENTS_GOODS -> ITEM_COUNT
            CustomRecyclerViewAdapter.CONTENTS_STYLE -> ITEM_COUNT
            CustomRecyclerViewAdapter.FOOTER -> spanCount
            else -> NONE
        }
    }

    companion object {
        private const val ITEM_COUNT = 1
        private const val NONE = 0
    }
}