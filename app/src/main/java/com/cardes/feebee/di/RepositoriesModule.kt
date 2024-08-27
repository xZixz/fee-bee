package com.cardes.feebee.di

import com.cardes.data.category.CategoryLocalDataSource
import com.cardes.data.category.CategoryLocalDataSourceImpl
import com.cardes.data.category.CategoryRepositoryImpl
import com.cardes.data.spending.SpendingLocalDataSource
import com.cardes.data.spending.SpendingLocalDataSourceImpl
import com.cardes.data.spending.SpendingRepositoryImpl
import com.cardes.domain.repository.CategoryRepository
import com.cardes.domain.repository.SpendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Singleton
    @Binds
    fun bindSpendingRepository(spendingRepository: SpendingRepositoryImpl): SpendingRepository

    @Singleton
    @Binds
    fun bindSpendingLocalDataSource(spendingLocalDataSource: SpendingLocalDataSourceImpl): SpendingLocalDataSource

    @Singleton
    @Binds
    fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

    @Singleton
    @Binds
    fun bindCategoryLocalDataSource(categoryLocalDataSource: CategoryLocalDataSourceImpl): CategoryLocalDataSource
}
