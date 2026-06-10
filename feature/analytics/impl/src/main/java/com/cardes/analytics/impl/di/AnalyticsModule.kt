package com.cardes.analytics.impl.di

import com.cardes.analytics.impl.AnalyticsViewModel
import com.cardes.analytics.impl.bycategories.ByCategoriesViewModel
import com.cardes.analytics.impl.totalspent.TotalSpentViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val analyticsModule = module {
    viewModelOf(::AnalyticsViewModel)
    viewModelOf(::ByCategoriesViewModel)
    viewModelOf(::TotalSpentViewModel)
}
