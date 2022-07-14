package com.example.musinsa.di

import com.example.musinsa.repository.Repository
import com.example.musinsa.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl,
    ): Repository
}