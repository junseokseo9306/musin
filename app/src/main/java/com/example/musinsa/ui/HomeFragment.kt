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
import com.example.musinsa.adapters.ItemListViewPagerAdapter
import com.example.musinsa.adapters.CustomRecyclerViewAdapter
import com.example.musinsa.databinding.FragmentHomeBinding
import com.example.musinsa.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerAdapter: ItemListViewPagerAdapter
    private lateinit var gridAdapter: CustomRecyclerViewAdapter
    private lateinit var scrollAdapter: CustomRecyclerViewAdapter
    private val viewModel: HomeViewModel by viewModels()

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
        bannerAdapter = ItemListViewPagerAdapter()
        gridAdapter = CustomRecyclerViewAdapter()
        scrollAdapter = CustomRecyclerViewAdapter()

        val manager = GridLayoutManager(context, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (gridAdapter.getItemViewType(position)) {
                    CustomRecyclerViewAdapter.HEADER -> 3
                    CustomRecyclerViewAdapter.CONTENTS -> 1
                    CustomRecyclerViewAdapter.FOOTER -> 3
                    else -> 0
                }
            }
        }

        binding.rvGridGoodsArea.layoutManager = manager
        binding.vpBanner.adapter = bannerAdapter
        binding.rvGridGoodsArea.adapter = gridAdapter
        binding.rvScrollGoodsArea.adapter = scrollAdapter
        binding.rvScrollGoodsArea.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        observeData()
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
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}