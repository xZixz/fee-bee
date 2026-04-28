package com.cardes.data.di

import com.cardes.data.category.CategoryLocalDataSource
import com.cardes.data.category.CategoryLocalDataSourceImpl
import com.cardes.data.category.CategoryRepositoryImpl
import com.cardes.data.spending.SpendingLocalDataSource
import com.cardes.data.spending.SpendingLocalDataSourceImpl
import com.cardes.data.spending.SpendingRepositoryImpl
import com.cardes.domain.repository.CategoryRepository
import com.cardes.domain.repository.SpendingRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule = module {
    single<SpendingRepository> { SpendingRepositoryImpl(get(), get(named("IO"))) }
    single<SpendingLocalDataSource> { SpendingLocalDataSourceImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(named("IO"))) }
    single<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(get()) }
}
