package com.cardes.categories.impl.di

import com.cardes.categories.impl.CategoriesListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoriesModule = module {
    viewModelOf(::CategoriesListViewModel)
}
