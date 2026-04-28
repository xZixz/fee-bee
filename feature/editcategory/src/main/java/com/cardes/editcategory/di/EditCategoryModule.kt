package com.cardes.editcategory.di

import com.cardes.editcategory.EditCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val editCategoryModule = module {
    viewModelOf(::EditCategoryViewModel)
}
