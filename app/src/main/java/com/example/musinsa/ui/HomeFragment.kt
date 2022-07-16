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
import com.example.musinsa.common.CustomSpanCount
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
        val manager = GridLayoutManager(context, GRID_COUNT)
        manager.spanSizeLookup = CustomSpanCount(
            getItemType = { Int -> gridAdapter.getItemViewType(Int) },
            spanCount = GRID_COUNT
        )
        manager
    }

    private val styleManager: GridLayoutManager by lazy {
        val manager = GridLayoutManager(context, STYLE_COUNT)
        manager.spanSizeLookup = CustomSpanCount(
            getItemType = { Int -> styleAdapter.getItemViewType(Int) },
            spanCount = STYLE_COUNT)
        manager
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
            vpBanner.adapter = bannerAdapter
            rvGridGoodsArea.adapter = gridAdapter
            rvScrollGoodsArea.adapter = scrollAdapter
            rvStyle.adapter = styleAdapter
            rvScrollGoodsArea.layoutManager = scrollManager
            rvGridGoodsArea.layoutManager = gridManager
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
            styleItem.observe(viewLifecycleOwner) {
                styleAdapter.submitList(it)
            }
        }
    }

    companion object {
        private const val GRID_COUNT = 3
        private const val STYLE_COUNT = 2
        fun newInstance() = HomeFragment()
    }
}