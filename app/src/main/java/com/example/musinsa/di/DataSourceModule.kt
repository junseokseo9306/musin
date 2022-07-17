package com.example.musinsa.di

import com.example.musinsa.datasource.DataSource
import com.example.musinsa.datasource.DataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindItemListSource(
        dataSourceImpl: DataSourceImpl,
    ): DataSource
}