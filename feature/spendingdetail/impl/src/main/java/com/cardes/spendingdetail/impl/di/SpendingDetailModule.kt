package com.cardes.spendingdetail.impl.di

import com.cardes.spendingdetail.impl.SpendingDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendingDetailModule = module {
    viewModelOf(::SpendingDetailViewModel)
}
