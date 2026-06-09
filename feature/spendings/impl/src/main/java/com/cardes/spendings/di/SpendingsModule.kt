package com.cardes.spendings.di

import com.cardes.spendings.SpendingsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val spendingsModule = module {
    viewModel { SpendingsListViewModel(get(), get(), get(), get(named("Default"))) }
}
