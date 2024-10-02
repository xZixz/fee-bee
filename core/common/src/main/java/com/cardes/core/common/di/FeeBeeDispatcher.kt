package com.cardes.core.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(
    val feeBeeDispatcher: FeeBeeDispatcher,
)

enum class FeeBeeDispatcher {
    IO,
    Default,
}
