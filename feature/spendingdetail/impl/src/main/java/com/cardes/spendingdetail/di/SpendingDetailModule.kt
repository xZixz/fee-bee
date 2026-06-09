package com.cardes.spendingdetail.di

import com.cardes.spendingdetail.SpendingDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendingDetailModule = module {
    viewModelOf(::SpendingDetailViewModel)
}
