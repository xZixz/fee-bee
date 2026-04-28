package com.cardes.analytics.di

import com.cardes.analytics.AnalyticsViewModel
import com.cardes.analytics.bycategories.ByCategoriesViewModel
import com.cardes.analytics.totalspent.TotalSpentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val analyticsModule = module {
    viewModelOf(::AnalyticsViewModel)
    viewModelOf(::ByCategoriesViewModel)
    viewModelOf(::TotalSpentViewModel)
}
