package com.example.musinsa.network

import com.example.musinsa.dto.ItemListDTO
import retrofit2.http.GET

interface ApiClient {

    @GET("interview/list.json")
    suspend fun getItemList(): ItemListDTO
}