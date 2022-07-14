package com.example.musinsa.datasource

import com.example.musinsa.network.ApiClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceImpl @Inject constructor(
    private val apiClient: ApiClient,
) : DataSource {
    override suspend fun getItemList() = apiClient.getItemList()
}