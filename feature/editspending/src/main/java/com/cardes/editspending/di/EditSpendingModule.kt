package com.cardes.editspending.di

import com.cardes.editspending.EditSpendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val editSpendingModule = module {
    viewModelOf(::EditSpendingViewModel)
}
