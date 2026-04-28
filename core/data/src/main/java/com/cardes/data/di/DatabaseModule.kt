package com.cardes.data.di

import androidx.room.Room
import com.cardes.data.db.FEE_BEE_DATABASE_NAME
import com.cardes.data.db.FeeBeeDatabase
import com.cardes.data.db.MIGRATION_2_3
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = FeeBeeDatabase::class.java,
            name = FEE_BEE_DATABASE_NAME,
        ).addMigrations(MIGRATION_2_3)
            .build()
    }

    factory { get<FeeBeeDatabase>().spendingDao() }
    factory { get<FeeBeeDatabase>().categoryDao() }
}
