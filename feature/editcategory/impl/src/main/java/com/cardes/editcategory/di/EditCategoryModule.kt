package com.cardes.editcategory.di

import com.cardes.editcategory.EditCategoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val editCategoryModule = module {
    viewModelOf(::EditCategoryViewModel)
}
