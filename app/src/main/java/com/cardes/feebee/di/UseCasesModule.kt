package com.cardes.feebee.di

import com.cardes.domain.repository.CategoryRepository
import com.cardes.domain.repository.SpendingRepository
import com.cardes.domain.usecase.GetSpendingsByCategoriesByDateRangeUseCase
import com.cardes.domain.usecase.addcategory.AddCategoryUseCase
import com.cardes.domain.usecase.addsamples.AddSamplesUseCase
import com.cardes.domain.usecase.createspending.CreateSpendingUseCase
import com.cardes.domain.usecase.deleteallspendings.DeleteAllSpendingsUseCase
import com.cardes.domain.usecase.getallspendings.GetAllSpendingsUseCase
import com.cardes.domain.usecase.getspending.GetSpendingUseCase
import com.cardes.domain.usecase.getspendingsbycategories.GetSpendingsByCategoriesUseCase
import com.cardes.domain.usecase.getspendingsbydaterange.GetSpendingsByDateRangeUseCase
import com.cardes.domain.usecase.gettotalspentbycategoriesinmonth.GetTotalSpentByCategoriesInMonthUseCase
import com.cardes.domain.usecase.observecategories.ObserveCategoriesUseCase
import com.cardes.domain.usecase.observecategory.ObserveCategoryUseCase
import com.cardes.domain.usecase.observegroupedbymonthspendings.ObserveGroupedByMonthsSpendingsUseCase
import com.cardes.domain.usecase.observespending.ObserveSpendingUseCase
import com.cardes.domain.usecase.observespendings.ObserveSpendingsUseCase
import com.cardes.domain.usecase.removecategory.RemoveCategoryUseCase
import com.cardes.domain.usecase.removecategoryemoji.RemoveCategoryEmojiUseCase
import com.cardes.domain.usecase.removespending.RemoveSpendingUseCase
import com.cardes.domain.usecase.updatecategoryemoji.UpdateCategoryEmojiUseCase
import com.cardes.domain.usecase.updatecategoryname.UpdateCategoryNameUseCase
import com.cardes.domain.usecase.updatespending.UpdateSpendingUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory<CreateSpendingUseCase> { CreateSpendingUseCase(get<SpendingRepository>()::createSpending) }
    factory<ObserveSpendingsUseCase> { ObserveSpendingsUseCase(get<SpendingRepository>()::observeSpendings) }
    factory<GetSpendingUseCase> { GetSpendingUseCase(get<SpendingRepository>()::getSpending) }
    factory<RemoveSpendingUseCase> { RemoveSpendingUseCase(get<SpendingRepository>()::removeSpending) }
    factory<AddSamplesUseCase> { AddSamplesUseCase(get<SpendingRepository>()::createSamples) }
    factory<DeleteAllSpendingsUseCase> { DeleteAllSpendingsUseCase(get<SpendingRepository>()::deleteAll) }
    factory<ObserveCategoriesUseCase> { ObserveCategoriesUseCase(get<CategoryRepository>()::observeCategories) }
    factory<AddCategoryUseCase> { AddCategoryUseCase(get<CategoryRepository>()::createCategory) }
    factory<UpdateSpendingUseCase> { UpdateSpendingUseCase(get<SpendingRepository>()::updateSpending) }
    factory<ObserveSpendingUseCase> { ObserveSpendingUseCase(get<SpendingRepository>()::observeSpending) }
    factory<ObserveCategoryUseCase> { ObserveCategoryUseCase(get<CategoryRepository>()::observeCategory) }
    factory<GetSpendingsByCategoriesUseCase> { GetSpendingsByCategoriesUseCase(get<SpendingRepository>()::getSpendingsByCategoryIds) }
    factory<GetAllSpendingsUseCase> { GetAllSpendingsUseCase(get<SpendingRepository>()::getAllSpendings) }
    factory<RemoveCategoryUseCase> { RemoveCategoryUseCase(get<CategoryRepository>()::removeCategory) }
    factory<UpdateCategoryNameUseCase> { UpdateCategoryNameUseCase(get<CategoryRepository>()::updateCategoryName) }
    factory<GetSpendingsByDateRangeUseCase> { GetSpendingsByDateRangeUseCase(get<SpendingRepository>()::getSpendingsByDateRage) }
    factory<GetSpendingsByCategoriesByDateRangeUseCase> { GetSpendingsByCategoriesByDateRangeUseCase(get<SpendingRepository>()::getSpendingsByCategoriesByDateRange) }
    factory<GetTotalSpentByCategoriesInMonthUseCase> { GetTotalSpentByCategoriesInMonthUseCase(get<SpendingRepository>()::getTotalSpentByCategoriesInMonth) }
    factory<UpdateCategoryEmojiUseCase> { UpdateCategoryEmojiUseCase(get<CategoryRepository>()::updateCategoryEmoji) }
    factory<RemoveCategoryEmojiUseCase> { RemoveCategoryEmojiUseCase(get<CategoryRepository>()::removeCategoryEmoji) }
    factory<ObserveGroupedByMonthsSpendingsUseCase> { ObserveGroupedByMonthsSpendingsUseCase(get<SpendingRepository>()::observeGroupedByMonthsSpending) }
}
