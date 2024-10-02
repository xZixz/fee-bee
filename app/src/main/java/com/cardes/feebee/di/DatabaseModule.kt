package com.cardes.feebee.di

import android.content.Context
import androidx.room.Room
import com.cardes.data.db.FEE_BEE_DATABASE_NAME
import com.cardes.data.db.FeeBeeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFeeBeeDatabase(
        @ApplicationContext context: Context,
    ): FeeBeeDatabase =
        Room
            .databaseBuilder(
                context = context,
                klass = FeeBeeDatabase::class.java,
                name = FEE_BEE_DATABASE_NAME,
            ).build()

    @Provides
    fun provideSpendingDao(feeBeeDatabase: FeeBeeDatabase) = feeBeeDatabase.spendingDao()

    @Provides
    fun provideCategoryDao(feeBeeDatabase: FeeBeeDatabase) = feeBeeDatabase.categoryDao()
}
