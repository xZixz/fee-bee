package com.cardes.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    factory(named("IO")) { Dispatchers.IO }
    factory(named("Default")) { Dispatchers.Default }
}
