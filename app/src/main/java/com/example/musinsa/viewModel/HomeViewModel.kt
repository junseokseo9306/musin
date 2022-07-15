package com.example.musinsa.viewModel

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

    private val _scrollGoodsFooter = MutableLiveData<Item.ItemType.Footer>()
    val scrollGoodsFooter: LiveData<Item.ItemType.Footer> = _scrollGoodsFooter

    private val _styleItem = MutableLiveData<List<Item.ItemType>>()
    val styleItem: LiveData<List<Item.ItemType>> = _styleItem

    init {
        loadData()
    }

    //중복되는 부분 함수 분리 필요
    private fun loadData() {
        viewModelScope.launch {
            val data = repository.getItemList()
            data.forEach { item ->
                val itemList = mutableListOf<Item.ItemType>()
                when (item.type) {
                    Item.ItemType.Contents.TYPE_BANNER -> {
                        item.contents.banners?.forEach { banner ->
                            itemList.add(banner)
                        }
                        _bannerItem.value = itemList
                    }
                    Item.ItemType.Contents.TYPE_GOODS_GRID -> {
//                        val tempList = mutableListOf<Item.ItemType.Contents.Goods>()
//                        item.contents.goods?.forEach { goods ->
//                            tempList.add(goods)
//                        }
//                        _gridGoodsItem.value = tempList
                        item.header?.let { itemList.add(it) }
                        item.contents.goods?.forEach { goods ->
                            itemList.add(goods)
                        }
                        _gridGoodsItem.value = itemList
                        item.footer?.let { itemList.add(it) }
                    }
                    Item.ItemType.Contents.TYPE_GOODS_SCROLL -> {
                        item.contents.goods?.forEach { goods ->
                            itemList.add(goods)
                        }
                        _scrollGoodsItem.value = itemList
                        _scrollGoodsHeader.value = item.header
                        _scrollGoodsFooter.value = item.footer
                    }
                    Item.ItemType.Contents.TYPE_STYLE -> {
                        item.header?.let { itemList.add(it) }
                        item.contents.styles?.forEach { style ->
                            itemList.add(style)
                        }
                        _styleItem.value = itemList
                        item.footer?.let { itemList.add(it) }
                    }
                }
            }
        }
    }
}