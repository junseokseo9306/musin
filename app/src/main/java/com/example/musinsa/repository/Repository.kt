package com.example.musinsa.repository

import com.example.musinsa.model.Item

interface Repository {

    suspend fun getItemList(): List<Item>
}