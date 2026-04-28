package com.cardes.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineScopesModule = module {
    single(named("ApplicationScope")) {
        CoroutineScope(SupervisorJob() + get<CoroutineDispatcher>(named("Default")))
    }
}
