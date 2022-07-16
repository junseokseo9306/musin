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
import java.lang.StringBuilder

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _bannerItem = MutableLiveData<List<ItemType>>()
    val bannerItem: LiveData<List<ItemType>> = _bannerItem

    private var _gridGoodsItem = mutableListOf<ItemType>()
    private val _gridGoodsUiItemList = MutableLiveData<List<ItemType>>()
    val gridGoodsItem: LiveData<List<ItemType>> = _gridGoodsUiItemList

    private val _scrollGoodsItem = MutableLiveData<List<ItemType>>()
    val scrollGoodsItem: LiveData<List<ItemType>> = _scrollGoodsItem

    private val _scrollGoodsHeader = MutableLiveData<ItemType.Header>()
    val scrollGoodsHeader: LiveData<ItemType.Header> = _scrollGoodsHeader

    private var _styleItem = mutableListOf<ItemType>()
    private val _styleUiItemList = MutableLiveData<List<ItemType>>()
    val styleItem: LiveData<List<ItemType>> = _styleUiItemList

    private val _indicator = MutableLiveData<String>()
    val indicator: LiveData<String> = _indicator

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
                _gridGoodsItem = dataList.toMutableList()
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
                _styleItem = dataList.toMutableList()
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

    fun expandUiData(
        type: String,
        spanCount: Int,
    ) {
        val tempList = mutableListOf<ItemType>()
        when (type) {
            ItemType.Contents.TYPE_GOODS_GRID -> {
                if (_gridGoodsItem.size == _gridGoodsUiItemList.value?.size) {
                    return
                }
                val startIndex = _gridGoodsUiItemList.value?.size ?: INITIAL_COUNT
                val lastIndex = _gridGoodsItem.size
                val footer = _gridGoodsItem[lastIndex - 1]

                val stopIndex = if (startIndex + spanCount >= lastIndex) {
                    lastIndex
                } else {
                    startIndex + spanCount
                }

                for (index in 0 until stopIndex - 1) {
                    tempList.add(_gridGoodsItem[index])
                }
                tempList.add(footer)
                _gridGoodsUiItemList.value = tempList
            }
            ItemType.Contents.TYPE_STYLE -> {
                if (_styleItem.size == _styleUiItemList.value?.size) {
                    return
                }
                val startIndex = _styleUiItemList.value?.size ?: INITIAL_COUNT
                val lastIndex = _styleItem.size
                val footer = _styleItem[lastIndex - 1]

                val stopIndex = if (startIndex + spanCount >= lastIndex) {
                    lastIndex
                } else {
                    startIndex + spanCount
                }

                for (index in 0 until stopIndex - 1) {
                    tempList.add(_styleItem[index])
                }
                tempList.add(footer)
                _styleUiItemList.value = tempList
            }
        }
    }

    fun changeIndicator(
        index: Int,
    ) {
        val separator = '/'
        val space = ' '
        val count = index + 1
        val total = _bannerItem.value?.size ?: INITIAL_BANNER_COUNT
        val indicator = buildString {
            append(space)
            append(count)
            append(space)
            append(separator)
            append(space)
            append(total)
            append(space)
        }
        _indicator.value = indicator
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val INITIAL_GRID_COUNT = 6
        private const val INITIAL_STYLE_COUNT = 4
        private const val INITIAL_BANNER_COUNT = 10
    }
}