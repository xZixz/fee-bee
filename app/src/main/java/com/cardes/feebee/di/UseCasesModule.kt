package com.cardes.feebee.di

import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRangeUseCase
import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRangeUseCaseImpl
import com.cardes.domain.usecase.addcategory.AddCategoryUseCase
import com.cardes.domain.usecase.addcategory.AddCategoryUseCaseImpl
import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.addsamples.AddSamplesUseCaseImpl
import com.cardes.domain.usecase.createspending.CreateSpendingUseCase
import com.cardes.domain.usecase.createspending.CreateSpendingUseCaseImpl
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCaseImpl
import com.cardes.domain.usecase.getallspendings.GetAllSpendingsUseCase
import com.cardes.domain.usecase.getallspendings.GetAllSpendingsUseCaseImpl
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.getspending.GetSpendingUseCaseImpl
import com.cardes.domain.usecase.getspendingsbycategories.GetSpendingsByCategoriesUseCase
import com.cardes.domain.usecase.getspendingsbycategories.GetSpendingsByCategoriesUseCaseImpl
import com.cardes.domain.usecase.getspendingsbydaterange.GetSpendingsByDateRangeUseCase
import com.cardes.domain.usecase.getspendingsbydaterange.GetSpendingsByDateRangeUseCaseImpl
import com.cardes.domain.usecase.gettotalspentbycategoriesinmonth.GetTotalSpentByCategoriesInMonthUseCase
import com.cardes.domain.usecase.gettotalspentbycategoriesinmonth.GetTotalSpentByCategoriesInMonthUseCaseImpl
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCaseImpl
import com.cardes.domain.usecase.observecategory.ObserveCategoryUseCase
import com.cardes.domain.usecase.observecategory.ObserveCategoryUseCaseImpl
import com.cardes.domain.usecase.observegroupedbymonthspendings.ObserveGroupedByMonthsSpendingsUseCase
import com.cardes.domain.usecase.observegroupedbymonthspendings.ObserveGroupedByMonthsSpendingsUseCaseImpl
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCase
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCaseImpl
import com.cardes.domain.usecase.observespendings.ObserveSpendingsUseCase
import com.cardes.domain.usecase.observespendings.ObserveSpendingsUseCaseImpl
import com.cardes.domain.usecase.removecategory.RemoveCategoryUseCase
import com.cardes.domain.usecase.removecategory.RemoveCategoryUseCaseImpl
import com.cardes.domain.usecase.removecategoryemoji.RemoveCategoryEmojiUseCase
import com.cardes.domain.usecase.removecategoryemoji.RemoveCategoryEmojiUseCaseImpl
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCaseImpl
import com.cardes.domain.usecase.updatecategoryemoji.UpdateCategoryEmojiUseCase
import com.cardes.domain.usecase.updatecategoryemoji.UpdateCategoryEmojiUseCaseImpl
import com.cardes.domain.usecase.updatecategoryname.UpdateCategoryNameUseCase
import com.cardes.domain.usecase.updatecategoryname.UpdateCategoryNameUseCaseImpl
import com.cardes.domain.usecase.updatespending.UpdateSpendingUseCase
import com.cardes.domain.usecase.updatespending.UpdateSpendingUseCaseImpl
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

    @Binds
    fun bindAddCategoryUseCase(addCategoryUseCase: AddCategoryUseCaseImpl): AddCategoryUseCase

    @Binds
    fun bindUpdateSpendingUseCase(updateSpendingUseCase: UpdateSpendingUseCaseImpl): UpdateSpendingUseCase

    @Binds
    fun bindObserveSpending(observeSpendingUseCase: ObserveSpendingUseCaseImpl): ObserveSpendingUseCase

    @Binds
    fun bindObserveCategory(observeCategoryUseCase: ObserveCategoryUseCaseImpl): ObserveCategoryUseCase

    @Binds
    fun bindGetSpendingByCategories(getSpendingsByCategoriesUseCase: GetSpendingsByCategoriesUseCaseImpl): GetSpendingsByCategoriesUseCase

    @Binds
    fun bindGetAllSpendings(getAllSpendingsUseCase: GetAllSpendingsUseCaseImpl): GetAllSpendingsUseCase

    @Binds
    fun bindRemoveCategory(removeCategoryUseCase: RemoveCategoryUseCaseImpl): RemoveCategoryUseCase

    @Binds
    fun bindUpdateCategoryNameUseCase(updateCategoryNameUseCase: UpdateCategoryNameUseCaseImpl): UpdateCategoryNameUseCase

    @Binds
    fun bindGetSpendingsByDateRange(getSpendingsByDateRange: GetSpendingsByDateRangeUseCaseImpl): GetSpendingsByDateRangeUseCase

    @Binds
    fun bindGetSpendingsByCategoriesByDateRange(getSpendingsByCategoriesByDateRange: GetSpendingsByCategoriesByDateRangeUseCaseImpl): GetSpendingsByCategoriesByDateRangeUseCase

    @Binds
    fun bindGetTotalSpentByCategoriesInMonthUseCase(getTotalSpentByCategoriesInMonthUseCase: GetTotalSpentByCategoriesInMonthUseCaseImpl): GetTotalSpentByCategoriesInMonthUseCase

    @Binds
    fun bindUpdateCategoryEmojiUseCase(updateCategoryEmojiUseCase: UpdateCategoryEmojiUseCaseImpl): UpdateCategoryEmojiUseCase

    @Binds
    fun bindRemoveCategoryEmojiUseCase(removeCategoryEmojiUseCase: RemoveCategoryEmojiUseCaseImpl): RemoveCategoryEmojiUseCase

    @Binds
    fun bindObservingGroupedByMonthsSpendings(groupedByMonthsSpendings: ObserveGroupedByMonthsSpendingsUseCaseImpl): ObserveGroupedByMonthsSpendingsUseCase
}
