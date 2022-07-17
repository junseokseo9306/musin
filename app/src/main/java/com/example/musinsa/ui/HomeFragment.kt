package com.example.musinsa.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musinsa.R
import com.example.musinsa.adapters.CustomRecyclerViewAdapter
import com.example.musinsa.common.CustomSpanCount
import com.example.musinsa.common.CustomViewPagerCallback
import com.example.musinsa.databinding.FragmentHomeBinding
import com.example.musinsa.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val bannerAdapter: CustomRecyclerViewAdapter by lazy {
        CustomRecyclerViewAdapter(
            launchBrowser = { url -> changeViewToChromeTab(url) },
            expandUiCount = { type, spanCount -> viewModel.expandUiItemList(type, spanCount) },
            changeRandomData = { type, spanCount -> viewModel.changeUiDataRandomly(type, spanCount) }
        )
    }

    private val gridAdapter: CustomRecyclerViewAdapter by lazy {
        CustomRecyclerViewAdapter(
            launchBrowser = { url -> changeViewToChromeTab(url) },
            expandUiCount = { type, spanCount -> viewModel.expandUiItemList(type, spanCount) },
            changeRandomData = { type, spanCount -> viewModel.changeUiDataRandomly(type, spanCount) },
            spanCount = GRID_COUNT
        )
    }

    private val scrollAdapter: CustomRecyclerViewAdapter by lazy {
        CustomRecyclerViewAdapter(
            launchBrowser = { url -> changeViewToChromeTab(url) },
            expandUiCount = { type, spanCount -> viewModel.expandUiItemList(type, spanCount) },
            changeRandomData = { type, spanCount -> viewModel.changeUiDataRandomly(type, spanCount) }
        )
    }

    private val styleAdapter: CustomRecyclerViewAdapter by lazy {
        CustomRecyclerViewAdapter(
            launchBrowser = { url -> changeViewToChromeTab(url) },
            expandUiCount = { type, spanCount -> viewModel.expandUiItemList(type, spanCount) },
            changeRandomData = { type, spanCount -> viewModel.changeUiDataRandomly(type, spanCount) },
            spanCount = STYLE_COUNT
        )
    }

    private val gridManager: GridLayoutManager by lazy {
        val manager = GridLayoutManager(context, GRID_COUNT)
        manager.spanSizeLookup = CustomSpanCount(
            getItemType = { index -> gridAdapter.getItemViewType(index) },
            spanCount = GRID_COUNT
        )
        manager
    }

    private val styleManager: GridLayoutManager by lazy {
        val manager = GridLayoutManager(context, STYLE_COUNT)
        manager.spanSizeLookup = CustomSpanCount(
            getItemType = { index -> styleAdapter.getItemViewType(index) },
            spanCount = STYLE_COUNT
        )
        manager
    }

    private val scrollManager: LinearLayoutManager by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private val customViewPagerCallback: CustomViewPagerCallback by lazy {
        CustomViewPagerCallback { index ->
            viewModel.changeBannerIndicator(index)
        }
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

        setBindingAdapters()
        observeData()
        changeBannerIndicator()
    }

    private fun setBindingAdapters() {
        with(binding) {
            vpBannerArea.adapter = bannerAdapter
            rvGridGoodsArea.adapter = gridAdapter
            rvScrollGoodsArea.adapter = scrollAdapter
            rvStyleArea.adapter = styleAdapter
            rvScrollGoodsArea.layoutManager = scrollManager
            rvGridGoodsArea.layoutManager = gridManager
            rvStyleArea.layoutManager = styleManager
        }
    }

    private fun observeData() {
        with(viewModel) {
            bannerItem.observe(viewLifecycleOwner) { item ->
                bannerAdapter.submitList(item)
            }
            gridGoodsItem.observe(viewLifecycleOwner) { item ->
                gridAdapter.submitList(item)
            }
            scrollGoodsItem.observe(viewLifecycleOwner) { item ->
                scrollAdapter.submitList(item)
            }
            scrollGoodsHeader.observe(viewLifecycleOwner) { item ->
                binding.header = item
                binding.scrollRvHeader.tvHeaderAll.setOnClickListener {
                    changeViewToChromeTab(item.linkURL)
                }
            }
            styleItem.observe(viewLifecycleOwner) { item ->
                styleAdapter.submitList(item)
            }
            indicator.observe(viewLifecycleOwner) { item ->
                binding.bannerIndicator = item
            }
        }
    }

    private fun changeBannerIndicator() {
        binding.vpBannerArea.registerOnPageChangeCallback(
            customViewPagerCallback
        )
    }

    private fun changeViewToChromeTab(url: String) {
        if (url.isEmpty()) {
            return
        }
        val context = this.context ?: return
        val uri = Uri.parse(url)

        CustomTabsIntent.Builder()
            .build()
            .launchUrl(context, uri)
    }

    companion object {
        const val GRID_COUNT = 3
        const val STYLE_COUNT = 2
        fun newInstance() = HomeFragment()
    }
}