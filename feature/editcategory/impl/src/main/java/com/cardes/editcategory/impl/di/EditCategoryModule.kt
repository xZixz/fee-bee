package com.cardes.editcategory.impl.di

import com.cardes.editcategory.impl.EditCategoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val editCategoryModule = module {
    viewModelOf(::EditCategoryViewModel)
}
