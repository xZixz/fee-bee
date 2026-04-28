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
import org.koin.dsl.module

val useCasesModule = module {
    factory<CreateSpendingUseCase> { CreateSpendingUseCaseImpl(get()) }
    factory<ObserveSpendingsUseCase> { ObserveSpendingsUseCaseImpl(get()) }
    factory<GetSpendingUseCase> { GetSpendingUseCaseImpl(get()) }
    factory<RemoveSpendingUseCase> { RemoveSpendingUseCaseImpl(get()) }
    factory<AddSamplesUseCase> { AddSamplesUseCaseImpl(get()) }
    factory<DeleteAllSpendingsUseCase> { DeleteAllSpendingsUseCaseImpl(get()) }
    factory<ObserveCategoriesUseCase> { ObserveCategoriesUseCaseImpl(get()) }
    factory<AddCategoryUseCase> { AddCategoryUseCaseImpl(get()) }
    factory<UpdateSpendingUseCase> { UpdateSpendingUseCaseImpl(get()) }
    factory<ObserveSpendingUseCase> { ObserveSpendingUseCaseImpl(get()) }
    factory<ObserveCategoryUseCase> { ObserveCategoryUseCaseImpl(get()) }
    factory<GetSpendingsByCategoriesUseCase> { GetSpendingsByCategoriesUseCaseImpl(get()) }
    factory<GetAllSpendingsUseCase> { GetAllSpendingsUseCaseImpl(get()) }
    factory<RemoveCategoryUseCase> { RemoveCategoryUseCaseImpl(get()) }
    factory<UpdateCategoryNameUseCase> { UpdateCategoryNameUseCaseImpl(get()) }
    factory<GetSpendingsByDateRangeUseCase> { GetSpendingsByDateRangeUseCaseImpl(get()) }
    factory<GetSpendingsByCategoriesByDateRangeUseCase> { GetSpendingsByCategoriesByDateRangeUseCaseImpl(get()) }
    factory<GetTotalSpentByCategoriesInMonthUseCase> { GetTotalSpentByCategoriesInMonthUseCaseImpl(get()) }
    factory<UpdateCategoryEmojiUseCase> { UpdateCategoryEmojiUseCaseImpl(get()) }
    factory<RemoveCategoryEmojiUseCase> { RemoveCategoryEmojiUseCaseImpl(get()) }
    factory<ObserveGroupedByMonthsSpendingsUseCase> { ObserveGroupedByMonthsSpendingsUseCaseImpl(get()) }
}
