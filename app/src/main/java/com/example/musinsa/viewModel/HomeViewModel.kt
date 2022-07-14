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

    private val _bannerItem = MutableLiveData<Item>()
    val bannerItem: LiveData<Item> = _bannerItem

    private val _gridGoodsItem = MutableLiveData<Item>()
    val gridGoodsItem: LiveData<Item> = _gridGoodsItem

    private val _scrollGoodsItem = MutableLiveData<Item>()
    val scrollGoodsItem: LiveData<Item> = _scrollGoodsItem

    private val _styleItem = MutableLiveData<Item>()
    val styleItem: LiveData<Item> = _styleItem

    fun loadData() {
        viewModelScope.launch {
            val list = repository.getItemList()
            list.forEach { item ->
                when (item.type) {
                    Item.ItemType.Contents.TYPE_BANNER -> {
                        _bannerItem.value = item
                        Log.d("ViewModel", item.contents.banners?.size.toString())
                    }
                    Item.ItemType.Contents.TYPE_GOODS_GRID -> {
                        _gridGoodsItem.value = item
                        Log.d("ViewModel", item.contents.goods?.size.toString())
                    }
                    Item.ItemType.Contents.TYPE_GOODS_SCROLL -> {
                        _scrollGoodsItem.value = item
                    }
                    Item.ItemType.Contents.TYPE_STYLE -> {
                        _styleItem.value = item
                    }
                }
            }
        }
    }
}