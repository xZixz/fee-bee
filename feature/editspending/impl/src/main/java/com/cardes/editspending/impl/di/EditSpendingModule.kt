package com.cardes.editspending.impl.di

import com.cardes.editspending.impl.EditSpendingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val editSpendingModule = module {
    viewModelOf(::EditSpendingViewModel)
}
