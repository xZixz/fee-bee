package com.cardes.spendingdetail.di

import com.cardes.spendingdetail.SpendingDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val spendingDetailModule = module {
    viewModelOf(::SpendingDetailViewModel)
}
