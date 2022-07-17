package com.example.musinsa.datasource

import com.example.musinsa.dto.ItemListDTO

interface DataSource {

    suspend fun getItemList(): ItemListDTO
}