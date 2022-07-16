package com.example.musinsa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musinsa.model.Item
import com.example.musinsa.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _bannerItem = MutableLiveData<List<Item.ItemType>>()
    val bannerItem: LiveData<List<Item.ItemType>> = _bannerItem

    private val _gridGoodsItem = MutableLiveData<List<Item.ItemType>>()
    val gridGoodsItem: LiveData<List<Item.ItemType>> = _gridGoodsItem

    private val _scrollGoodsItem = MutableLiveData<List<Item.ItemType>>()
    val scrollGoodsItem: LiveData<List<Item.ItemType>> = _scrollGoodsItem

    private val _scrollGoodsHeader = MutableLiveData<Item.ItemType.Header>()
    val scrollGoodsHeader: LiveData<Item.ItemType.Header> = _scrollGoodsHeader

    private val _styleItem = MutableLiveData<List<Item.ItemType>>()
    val styleItem: LiveData<List<Item.ItemType>> = _styleItem

    init {
        loadData()
    }

    private fun loadData() {
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
        header: Item.ItemType.Header?,
        contents: Item.ItemType.Contents?,
        footer: Item.ItemType.Footer?,
    ) {
        val itemList = mutableListOf<Item.ItemType>()
        when (type) {
            Item.ItemType.Contents.TYPE_BANNER -> {
                contents?.banners?.forEach { banner ->
                    itemList.add(banner)
                }
                _bannerItem.value = itemList
            }
            Item.ItemType.Contents.TYPE_GOODS_GRID -> {
                header?.let { itemList.add(it) }
                contents?.goods?.forEach { goods ->
                    itemList.add(goods)
                }
                footer?.let { itemList.add(it) }
                _gridGoodsItem.value = itemList
            }
            Item.ItemType.Contents.TYPE_GOODS_SCROLL -> {
                contents?.goods?.forEach { goods ->
                    itemList.add(goods)
                }
                _scrollGoodsItem.value = itemList
                _scrollGoodsHeader.value = header
            }
            Item.ItemType.Contents.TYPE_STYLE -> {
                header?.let { itemList.add(it) }
                run loop@{
                    contents?.styles?.forEachIndexed { index, style ->
                        if (index == 8) {
                            return@loop
                        }
                        itemList.add(style)
                    }
                }
                footer?.let { itemList.add(it) }
                Log.d("ViewModel", "style size: ${itemList.size.toString()}")
                _styleItem.value = itemList
            }
        }
    }
}