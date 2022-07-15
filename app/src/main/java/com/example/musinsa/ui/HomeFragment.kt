package com.example.musinsa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musinsa.R
import com.example.musinsa.adapters.CustomRecyclerViewAdapter
import com.example.musinsa.databinding.FragmentHomeBinding
import com.example.musinsa.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerAdapter: CustomRecyclerViewAdapter
    private lateinit var gridAdapter: CustomRecyclerViewAdapter
    private lateinit var scrollAdapter: CustomRecyclerViewAdapter
    private lateinit var styleAdapter: CustomRecyclerViewAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val gridManager: GridLayoutManager by lazy {
        GridLayoutManager(context, GRID_COUNT)
    }
    private val styleManager: GridLayoutManager by lazy {
        GridLayoutManager(context, STYLE_COUNT)
    }
    private val scrollManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerAdapter = CustomRecyclerViewAdapter()
        gridAdapter = CustomRecyclerViewAdapter()
        scrollAdapter = CustomRecyclerViewAdapter()
        styleAdapter = CustomRecyclerViewAdapter()

        setBindingAdapters()
        observeData()
    }

    private fun setBindingAdapters() {
        with(binding) {
            gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (gridAdapter.getItemViewType(position)) {
                        CustomRecyclerViewAdapter.HEADER -> GRID_COUNT
                        CustomRecyclerViewAdapter.CONTENTS_GOODS -> ITEM_COUNT
                        CustomRecyclerViewAdapter.FOOTER -> GRID_COUNT
                        else -> NONE
                    }
                }
            }

            styleManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (styleAdapter.getItemViewType(position)) {
                        CustomRecyclerViewAdapter.HEADER -> STYLE_COUNT
                        CustomRecyclerViewAdapter.CONTENTS_STYLE -> ITEM_COUNT
                        CustomRecyclerViewAdapter.FOOTER -> STYLE_COUNT
                        else -> NONE
                    }
                }
            }

            vpBanner.adapter = bannerAdapter
            rvGridGoodsArea.adapter = gridAdapter
            rvGridGoodsArea.layoutManager = gridManager
            rvScrollGoodsArea.adapter = scrollAdapter
            rvScrollGoodsArea.layoutManager = scrollManager
            rvStyle.adapter = styleAdapter
            rvStyle.layoutManager = styleManager
        }
    }

    private fun observeData() {
        with(viewModel) {
            bannerItem.observe(viewLifecycleOwner) {
                bannerAdapter.submitList(it)
            }
            gridGoodsItem.observe(viewLifecycleOwner) {
                gridAdapter.submitList(it)
            }
            scrollGoodsItem.observe(viewLifecycleOwner) {
                scrollAdapter.submitList(it)
            }
            scrollGoodsHeader.observe(viewLifecycleOwner) {
                binding.header = it
            }
            scrollGoodsFooter.observe(viewLifecycleOwner) {
                binding.isRefresh = true
            }
            styleItem.observe(viewLifecycleOwner) {
                styleAdapter.submitList(it)
            }
        }
    }

    companion object {
        private const val GRID_COUNT = 3
        private const val STYLE_COUNT = 2
        private const val ITEM_COUNT = 1
        private const val NONE = 0
        fun newInstance() = HomeFragment()
    }
}