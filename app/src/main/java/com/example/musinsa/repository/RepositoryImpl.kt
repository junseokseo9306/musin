package com.example.musinsa.repository

import com.example.musinsa.datasource.DataSource
import com.example.musinsa.dto.toItemList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
) : Repository {

    override suspend fun getItemList() = dataSource.getItemList().toItemList()
}