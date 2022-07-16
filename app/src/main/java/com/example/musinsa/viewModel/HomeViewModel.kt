package com.example.musinsa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.model.Item.ItemType
import com.example.musinsa.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        val itemList = makeItemTypeList(
            type,
            header,
            contents,
            footer
        )
        val uiDataList = makeUiItemList(
            type,
            itemList
        )
        when (type) {
            ItemType.Contents.TYPE_BANNER -> {
                _bannerItem.value = itemList
            }
            ItemType.Contents.TYPE_GOODS_GRID -> {
                _gridGoodsItem = itemList.toMutableList()
                _gridGoodsUiItemList.value = uiDataList
            }
            ItemType.Contents.TYPE_GOODS_SCROLL -> {
                _scrollGoodsItem.value = itemList
                _scrollGoodsHeader.value = header
            }
            ItemType.Contents.TYPE_STYLE -> {
                _styleItem = itemList.toMutableList()
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

    private fun makeUiItemList(
        type: String,
        dataList: List<ItemType>,
    ): List<ItemType> {
        val uiDataList = when (type) {
            ItemType.Contents.TYPE_GOODS_GRID -> {
                dataList.slice(INITIAL_COUNT..INITIAL_GRID_COUNT).toMutableList()
            }
            ItemType.Contents.TYPE_STYLE -> {
                dataList.slice(INITIAL_COUNT..INITIAL_STYLE_COUNT).toMutableList()
            }
            else -> return dataList
        }
        val footer = dataList[dataList.size - 1]
        uiDataList.add(footer)
        return uiDataList
    }

    fun expandUiItemList(
        type: String,
        spanCount: Int,
    ) {
        when (type) {
            ItemType.Contents.TYPE_GOODS_GRID -> {
                makeExpandItemList(
                    _gridGoodsItem,
                    _gridGoodsUiItemList,
                    spanCount
                )
            }
            ItemType.Contents.TYPE_STYLE -> {
                makeExpandItemList(
                    _styleItem,
                    _styleUiItemList,
                    spanCount
                )
            }
        }
    }

    private fun makeExpandItemList(
        dataList: MutableList<ItemType>,
        uiDataList: MutableLiveData<List<ItemType>>,
        spanCount: Int,
    ) {
        if (dataList.size == uiDataList.value?.size) return
        val tempList = mutableListOf<ItemType>()
        val startIndex = uiDataList.value?.size ?: INITIAL_COUNT
        val lastIndex = dataList.size
        val footer = dataList[lastIndex - 1]
        val stopIndex = if (startIndex + spanCount >= lastIndex) {
            lastIndex
        } else {
            startIndex + spanCount
        }

        for (index in 0 until stopIndex - 1) {
            tempList.add(dataList[index])
        }
        tempList.add(footer)
        uiDataList.value = tempList
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

    fun changeRandomData(
        type: String,
        spanCount: Int,
    ) {
        val pickNextCount = spanCount * 2
        when (type) {
            ItemType.Contents.TYPE_GOODS_GRID -> {
                makeRandomList(
                    _gridGoodsItem,
                    _gridGoodsUiItemList,
                    pickNextCount
                )
            }
            ItemType.Contents.TYPE_STYLE -> {
                makeRandomList(
                    _styleItem,
                    _styleUiItemList,
                    pickNextCount
                )
            }
        }
    }

    private fun makeRandomList(
        dataList: List<ItemType>,
        uiDataList: MutableLiveData<List<ItemType>>,
        pickNextCount: Int,
    ) {
        val uiList = uiDataList.value ?: return
        val itemList = mutableListOf<ItemType>()
        val newItemList = mutableListOf<ItemType>()
        itemList.addAll(dataList)
        val header = itemList[0]
        val footer = itemList[itemList.size - 1]
        itemList.removeAt(0)
        itemList.removeAt(itemList.size - 1)
        val lastItem = uiList[uiList.size - 2]
        val lastIndex = dataList.indexOf(lastItem)

        newItemList.add(header)
        for (index in lastIndex until lastIndex + pickNextCount) {
            val newIndex = (index % itemList.size)
            newItemList.add(itemList[newIndex])
        }
        newItemList.add(footer)
        uiDataList.value = newItemList
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val INITIAL_GRID_COUNT = 6
        private const val INITIAL_STYLE_COUNT = 4
        private const val INITIAL_BANNER_COUNT = 10
    }
}