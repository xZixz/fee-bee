package com.cardes.feebee.di

import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.addsamples.AddSamplesUseCaseImpl
import com.cardes.domain.usecase.createspending.CreateSpendingUseCase
import com.cardes.domain.usecase.createspending.CreateSpendingUseCaseImpl
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCaseImpl
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.getspending.GetSpendingUseCaseImpl
import com.cardes.domain.usecase.getspendings.ObserveSpendingsUseCase
import com.cardes.domain.usecase.getspendings.ObserveSpendingsUseCaseImpl
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCaseImpl
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesModule {
    @Binds
    fun bindCreateSpendingUseCase(createSpendingUseCase: CreateSpendingUseCaseImpl): CreateSpendingUseCase

    @Binds
    fun bindGetSpendingsUseCase(getSpendingsUseCase: ObserveSpendingsUseCaseImpl): ObserveSpendingsUseCase

    @Binds
    fun bindGetSpendingUseCase(getSpendingUseCase: GetSpendingUseCaseImpl): GetSpendingUseCase

    @Binds
    fun bindRemoveSpendingUseCase(removeSpendingUseCase: RemoveSpendingUseCaseImpl): RemoveSpendingUseCase

    @Binds
    fun bindAddSamplesUseCase(addSamplesUseCase: AddSamplesUseCaseImpl): AddSamplesUseCase

    @Binds
    fun bindDeleteAllSpendingsUseCase(deleteAllSpendingsUseCase: DeleteAllSpendingsUseCaseImpl): DeleteAllSpendingsUseCase

    @Binds
    fun bindObserveCategoriesUseCase(observeCategoriesUseCase: ObserveCategoriesUseCaseImpl): ObserveCategoriesUseCase
}
