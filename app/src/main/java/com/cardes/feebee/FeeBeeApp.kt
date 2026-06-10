package com.cardes.feebee

import android.app.Application
import com.cardes.analytics.impl.di.analyticsModule
import com.cardes.categories.impl.di.categoriesModule
import com.cardes.core.common.di.coroutineScopesModule
import com.cardes.core.common.di.dispatchersModule
import com.cardes.data.di.databaseModule
import com.cardes.data.di.repositoriesModule
import com.cardes.editcategory.impl.di.editCategoryModule
import com.cardes.editspending.impl.di.editSpendingModule
import com.cardes.feebee.di.useCasesModule
import com.cardes.spendingdetail.impl.di.spendingDetailModule
import com.cardes.spendings.impl.di.spendingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FeeBeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FeeBeeApp)
            modules(
                dispatchersModule,
                coroutineScopesModule,
                databaseModule,
                repositoriesModule,
                useCasesModule,
                analyticsModule,
                categoriesModule,
                editCategoryModule,
                editSpendingModule,
                spendingDetailModule,
                spendingsModule,
            )
        }
    }
}
