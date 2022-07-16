package com.example.musinsa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.musinsa.model.Item.ItemType

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _bannerItem = MutableLiveData<List<ItemType>>()
    val bannerItem: LiveData<List<ItemType>> = _bannerItem

    private val _gridGoodsItem = MutableLiveData<List<ItemType>>()
    private val _gridGoodsUiItemList = MutableLiveData<List<ItemType>>()
    val gridGoodsItem: LiveData<List<ItemType>> = _gridGoodsUiItemList

    private val _scrollGoodsItem = MutableLiveData<List<ItemType>>()
    val scrollGoodsItem: LiveData<List<ItemType>> = _scrollGoodsItem

    private val _scrollGoodsHeader = MutableLiveData<ItemType.Header>()
    val scrollGoodsHeader: LiveData<ItemType.Header> = _scrollGoodsHeader

    private val _styleItem = MutableLiveData<List<ItemType>>()
    private val _styleUiItemList = MutableLiveData<List<ItemType>>()
    val styleItem: LiveData<List<ItemType>> = _styleUiItemList

    init {
        loadDataFromServer()
    }

    private fun loadDataFromServer() {
        viewModelScope.launch {
            val data = repository.getItemList()
            data.forEach { item ->
                addDataToLiveData(
                    item.type,
                    item.header,
                    item.contents,
                    item.footer
                )
            }
        }
    }

    private fun addDataToLiveData(
        type: String,
        header: ItemType.Header,
        contents: ItemType.Contents,
        footer: ItemType.Footer,
    ) {
        when (type) {
            ItemType.Contents.TYPE_BANNER -> {
                _bannerItem.value = makeItemTypeList(
                    type,
                    header,
                    contents,
                    footer
                )
            }
            ItemType.Contents.TYPE_GOODS_GRID -> {
                val dataList = makeItemTypeList(
                    type,
                    header,
                    contents,
                    footer
                )
                val uiDataList = makeUIDataList(
                    type,
                    dataList
                )
                _gridGoodsItem.value = dataList
                _gridGoodsUiItemList.value = uiDataList
            }
            ItemType.Contents.TYPE_GOODS_SCROLL -> {
                _scrollGoodsItem.value = makeItemTypeList(
                    type,
                    header,
                    contents,
                    footer
                )
                _scrollGoodsHeader.value = header
            }
            ItemType.Contents.TYPE_STYLE -> {
                val dataList = makeItemTypeList(
                    type,
                    header,
                    contents,
                    footer
                )
                val uiDataList = makeUIDataList(
                    type,
                    dataList
                )
                _styleItem.value = dataList
                _styleUiItemList.value = uiDataList
            }
        }
    }

    private fun makeItemTypeList(
        type: String,
        header: ItemType.Header,
        contents: ItemType.Contents,
        footer: ItemType.Footer,
    ): List<ItemType> {
        val itemList = mutableListOf<ItemType>()
        if (contents.banners.isNotEmpty()) {
            contents.banners.forEach { banner ->
                itemList.add(banner)
            }
        }
        if (contents.goods.isNotEmpty()) {
            contents.goods.forEach { goods ->
                itemList.add(goods)
            }
        }
        if (contents.styles.isNotEmpty()) {
            contents.styles.forEach { style ->
                itemList.add(style)
            }
        }
        return when (type) {
            ItemType.Contents.TYPE_GOODS_SCROLL -> itemList
            ItemType.Contents.TYPE_BANNER -> itemList
            else -> {
                if (header != ItemType.Header.INITIAL_HEADER) {
                    itemList.add(0, header)
                }
                if (footer != ItemType.Footer.INITIAL_HEADER) {
                    itemList.add(footer)
                }
                itemList
            }
        }
    }

    private fun makeUIDataList(
        type: String,
        dataList: List<ItemType>,
    ): List<ItemType> {
        return when (type) {
            ItemType.Contents.TYPE_GOODS_GRID -> {
                val uiDataList = dataList.slice(INITIAL_COUNT..INITIAL_GRID_COUNT).toMutableList()
                val footer = dataList[dataList.size - 1]
                uiDataList.add(footer)
                uiDataList
            }
            else -> {
                val uiDataList = dataList.slice(INITIAL_COUNT..INITIAL_STYLE_COUNT).toMutableList()
                val footer = dataList[dataList.size - 1]
                uiDataList.add(footer)
                uiDataList
            }
        }
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val INITIAL_GRID_COUNT = 6
        private const val INITIAL_STYLE_COUNT = 4
    }
}